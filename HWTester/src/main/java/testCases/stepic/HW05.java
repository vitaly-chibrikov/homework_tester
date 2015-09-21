package testCases.stepic;

import base.CaseConfig;
import base.TestCase;
import base.TestException;
import testCases.http.HttpHelper;
import testCases.jmx.Client;

import java.io.IOException;

/**
 * @author v.chibrikov
 */
public class HW05 implements TestCase {
    private static final int NEW_VALUE = 15;

    public boolean test(CaseConfig cfg) {
        try {
            String url = "http://" + cfg.getHost() + ":" + cfg.getPort() + "/admin";
            String pageBody = HttpHelper.sendGet(url).getPage();
            if (!pageBody.equals("10"))
                return false;

            Client jmxClient = new Client("service:jmx:rmi:///jndi/rmi://:9010/jmxrmi");

            String objectName = "Admin:type=AccountServerController";
            String attributeName = "UsersLimit";
            Object usersLimit = jmxClient.getAttribute(objectName, attributeName);
            if (!usersLimit.equals(10)) {
                return false;
            }

            jmxClient.setAttribute(objectName, attributeName, NEW_VALUE);

            pageBody = HttpHelper.sendGet(url).getPage();
            if (!pageBody.equals(String.valueOf(NEW_VALUE)))
                return false;

            usersLimit = jmxClient.getAttribute(objectName, attributeName);
            return usersLimit.equals(NEW_VALUE);

        } catch (IOException e) {
            throw new TestException(e);
        }
    }
}
