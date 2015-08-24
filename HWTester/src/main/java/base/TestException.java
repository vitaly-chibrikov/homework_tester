package base;

/**
 * @author v.chibrikov
 */
public class TestException extends RuntimeException {
    public TestException(String s) {
        super(s);
    }

    public TestException(Throwable throwable) {
        super(throwable);
    }
}
