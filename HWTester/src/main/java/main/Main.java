package main;

import base.CaseConfig;

import java.security.NoSuchAlgorithmException;

/**
 * @author v.chibrikov
 */
public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        CaseConfig cfg = new CaseConfig("./cfg/test.properties", args);
        CaseProcessor caseProcessor = new CaseProcessor(cfg);
        boolean result = caseProcessor.process();

        if (!result) {
            System.out.println("Test failed with output:");
            System.out.print(caseProcessor.getServerOut());
            System.exit(1);
        } else {
            System.out.println("Test passed.");
            String key = KeyPassCreator.create(cfg.getCaseClass());
            System.out.println("Task pass-key: " + key);
        }
    }
}
