package pt.solutions.af.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TimePeriod that = (TimePeriod) o;

        return new EqualsBuilder().append(start, that.start).append(end, that.end).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(start).append(end).toHashCode();
    }
}
