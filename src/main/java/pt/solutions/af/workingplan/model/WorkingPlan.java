package pt.solutions.af.workingplan.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import pt.solutions.af.user.model.provider.Provider;
import pt.solutions.af.utils.TimePeriod;

import java.time.LocalTime;


@Data
@Entity
@Table(name = "working_plans")
@NoArgsConstructor
public class WorkingPlan {


    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(name = "provider_id")
    private String id;

    @OneToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @Type(JsonType.class)
    @Column(columnDefinition = "json", name = "monday")
    private DayPlan monday;

    @Type(JsonType.class)
    @Column(name = "tuesday")
    private DayPlan tuesday;

    @Type(JsonType.class)
    @Column(columnDefinition = "json", name = "wednesday")
    private DayPlan wednesday;

    @Type(JsonType.class)
    @Column(columnDefinition = "json", name = "thursday")
    private DayPlan thursday;

    @Type(JsonType.class)
    @Column(columnDefinition = "json", name = "friday")
    private DayPlan friday;

    @Type(JsonType.class)
    @Column(columnDefinition = "json", name = "saturday")
    private DayPlan saturday;

    @Type(JsonType.class)
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
