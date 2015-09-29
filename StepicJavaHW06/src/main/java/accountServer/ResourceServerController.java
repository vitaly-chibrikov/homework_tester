package accountServer;

/**
 * @author v.chibrikov
 */
public class ResourceServerController implements ResourceServerControllerMBean {
    private final ResourceServer resourceServer;

    public ResourceServerController(ResourceServer resourceServer) {
        this.resourceServer = resourceServer;
    }


    @Override
    public String getName() {
        return resourceServer.getName();
    }

    @Override
    public int getAge() {
        return resourceServer.getAge();
    }
}
