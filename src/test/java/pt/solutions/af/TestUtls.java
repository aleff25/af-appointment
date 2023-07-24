package pt.solutions.af;

import java.util.Random;

public class TestUtls {

    public static Long randomLong() {
        return new Random().nextLong() * (99 - 1);
    }
}
