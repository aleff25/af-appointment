package pt.solutions.af.user.model.provider;

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
@PrimaryKeyJoinColumn(name = "id_provider")
public class Provider extends User {

    @OneToMany(mappedBy = "provider", fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "works_providers", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_work"))
    private List<Work> works;

    @OneToOne(mappedBy = "providerId", cascade = CascadeType.ALL)
    private WorkingPlan workingPlan;
}
