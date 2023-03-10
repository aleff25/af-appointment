package pt.solutions.af.user.model.customer;

import lombok.Data;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.user.model.User;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;


@Data
@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "id_customer")
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    private List<Appointment> appointments;
}
