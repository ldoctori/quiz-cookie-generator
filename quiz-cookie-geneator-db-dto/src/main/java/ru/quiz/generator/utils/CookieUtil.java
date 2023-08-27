package ru.quiz.generator.utils;

import java.util.UUID;

public class CookieUtil {

    public static String generateCookieString() {
        UUID uuid = UUID.randomUUID();
        return String.format("%1$016x%2$016x", uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
    }
}
