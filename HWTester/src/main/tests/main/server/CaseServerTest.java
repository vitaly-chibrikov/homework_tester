package main.server;

import base.CaseClient;
import base.CaseConfig;
import base.CaseServer;
import main.CaseProcessor;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author v.chibrikov
 */
public class CaseServerTest {
    @Test
    public void serverHangTest() {
        CaseClient caseClient = mock(CaseClient.class);
        when(caseClient.test()).thenReturn(true);

        CaseConfig cfg = mock(CaseConfig.class);
        when(cfg.getStartWaitPeriod()).thenReturn(100);
        when(cfg.getServerStartCommand()).thenReturn("java -jar src/main/tests/samples/hanged.jar");
        when(cfg.getStartedMessage()).thenReturn("Server started");

        CaseServer caseServer = new CaseServerImpl(cfg);

        CaseProcessor caseProcessor = new CaseProcessor(caseServer, caseClient);
        boolean result = caseProcessor.process();

        verify(caseClient, times(0)).test();
        Assert.assertFalse(result);
        if (caseServer.getOut().contains("OUTPUT"))
            Assert.assertTrue(caseServer.getOut().contains("hanged"));
    }
}
