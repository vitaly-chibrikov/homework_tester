package main;

import base.CaseConfig;
import base.CaseServer;
import org.junit.Assert;
import org.junit.Test;
import base.TestCase;

import static org.mockito.Mockito.*;

/**
 * @author v.chibrikov
 */
public class CaseProcessorTest {
    @Test
    public void passedTest() {
        CaseConfig cfg = mock(CaseConfig.class);
        TestCase testCase = mock(TestCase.class);
        when(testCase.test(cfg)).thenReturn(true);
        CaseServer caseServer = mock(CaseServer.class);
        when(caseServer.joinTillStarted()).thenReturn(true);

        CaseProcessor caseProcessor = new CaseProcessor(cfg, caseServer, testCase);
        boolean result = caseProcessor.process();
        verify(caseServer, times(1)).run();
        verify(caseServer, times(1)).joinTillStarted();
        verify(caseServer, times(1)).stop();
        verify(testCase, times(1)).test(cfg);
        Assert.assertTrue(result);
    }

    @Test
    public void timeOutTest() {
        CaseConfig cfg = mock(CaseConfig.class);
        TestCase testCase = mock(TestCase.class);
        when(testCase.test(cfg)).thenReturn(true);
        CaseServer caseServer = mock(CaseServer.class);
        when(caseServer.joinTillStarted()).thenReturn(false);
        String timeoutLog = "Time out";
        when(caseServer.joinTillStarted()).thenReturn(false);
        when(caseServer.getOut()).thenReturn(timeoutLog);

        CaseProcessor caseProcessor = new CaseProcessor(cfg, caseServer, testCase);
        boolean result = caseProcessor.process();
        verify(caseServer, times(1)).run();
        verify(caseServer, times(1)).joinTillStarted();
        verify(caseServer, times(1)).stop();
        verify(testCase, times(0)).test(cfg);
        Assert.assertFalse(result);

        Assert.assertEquals(timeoutLog, caseProcessor.getServerOut());
    }


}
