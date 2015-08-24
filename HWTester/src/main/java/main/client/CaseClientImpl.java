package main.client;

import base.CaseClient;
import base.CaseConfig;
import base.TestException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import testCases.TestCase;

/**
 * @author v.chibrikov
 */
public class CaseClientImpl implements CaseClient {
    private final CaseConfig cfg;

    public CaseClientImpl(CaseConfig cfg) {
        this.cfg = cfg;
    }

    @Override
    public boolean test() {
        WebDriver driver = new HtmlUnitDriver();
        boolean result = createTestCase(cfg.getCaseClass()).test(driver, cfg);
        driver.quit();
        return result;
    }

    private TestCase createTestCase(String className) {
        try {
            return (TestCase) Class.forName(className).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new TestException(e);
        }
    }

}
