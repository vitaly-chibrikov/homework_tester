package testCases.stepic;

import base.CaseConfig;
import base.TestException;
import base.TestCase;
import testCases.http.HttpHelper;

import java.io.IOException;

/**
 * @author v.chibrikov
 */
public class HW01 implements TestCase {
    public boolean test(CaseConfig cfg) {
        try {
            String value;
            if (cfg.getArgs().length == 1)
                value = cfg.getArgs()[0];
            else
                value = "test_message";

            String url = "http://" + cfg.getHost() + ":" + cfg.getPort() + "/" + "mirror?key=" + value;
            String pageBody = HttpHelper.sendGet(url).getPage();
            return pageBody.equals(value);
        } catch (IOException e) {
            throw new TestException(e);
        }
    }
}
