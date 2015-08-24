package main;

import base.CaseClient;
import base.CaseConfig;
import base.CaseServer;
import main.server.CaseServerImpl;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author v.chibrikov
 */
public class CaseProcessorTest {
    @Test
    public void passedTest() {
        CaseClient caseClient = mock(CaseClient.class);
        when(caseClient.test()).thenReturn(true);
        CaseServer caseServer = mock(CaseServer.class);
        when(caseServer.joinTillStarted()).thenReturn(true);

        CaseProcessor caseProcessor = new CaseProcessor(caseServer, caseClient);
        boolean result = caseProcessor.process();
        verify(caseServer, times(1)).run();
        verify(caseServer, times(1)).joinTillStarted();
        verify(caseServer, times(1)).stop();
        verify(caseClient, times(1)).test();
        Assert.assertTrue(result);
    }

    @Test
    public void timeOutTest() {
        CaseClient caseClient = mock(CaseClient.class);
        when(caseClient.test()).thenReturn(true);
        CaseServer caseServer = mock(CaseServer.class);
        when(caseServer.joinTillStarted()).thenReturn(false);
        String timeoutLog = "Time out";
        when(caseServer.joinTillStarted()).thenReturn(false);
        when(caseServer.getOut()).thenReturn(timeoutLog);

        CaseProcessor caseProcessor = new CaseProcessor(caseServer, caseClient);
        boolean result = caseProcessor.process();
        verify(caseServer, times(1)).run();
        verify(caseServer, times(1)).joinTillStarted();
        verify(caseServer, times(1)).stop();
        verify(caseClient, times(0)).test();
        Assert.assertFalse(result);

        Assert.assertEquals(timeoutLog, caseProcessor.getServerOut());
    }



}
