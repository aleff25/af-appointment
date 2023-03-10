package pt.solutions.af.invoice.model;

import lombok.Data;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.commons.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity {

    private String number;
    private String status;
    private double totalAmount;
    private LocalDateTime issued;

    @OneToMany(mappedBy = "invoice")
    private List<Appointment> appointments;
}
