package main;

import base.CaseConfig;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.NoSuchAlgorithmException;

/**
 * @author v.chibrikov
 */
public class Main {
    private static final String VERSION = "1.0";

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
            String md5 = DigestUtils.md5Hex("Version: " + VERSION + " case:" + cfg.getCaseClass());
            System.out.println("Task pass-key: " + md5);
        }
    }
}
