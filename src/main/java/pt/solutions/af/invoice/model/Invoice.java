package pt.solutions.af.invoice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.commons.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "invoices")
@NoArgsConstructor
public class Invoice extends BaseEntity {

    private String number;
    private String status;
    private double totalAmount;
    private LocalDateTime issued;

    @OneToMany(mappedBy = "invoice")
    private List<Appointment> appointments;

    @Builder
    public Invoice(String number, String status, double totalAmount, LocalDateTime issued, List<Appointment> appointments) {
        this.number = number;
        this.status = status;
        this.totalAmount = totalAmount;
        this.issued = issued;
        this.appointments = appointments;
    }

    public void totalAmountFromAppointments() {
        this.appointments.forEach(appointment -> {
            appointment.setInvoice(this);
            totalAmount += appointment.getWork().getPrice();
        });
    }
}
