package pt.solutions.af;

import java.util.Random;
import java.util.UUID;

public class TestUtls {

    public static Long randomLong() {
        return new Random().nextLong() * (99 - 1);
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }
}
