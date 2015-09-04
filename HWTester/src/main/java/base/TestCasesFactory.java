package base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author v.chibrikov
 */
public class TestCasesFactory {
    public static TestCase[] createTestCases(CaseConfig cfg) {
        String classes = cfg.getCaseClass();
        String[] classesArray = classes.split(";");
        List<TestCase> testCases = new ArrayList<>();
        for (String className : classesArray) {
            if (!className.trim().isEmpty()) {
                testCases.add(createTestCase(className.trim()));
            }
        }
        return testCases.toArray(new TestCase[testCases.size()]);
    }

    private static TestCase createTestCase(String className) {
        try {
            return (TestCase) Class.forName(className).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new TestException(e);
        }
    }
}
