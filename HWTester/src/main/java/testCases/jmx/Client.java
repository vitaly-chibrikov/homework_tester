package testCases.jmx;

import base.TestException;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author v.chibrikov
 */
@SuppressWarnings("UnusedDeclaration")
public class Client {
    private final MBeanServerConnection mbsc;

    public Client(String url) throws IOException {
        JMXServiceURL jmxServiceURL = new JMXServiceURL(url);
        JMXConnector jmxc = JMXConnectorFactory.connect(jmxServiceURL, null);
        this.mbsc = jmxc.getMBeanServerConnection();
    }

    public String[] getDomains() {
        try {
            return mbsc.getDomains();
        } catch (IOException e) {
            throw new TestException(e);
        }
    }

    public Set<ObjectName> getObjects() {
        try {
            return new TreeSet<>(mbsc.queryNames(null, null));
        } catch (IOException e) {
            throw new TestException(e);
        }
    }

    public Object getAttribute(String objectName, String name) {
        try {
            return mbsc.getAttribute(new ObjectName(objectName), name);
        } catch (AttributeNotFoundException | MBeanException | MalformedObjectNameException | ReflectionException | IOException | InstanceNotFoundException e) {
            throw new TestException(e);
        }
    }


    public void setAttribute(String objectName, String name, Object value) {
        try {
            Attribute attribute = new Attribute(name, value);
            mbsc.setAttribute(new ObjectName(objectName), attribute);
        } catch (AttributeNotFoundException | MBeanException | MalformedObjectNameException | ReflectionException | IOException | InstanceNotFoundException | InvalidAttributeValueException e) {
            throw new TestException(e);
        }
    }

}
