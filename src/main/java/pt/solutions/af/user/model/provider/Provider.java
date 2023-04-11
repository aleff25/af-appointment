package pt.solutions.af.user.model.provider;

import lombok.Builder;
import lombok.Data;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.user.model.User;
import pt.solutions.af.work.model.Work;
import pt.solutions.af.workingplan.model.WorkingPlan;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "providers")
@PrimaryKeyJoinColumn(name = "provider_id")
public class Provider extends User {

    @OneToMany(mappedBy = "provider")
    private List<Appointment> appointments;

    @ManyToMany
    @JoinTable(name = "works_providers", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns =
    @JoinColumn(name = "work_id"))
    private List<Work> works;

    @OneToOne(cascade = CascadeType.ALL)
    private WorkingPlan workingPlan;

}
