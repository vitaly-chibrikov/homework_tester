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
public class HW02 implements TestCase {
    public boolean test(CaseConfig cfg) {
        try {
            String login = "login" + (new Date().getTime());
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("login", login));
            urlParameters.add(new BasicNameValuePair("password", "userPassword"));

            String signUpURL = "http://" + cfg.getHost() + ":" + cfg.getPort() + "/signup";
            HttpAnswer signUpAnswer = HttpHelper.sendPost(signUpURL, urlParameters);
            int signUpCode = signUpAnswer.getCode();
            if (signUpCode != 200) {
                System.out.println("Can't sign up. Response code: " + signUpCode);
                return false;
            }

            String signInURL = "http://" + cfg.getHost() + ":" + cfg.getPort() + "/signin";
            HttpAnswer signInAnswer = HttpHelper.sendPost(signInURL, urlParameters);
            int signInCode = signInAnswer.getCode();
            String page = signInAnswer.getPage();
            if (signInCode != 200) {
                System.out.println("Can't sign in. Response code: " + signInCode + " page: " + page);
                return false;
            }
            if (!page.contains("Authorized: " + login)) {
                System.out.println("Can't sign in. Response code: " + signInCode + " page: " + page);
                return false;
            }

        } catch (IOException e) {
            throw new TestException(e);
        }
        return true;
    }
}
