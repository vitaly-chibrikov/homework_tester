package testCases.stepic;

import base.CaseConfig;
import base.TestCase;
import base.TestException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import testCases.http.HttpAnswer;
import testCases.http.HttpHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author v.chibrikov
 */
public class HW031 implements TestCase {

    public boolean test(CaseConfig cfg) {
        try {
            List<NameValuePair> urlParameters = new ArrayList<>();
            String login = "userName" + (new Date().getTime());
            cfg.setInterCasesData(login);
            String password = "userPassword";
            urlParameters.add(new BasicNameValuePair("login", login));
            urlParameters.add(new BasicNameValuePair("password", password));

            String signUpURL = "http://" + cfg.getHost() + ":" + cfg.getPort() + "/signup";
            HttpAnswer signUpAnswer = HttpHelper.sendPost(signUpURL, urlParameters);
            int signUpCode = signUpAnswer.getCode();
            if (signUpCode != 200) {
                System.out.println("Can't sign up. Response code: " + signUpCode);
                return false;
            }

        } catch (IOException e) {
            throw new TestException(e);
        }
        return true;
    }
}
