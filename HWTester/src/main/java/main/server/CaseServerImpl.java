package main.server;

import base.CaseConfig;
import base.CaseServer;
import base.TestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author v.chibrikov
 */
public class CaseServerImpl implements CaseServer {
    private static final int TICK = 10;
    private final StringBuffer out = new StringBuffer();
    private final AtomicBoolean serverStarted = new AtomicBoolean(false);
    private final CaseConfig cfg;
    private Process process;

    public CaseServerImpl(CaseConfig cfg) {
        this.cfg = cfg;
    }


    public void run() {
        process = runServerProcess();
    }

    public void stop() {
        process.destroy();
    }

    public String getOut() {
        return out.toString();
    }

    public boolean joinTillStarted() {
        long startTime = (new Date()).getTime();
        int waitPeriod = cfg.getStartWaitPeriod();
        try {
            while (!serverStarted.get()) {
                Thread.sleep(TICK);
                long currentTime = (new Date()).getTime();
                if (currentTime > startTime + waitPeriod)
                    return false;
            }
        } catch (InterruptedException e) {
            throw new TestException(e);
        }
        return true;
    }

    private Process runServerProcess() {
        try {
            ProcessBuilder pb = new ProcessBuilder(cfg.getServerStartCommand().split(" "));
            pb.redirectErrorStream(true);
            Process p = pb.start();

            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
            StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");

            outputGobbler.start();
            errorGobbler.start();
            return p;
        } catch (IOException e) {
            throw new TestException(e);
        }
    }

    private class StreamGobbler extends Thread {
        private final InputStream is;
        private final String type;

        private StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        @Override
        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    out.append(type).append('>').append(line).append('\n');
                    if (line.contains(cfg.getStartedMessage())) {
                        serverStarted.set(true);
                    }
                }
            } catch (IOException e) {
                throw new TestException(e);
            }
        }
    }
}
