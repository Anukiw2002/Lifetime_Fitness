package org.example.demo2.util;

import java.security.SecureRandom;

public class TokenUtil {

    private static final SecureRandom random = new SecureRandom();

    // Generates a numeric token of the specified length
    public static String generateNumericToken(int length) {
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // Generate a random digit (0-9)
            token.append(digit);
        }

        return token.toString();
    }
}
