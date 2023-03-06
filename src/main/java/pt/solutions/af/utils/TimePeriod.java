package pt.solutions.af.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimePeriod implements Comparable<TimePeriod> {

    private LocalTime start;
    private LocalTime end;


    @Override
    public int compareTo(TimePeriod o) {
        return this.getStart().compareTo(o.getStart());
    }
}
