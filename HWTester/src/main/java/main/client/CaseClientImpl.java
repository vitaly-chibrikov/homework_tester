package main.client;

import base.CaseClient;
import base.CaseConfig;
import base.TestException;
import testCases.TestCase;

/**
 * @author v.chibrikov
 */
public class CaseClientImpl implements CaseClient {
    private final CaseConfig cfg;

    public CaseClientImpl(CaseConfig cfg) {
        this.cfg = cfg;
    }

    @Override
    public boolean test() {
        boolean result = createTestCase(cfg.getCaseClass()).test(cfg);
        return result;
    }

    private TestCase createTestCase(String className) {
        try {
            return (TestCase) Class.forName(className).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new TestException(e);
        }
    }

}
