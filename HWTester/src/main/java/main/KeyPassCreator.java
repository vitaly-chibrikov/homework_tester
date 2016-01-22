package main;

import base.BaseValues;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author v.chibrikov
 */
public class KeyPassCreator {
    private static final String salt = "Amadeus" + BaseValues.VERSION;

    public static String create(String base) {
        return DigestUtils.md5Hex("Salt: " + salt + " Base:" + base);
    }

}
