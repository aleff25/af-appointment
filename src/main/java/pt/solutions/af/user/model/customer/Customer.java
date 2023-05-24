package pt.solutions.af.user.model.customer;

import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.user.model.User;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "customer_id")
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    private List<Appointment> appointments;

    public Customer() {
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
