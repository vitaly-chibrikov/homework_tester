package testCases.http;

/**
 * @author v.chibrikov
 */
public class HttpAnswer {
    private final String page;
    private final int code;

    public HttpAnswer(String page, int code) {
        this.page = page;
        this.code = code;
    }

    public String getPage() {
        return page;
    }

    public int getCode() {
        return code;
    }
}
