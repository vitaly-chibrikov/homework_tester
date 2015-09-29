package testCases.stepic;

import base.CaseConfig;
import base.TestCase;
import base.TestException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import testCases.http.HttpAnswer;
import testCases.http.HttpHelper;
import testCases.jmx.Client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author v.chibrikov
 */
public class HW06 implements TestCase {
    private static final String RESOURCE_NAME = "resource.xml";
    private static final String UTF_8 = "utf-8";

    public boolean test(CaseConfig cfg) {
        try {
            long currentTime = (new Date()).getTime();
            String name = "Test" + currentTime;
            int age = (int) (currentTime / Integer.MAX_VALUE);
            String resourceString = createResourceString(name, age);
            Path resource = Files.write(Paths.get(RESOURCE_NAME), resourceString.getBytes(UTF_8), StandardOpenOption.CREATE);
            resource.toFile().deleteOnExit();
            if (!resource.toFile().exists()) {
                throw new TestException("Can't create resource file: " + resource.toFile().getAbsolutePath());
            }

            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("path", "resource.xml"));

            String signUpURL = "http://" + cfg.getHost() + ":" + cfg.getPort() + "/resources";
            HttpAnswer signUpAnswer = HttpHelper.sendPost(signUpURL, urlParameters);
            int signUpCode = signUpAnswer.getCode();
            if (signUpCode != 200) {
                System.out.println("Can't load resource. Response code: " + signUpCode);
                return false;
            }

            Client jmxClient = new Client("service:jmx:rmi:///jndi/rmi://:9010/jmxrmi");

            String objectName = "Admin:type=ResourceServerController";
            String attributeNameName = "Name";
            Object nameFromResource = jmxClient.getAttribute(objectName, attributeNameName);
            String attributeAgeName = "Age";
            Object ageFromResource = jmxClient.getAttribute(objectName, attributeAgeName);

            return name.equals(nameFromResource) && ageFromResource.equals(age);
        } catch (IOException e) {
            throw new TestException(e);
        }
    }

    private String createResourceString(String name, int age) {
        return "<class name=\"resources.TestResource\">" + System.lineSeparator() +
                "<name>" + name + "</name>" + System.lineSeparator() +
                "<age>" + age + "</age>" + System.lineSeparator() +
                "</class>";
    }
}
