package pt.solutions.af.appointment.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import pt.solutions.af.commons.entity.BaseEntity;
import pt.solutions.af.invoice.model.Invoice;
import pt.solutions.af.user.model.User;
import pt.solutions.af.user.model.UserView;
import pt.solutions.af.user.model.customer.Customer;
import pt.solutions.af.user.model.provider.Provider;
import pt.solutions.af.work.model.Work;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "appointments")
@Entity
@NoArgsConstructor
public class Appointment extends BaseEntity {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String providerId;

    @Enumerated(EnumType.STRING)
    private AppointmentStatusEnum status;

//    @Type(type = "json")
//    @Column(columnDefinition = "json", name = "status_changes")
//    private List<AppointmentStatus> statusChanges = new ArrayList<>();

    @JoinColumn(name = "providerId", insertable = false, updatable = false)
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User provider;

    private String custumerId;

    @JoinColumn(name = "custumerId", insertable = false, updatable = false, nullable = true)
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User customer;

    private String workId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workId", insertable = false, updatable = false)
    private Work work;

    private String invoiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoiceId", insertable = false, updatable = false)
    private Invoice invoice;

    @Builder
    public Appointment(LocalDateTime startDate, LocalDateTime endDate, String providerId, AppointmentStatusEnum status, Provider provider, String custumerId, Customer customer,
                       String workId, Work work, String invoiceId, Invoice invoice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.providerId = providerId;
        this.status = status;
//        this.statusChanges = statusChanges;
        this.provider = provider;
        this.custumerId = custumerId;
        this.customer = customer;
        this.workId = workId;
        this.work = work;
        this.invoiceId = invoiceId;
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
}
