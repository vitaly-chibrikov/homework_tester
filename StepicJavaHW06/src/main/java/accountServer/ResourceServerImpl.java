package accountServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resources.ResourceFactory;
import resources.TestResource;

/**
 * @author v.chibrikov
 */
public class ResourceServerImpl implements ResourceServer {
    static final Logger logger = LogManager.getLogger(ResourceServerImpl.class.getName());
    private TestResource resource;

    public ResourceServerImpl() {
    }


    @Override
    public String getName() {
        return resource.getName();
    }

    @Override
    public int getAge() {
        return resource.getAge();
    }

    @Override
    public void readResource(String path) {
        resource = ResourceFactory.instance().getResource(path, TestResource.class);
        logger.info("Resource loaded");
    }
}
