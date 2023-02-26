package pt.solutions.af.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");

    public static LocalDateTime formatDate(String date) {
        return LocalDateTime.parse(date, formatter);
    }

}
