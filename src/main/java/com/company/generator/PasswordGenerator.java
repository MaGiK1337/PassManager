package com.company.generator;

import java.security.SecureRandom;
import java.util.stream.Collectors;

public class PasswordGenerator {

    private static String password;

    private static void generator(int length)
    {
        SecureRandom secRandom = new SecureRandom();

        password = secRandom.ints(length, '0', 'z')
                .mapToObj(Character::toString)
                .collect(Collectors.joining());
    }

    public String generatePassword(int length) {
        generator( length);
        return password;
    }

}
