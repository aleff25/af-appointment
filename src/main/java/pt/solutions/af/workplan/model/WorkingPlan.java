package pt.solutions.af.workplan.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import pt.solutions.af.utils.TimePeriod;
import pt.solutions.af.workplan.DayPlan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;


@TypeDef(name = "json", typeClass = JsonStringType.class)
@Data
@Entity
@Table(name = "working_plans")
@NoArgsConstructor
public class WorkingPlan {


    @Id
    @Column(name = "provider_id")
    private String providerId;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "monday")
    private DayPlan monday;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "tuesday")
    private DayPlan tuesday;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "wednesday")
    private DayPlan wednesday;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "thursday")
    private DayPlan thursday;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "friday")
    private DayPlan friday;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "saturday")
    private DayPlan saturday;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "sunday")
    private DayPlan sunday;

    public static WorkingPlan generateDefaultWorkingPlan() {
        WorkingPlan wp = new WorkingPlan();
        LocalTime defaultStartHour = LocalTime.parse("07:00");
        LocalTime defaultEndHour = LocalTime.parse("18:00");
        TimePeriod defaultWorkingPeroid = new TimePeriod(defaultStartHour, defaultEndHour);
        DayPlan defaultDayPlan = new DayPlan(defaultWorkingPeroid);
        wp.setMonday(defaultDayPlan);
        wp.setTuesday(defaultDayPlan);
        wp.setWednesday(defaultDayPlan);
        wp.setThursday(defaultDayPlan);
        wp.setFriday(defaultDayPlan);
        wp.setSaturday(defaultDayPlan);
        wp.setSunday(defaultDayPlan);
        return wp;
    }

    public DayPlan getDay(String day) {
        switch (day) {
            case "monday":
                return monday;

            case "tuesday":
                return tuesday;

            case "wednesday":
                return wednesday;

            case "thursday":
                return thursday;

            case "friday":
                return friday;

            case "saturday":
                return saturday;

            case "sunday":
                return sunday;

            default:
                return null;
        }
    }
}
