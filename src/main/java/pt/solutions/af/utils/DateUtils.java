package pt.solutions.af.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");

    public static LocalDateTime formatDate(String date) {
        return LocalDateTime.parse(date, formatter);
    }

    public static LocalDateTime changeDateStrToLocalDateTime(String dateStr, String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateStr, formatter);

        int startHour = Integer.parseInt(timeStr.split(":")[0]);
        int startMinute = Integer.parseInt(timeStr.split(":")[1]);

        return localDate.atTime(startHour, startMinute);
    }

}
