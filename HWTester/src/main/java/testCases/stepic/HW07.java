package testCases.stepic;

import base.CaseConfig;
import base.TestCase;
import base.TestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author v.chibrikov
 */
public class HW07 implements TestCase {
    private static final int CLIENTS_NUMBER = 10;
    private static final long SLEEP_TIME = TimeUnit.MILLISECONDS.toMillis(1);
    private static final long TEST_TIME = TimeUnit.SECONDS.toMillis(10);

    @Override
    public boolean test(CaseConfig cfg) {
        String host = cfg.getHost();
        String port = cfg.getPort();
        ExecutorService executor = null;
        long timeStart = new Date().getTime();
        try {
            executor = Executors.newFixedThreadPool(CLIENTS_NUMBER);
            List<Future<Boolean>> list = new ArrayList<>();
            for (int i = 0; i < CLIENTS_NUMBER; i++) {
                Callable<Boolean> worker = new ClientSocketCallable(host, Integer.decode(port));
                Future<Boolean> submit = executor.submit(worker);
                list.add(submit);
            }
            Boolean result = true;
            System.out.println(list.size());
            for (Future<Boolean> future : list) {
                if (!future.get()) {
                    result = false;
                    break;
                }
            }
            long timeEnd = new Date().getTime();
            long totalTime = timeEnd - timeStart;
            if (totalTime > TEST_TIME) {
                System.out.println("Server is too slow. Test time: " + totalTime + ". Expected time: " + TEST_TIME);
            }

            return result && totalTime < TEST_TIME;
        } catch (InterruptedException | ExecutionException e) {
            throw new TestException(e);
        } finally {
            if (executor != null)
                executor.shutdown();
        }
    }

    private class ClientSocketCallable implements Callable<Boolean> {
        private final String host;
        private final int port;

        private ClientSocketCallable(String host, int port) {
            this.host = host;
            this.port = port;
        }

        @Override
        public Boolean call() throws Exception {
            try (Socket clientSocket = new Socket(host, port);
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                int callIndex = 0;
                do {
                    String messageToServer = "Client time: " + new Date().getTime();
                    out.println(messageToServer);
                    String messageFromServer = in.readLine();
                    if (messageFromServer == null || !messageFromServer.equals(messageToServer))
                        return false;
                    ++callIndex;
                    Thread.sleep(SLEEP_TIME);
                } while (callIndex < 5000);
                out.println("Bye.");
                System.out.println("Thread: " + Thread.currentThread().getName() + " messages: " + callIndex);
                return true;
            } catch (IOException e) {
                throw new TestException(e);
            }
        }
    }
}
