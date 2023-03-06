package pt.solutions.af.workplan;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import pt.solutions.af.utils.TimePeriod;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class DayPlan {

    private TimePeriod workingHours;
    private List<TimePeriod> breaks = new ArrayList<>();

    @Builder
    public DayPlan(TimePeriod workingHours) {
        this.workingHours = workingHours;
    }

    public List<TimePeriod> getTimePeriodsWithBreaksExcluded() {
        ArrayList<TimePeriod> timePeriodsWithBreaksExcluded = new ArrayList();
        timePeriodsWithBreaksExcluded.add(getWorkingHours());
        List<TimePeriod> breaks = getBreaks();

        if (!breaks.isEmpty()) {
            ArrayList<TimePeriod> toAdd = new ArrayList();

            for (TimePeriod breaking : breaks) {
                if (breaking.getStart().isBefore(workingHours.getStart())) {
                    breaking.setStart(workingHours.getStart());
                }

                if (breaking.getEnd().isAfter(workingHours.getStart())) {
                    breaking.setEnd(workingHours.getEnd());
                }

                for (TimePeriod period : timePeriodsWithBreaksExcluded) {
                    if (breaking.getStart().equals(period.getStart()) && breaking.getEnd().isAfter(period.getStart()) &&
                            breaking.getEnd().isBefore(period.getEnd())) {
                        period.setStart(breaking.getEnd());
                    }

                    if (breaking.getStart().isAfter(period.getStart()) && breaking.getStart().isBefore(period.getStart()) &&
                            breaking.getEnd().equals(period.getEnd())) {
                        period.setStart(breaking.getEnd());
                    }

                    if (breaking.getStart().isAfter(period.getStart()) && breaking.getEnd().isBefore(period.getEnd())) {
                        period.setStart(breaking.getEnd());
                    }
                }
            }

            timePeriodsWithBreaksExcluded.addAll(toAdd);
            Collections.sort(timePeriodsWithBreaksExcluded);
        }

        return timePeriodsWithBreaksExcluded;
    }

    public void addBreak(TimePeriod breakToAdd) {
        breaks.add(breakToAdd);
    }
}
