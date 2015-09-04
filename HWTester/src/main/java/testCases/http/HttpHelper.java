package testCases.http;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author v.chibrikov
 */
public class HttpHelper {
    private static final String USER_AGENT = "Mozilla/5.0";

    public static HttpAnswer sendGet(String url) throws IOException {
        HttpClient client = HttpClients.createDefault();

        HttpGet request = new HttpGet(url);
        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);
        String page = getPageText(response);
        int code = response.getStatusLine().getStatusCode();

        return new HttpAnswer(page, code);
    }

    public static HttpAnswer sendPost(String url, List<NameValuePair> nameValuePairs) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setHeader("User-Agent", USER_AGENT);

        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = client.execute(post);
        String page = getPageText(response);
        int code = response.getStatusLine().getStatusCode();

        return new HttpAnswer(page, code);
    }

    private static String getPageText(HttpResponse response) throws IOException {
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }
    }
}
