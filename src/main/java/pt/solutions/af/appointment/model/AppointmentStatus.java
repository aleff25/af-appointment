package pt.solutions.af.appointment.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentStatus implements Comparable<AppointmentStatus> {

    private AppointmentStatusEnum status;
    private ZonedDateTime when;

    public static AppointmentStatus ofScheduled() {
        return new AppointmentStatus(AppointmentStatusEnum.SCHEDULED, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    @Override
    public int compareTo(AppointmentStatus o) {
        return this.when.compareTo(o.when);
    }
}
