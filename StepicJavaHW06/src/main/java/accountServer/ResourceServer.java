package accountServer;

/**
 * @author v.chibrikov
 */
public interface ResourceServer {
    String getName();

    int getAge();

    void readResource(String path);
}
