package pt.solutions.af.serviceType.model;

import java.util.Arrays;

public enum Effort {

    MIN_15(900),
    MIN_30(1800),
    MIN_45(2700),
    MIN_60(3600),
    MIN_90(5400),
    MIN_120(7200);

    private final int seconds;

    Effort(int time) {
        this.seconds = time;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getEffortIntMinutes() {
        return seconds * 60;
    }

    public static Effort of(int minute) {
        int seconds = minute * 60;

        return Arrays.stream(Effort.values())
                .filter(e -> e.seconds == seconds)
                .findFirst().get();
    }
}
