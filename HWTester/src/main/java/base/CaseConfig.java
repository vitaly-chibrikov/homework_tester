package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author v.chibrikov
 */
public class CaseConfig {
    private final String host;
    private final String port;
    private final String serverStartCommand;
    private final int startWaitPeriod;
    private final int stopWaitPeriod;
    private final String caseClass;
    private final String[] args;
    private final String startedMessage;

    private String interCasesData;


    public CaseConfig(String cfgName, String[] args) {
        Properties properties = getProperties(cfgName);
        this.host = getNotNull(properties, "host", cfgName);
        this.port = getNotNull(properties, "port", cfgName);
        this.serverStartCommand = getNotNull(properties, "serverStartCommand", cfgName);
        this.startWaitPeriod = Integer.parseInt(getNotNull(properties, "startWaitPeriod", cfgName));
        this.stopWaitPeriod = Integer.parseInt(getNotNull(properties, "stopWaitPeriod", cfgName));
        this.caseClass = getNotNull(properties, "caseClass", cfgName);
        this.startedMessage = getNotNull(properties, "startedMessage", cfgName);
        this.args = args;
    }

    public CaseConfig(String cfgName) {
        //noinspection ZeroLengthArrayAllocation
        this(cfgName, new String[0]);
    }


    private String getNotNull(Properties properties, String name, String cfgName) {
        String value = properties.getProperty(name);
        if (value == null)
            throw new TestException("Can't get " + name + " from config " + cfgName);
        return value;
    }

    public String getInterCasesData() {
        return interCasesData;
    }

    public void setInterCasesData(String interCasesData) {
        this.interCasesData = interCasesData;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getServerStartCommand() {
        return serverStartCommand;
    }

    public int getStartWaitPeriod() {
        return startWaitPeriod;
    }

    public int getStopWaitPeriod() {
        return stopWaitPeriod;
    }

    public String getCaseClass() {
        return caseClass;
    }

    public String[] getArgs() {
        return args;
    }

    public String getStartedMessage() {
        return startedMessage;
    }

    private static Properties getProperties(String cfgName) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(cfgName)) {
            properties.load(input);
            return properties;
        } catch (IOException e) {
            throw new TestException(e);
        }
    }
}
