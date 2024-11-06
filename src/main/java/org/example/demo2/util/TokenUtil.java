package org.example.demo2.util;

import java.security.SecureRandom;
import java.math.BigInteger;

public class TokenUtil {

    private static final SecureRandom random = new SecureRandom();

    public static String generateToken() {
        return new BigInteger(130, random).toString(32);
    }
}
