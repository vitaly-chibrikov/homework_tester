package main;

import base.CaseClient;
import base.CaseConfig;
import base.CaseServer;
import base.TestException;
import main.client.CaseClientImpl;
import main.server.CaseServerImpl;

/**
 * @author v.chibrikov
 */
public class CaseProcessor {
    private final CaseServer caseServer;
    private final CaseClient caseClient;

    public CaseProcessor(CaseConfig cfg) {
        caseServer = new CaseServerImpl(cfg);
        caseClient = new CaseClientImpl(cfg);
    }

    public CaseProcessor(CaseServer caseServer,
                         CaseClient caseClient) {
        this.caseServer = caseServer;
        this.caseClient = caseClient;
    }

    public boolean process() {
        try {
            caseServer.run();
            if (caseServer.joinTillStarted()) {
                boolean result = caseClient.test();
                return result;
            }
        } catch (TestException e) {
            System.out.println(e.getMessage());
        } finally {
            caseServer.stop();
        }
        return false;
    }

    public String getServerOut() {
        return caseServer.getOut();
    }
}
