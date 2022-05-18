package com.company.generator;

import java.security.SecureRandom;
import java.util.stream.Collectors;

/**
 *  Класс, который отвечает за генерацию криптостойкого пароля
 */
public class PasswordGenerator {
    private static String password;

    /**
     *  Функция, которая генерирует пароль, и на вход принимает длину пароля
     * @param length
     */
    private static void generator(int length)
    {
        SecureRandom secRandom = new SecureRandom();
        password = secRandom.ints(length, '0', 'z')
                .mapToObj(Character::toString)
                .collect(Collectors.joining());
    }

    /**
     *  Функция, которая возвращает пароль, и на вход принимает длину пароля
     * @param length
     * @return
     */
    public String generatePassword(int length) {
        generator(length);
        return password;
    }
}
