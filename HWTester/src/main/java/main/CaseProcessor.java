package main;

import base.CaseConfig;
import base.CaseServer;
import base.TestException;
import main.server.CaseServerImpl;
import base.TestCase;
import base.TestCasesFactory;

/**
 * @author v.chibrikov
 */
public class CaseProcessor {
    private final CaseServer caseServer;
    private final TestCase[] testCases;
    private final CaseConfig cfg;

    public CaseProcessor(CaseConfig cfg) {
        this.caseServer = new CaseServerImpl(cfg);
        this.cfg = cfg;
        testCases = TestCasesFactory.createTestCases(cfg);
    }

    public CaseProcessor(CaseConfig cfg,
                         CaseServer caseServer,
                         TestCase... testCases) {
        this.cfg = cfg;
        this.caseServer = caseServer;
        this.testCases = testCases;
    }

    public boolean process() {
        for (TestCase client : testCases) {
            try {
                caseServer.run();
                if (caseServer.joinTillStarted()) {
                    boolean result = client.test(cfg);
                    if (!result)
                        return false;
                } else {
                    return false;
                }
                Thread.sleep(cfg.getStopWaitPeriod());
            } catch (TestException | InterruptedException e) {
                System.out.println(e.getMessage());
                return false; //если caseServer выбросит исключение - то метод process всегда будет возвращать true
            } finally {
                caseServer.stop();
            }
        }
        return true;
    }

    public String getServerOut() {
        return caseServer.getOut();
    }
}
