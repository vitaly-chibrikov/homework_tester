package base;

/**
 * @author v.chibrikov
 */
public interface CaseServer {
    void run();

    void stop();

    String getOut();

    boolean joinTillStarted();
}
