package testCases;

import base.CaseConfig;
import org.openqa.selenium.WebDriver;

/**
 * @author v.chibrikov
 */
public interface TestCase {
    boolean test(WebDriver driver, CaseConfig cfg);
}
