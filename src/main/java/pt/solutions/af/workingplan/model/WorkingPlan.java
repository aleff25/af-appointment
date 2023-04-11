package pt.solutions.af.workingplan.model;

import io.hypersistence.utils.hibernate.type.json.JsonStringType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import pt.solutions.af.user.model.provider.Provider;
import pt.solutions.af.utils.TimePeriod;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalTime;


@TypeDef(name = "json", typeClass = JsonStringType.class)
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

    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonType")
    @Column(columnDefinition = "json", name = "monday")
    private DayPlan monday;

    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonType")
    @Column(columnDefinition = "json", name = "tuesday")
    private DayPlan tuesday;

    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonType")
    @Column(columnDefinition = "json", name = "wednesday")
    private DayPlan wednesday;

    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonType")
    @Column(columnDefinition = "json", name = "thursday")
    private DayPlan thursday;

    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonType")
    @Column(columnDefinition = "json", name = "friday")
    private DayPlan friday;

    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonType")
    @Column(columnDefinition = "json", name = "saturday")
    private DayPlan saturday;

    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonType")
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
