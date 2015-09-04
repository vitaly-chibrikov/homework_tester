package testCases;

import base.CaseConfig;
import base.CaseServer;
import main.CaseProcessor;
import main.server.CaseServerImpl;
import org.junit.Assert;
import org.junit.Test;
import base.TestCase;
import base.TestCasesFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author v.chibrikov
 */
public class TestCaseTest {
    @Test
    public void mirrorWebServerTest() {
        CaseConfig cfg = mock(CaseConfig.class);
        when(cfg.getStartWaitPeriod()).thenReturn(10000);
        when(cfg.getHost()).thenReturn("localhost");
        when(cfg.getPort()).thenReturn("8080");
        when(cfg.getServerStartCommand()).thenReturn("java -jar src/main/tests/samples/mirror.jar");
        when(cfg.getStartedMessage()).thenReturn("Server started");
        when(cfg.getCaseClass()).thenReturn("testCases.testExamples.MirrorHomeWork");
        when(cfg.getArgs()).thenReturn(new String[]{"hello"});

        TestCase[] testCases = TestCasesFactory.createTestCases(cfg);
        CaseServer caseServer = new CaseServerImpl(cfg);

        CaseProcessor caseProcessor = new CaseProcessor(cfg, caseServer, testCases);
        boolean result = caseProcessor.process();

        Assert.assertTrue(result);
    }
}
