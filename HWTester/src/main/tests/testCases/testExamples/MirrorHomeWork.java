package testCases.testExamples;

import base.CaseConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testCases.TestCase;

/**
 * @author v.chibrikov
 */
@SuppressWarnings("UnusedDeclaration")
public class MirrorHomeWork implements TestCase {
    public boolean test(WebDriver driver, CaseConfig cfg) {
        String value = cfg.getArgs()[0];
        String URL = "http://" + cfg.getHost() + ":" + cfg.getPort() + "/";
        driver.get(URL + "mirror?key=" + value);
        WebElement element = driver.findElement(By.tagName("body"));
        String pageBody = element.getText();
        return pageBody.equals(value);
    }
}
