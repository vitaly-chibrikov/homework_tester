package main.server;

import base.CaseConfig;
import base.CaseServer;
import main.CaseProcessor;
import org.junit.Assert;
import org.junit.Test;
import base.TestCase;

import static org.mockito.Mockito.*;

/**
 * @author v.chibrikov
 */
public class CaseServerTest {
    @Test
    public void serverHangTest() {
        CaseConfig cfg = mock(CaseConfig.class);
        when(cfg.getStartWaitPeriod()).thenReturn(100);
        when(cfg.getServerStartCommand()).thenReturn("java -jar src/main/tests/samples/hanged.jar");
        when(cfg.getStartedMessage()).thenReturn("Server started");

        TestCase testCase = mock(TestCase.class);
        when(testCase.test(cfg)).thenReturn(true);

        CaseServer caseServer = new CaseServerImpl(cfg);

        CaseProcessor caseProcessor = new CaseProcessor(cfg, caseServer, testCase);
        boolean result = caseProcessor.process();

        verify(testCase, times(0)).test(cfg);
        Assert.assertFalse(result);
        if (caseServer.getOut().contains("OUTPUT"))
            Assert.assertTrue(caseServer.getOut().contains("hanged"));
    }
}
