package pt.solutions.af.appointment.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.solutions.af.commons.entity.BaseEntity;
import pt.solutions.af.invoice.model.Invoice;
import pt.solutions.af.user.model.customer.Customer;
import pt.solutions.af.user.model.provider.Provider;
import pt.solutions.af.work.model.Work;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "appointments")
@Entity
public class Appointment extends BaseEntity implements Comparable<Appointment> {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private AppointmentStatusEnum status;

//    @Type(type = "json")
//    @Column(columnDefinition = "json", name = "status_changes")
//    private List<AppointmentStatus> statusChanges = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "work_id")
    private Work work;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Builder
    public Appointment(LocalDateTime startDate, LocalDateTime endDate,
                       AppointmentStatusEnum status, Provider provider, Customer customer, Work work, String invoiceId, Invoice invoice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
//        this.statusChanges = statusChanges;
        this.provider = provider;
        this.customer = customer;
        this.work = work;
        this.invoice = invoice;
    }

    public void newAppointment() {
        this.status = AppointmentStatusEnum.SCHEDULED;
//        this.statusChanges = new ArrayList<>();
//        this.statusChanges.add(AppointmentStatus.ofScheduled());
    }

    public void invoiceAppointment() {
        this.status = AppointmentStatusEnum.INVOICED;
//        this.statusChanges.add(AppointmentStatus.ofInvoiced());
    }

    @Override
    public int compareTo(Appointment o) {
        return this.getStartDate().compareTo(o.getStartDate());
    }
}
