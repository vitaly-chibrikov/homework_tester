package base;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author v.chibrikov
 */
public class CaseConfigTest {
    @Test
    public void readParamsTest() {
        /*
        serverStartCommand: java -jar server.jar
        maxStartWaitPeriod: 1000
        caseClass: testCases.stepic.HW1
        host: localhost
        port: 8080
         */
        CaseConfig cfg = new CaseConfig("src/main/tests/test.properties");
        Assert.assertEquals("8080", cfg.getPort());
        Assert.assertEquals("localhost", cfg.getHost());
        Assert.assertEquals("testCases.stepic.HW01", cfg.getCaseClass());
        Assert.assertEquals(1000, cfg.getStartWaitPeriod());
        Assert.assertEquals("java -jar server.jar", cfg.getServerStartCommand());
        Assert.assertEquals("Server started", cfg.getStartedMessage());
    }
}
