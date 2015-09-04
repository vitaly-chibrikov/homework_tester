package testCases.testExamples;

import base.CaseConfig;
import base.TestException;
import testCases.http.HttpHelper;
import base.TestCase;

import java.io.IOException;

/**
 * @author v.chibrikov
 */
@SuppressWarnings("UnusedDeclaration")
public class MirrorHomeWork implements TestCase {
    public boolean test(CaseConfig cfg) {
        try {
            String value = cfg.getArgs()[0];
            String url = "http://" + cfg.getHost() + ":" + cfg.getPort() + "/" + "mirror?key=" + value;
            String pageBody = HttpHelper.sendGet(url).getPage();
            return pageBody.equals(value);
        } catch (IOException e) {
            throw new TestException(e);
        }
    }
}
