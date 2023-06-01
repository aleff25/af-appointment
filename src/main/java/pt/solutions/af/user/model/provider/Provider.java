package pt.solutions.af.user.model.provider;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.user.model.User;
import pt.solutions.af.work.model.Work;
import pt.solutions.af.workingplan.model.WorkingPlan;

import java.util.List;

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

    public Provider() {
    }

    public Provider(List<Work> works, WorkingPlan workingPlan) {
        super();
        this.works = works;
        this.workingPlan = workingPlan;
    }


    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(List<Work> works) {
        this.works = works;
    }

    public WorkingPlan getWorkingPlan() {
        return workingPlan;
    }

    public void setWorkingPlan(WorkingPlan workingPlan) {
        this.workingPlan = workingPlan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Provider provider = (Provider) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(appointments, provider.appointments).append(works, provider.works).append(workingPlan, provider.workingPlan).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(appointments).append(works).append(workingPlan).toHashCode();
    }
}
