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
//        char[] text = new char[length];
//        for (int i = 0; i < length; i++)
//        {
//            text[i] = characters.charAt(new Random().nextInt(characters.length()));
//        }
//        password = new String(text);
    }

    public String generatePassword(int length) {
        generator( length);
        return password;
    }

}
