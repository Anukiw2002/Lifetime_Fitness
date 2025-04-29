package org.example.demo2.util;

import java.security.SecureRandom;

public class TokenUtil {

    private static final SecureRandom random = new SecureRandom();


    public static String generateNumericToken(int length) {
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            token.append(digit);
        }

        return token.toString();
    }
}
