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

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "status_changes")
    private List<AppointmentStatus> statusChanges = new ArrayList<>();

    @JoinColumn(name = "providerId", insertable = false, updatable = false)
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private Provider provider;

    private String custumerId;

    @JoinColumn(name = "custumerId", insertable = false, updatable = false)
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private Customer customer;

    private String workId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workId", insertable = false, updatable = false)
    private Work work;

    private String invoiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoiceId", insertable = false, updatable = false)
    private Invoice invoice;


    @Builder
    public Appointment(LocalDateTime startDate, LocalDateTime endDate, String providerId, String custumerId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.providerId = providerId;
        this.custumerId = custumerId;
    }

    public void newAppointment() {
        this.status = AppointmentStatusEnum.SCHEDULED;
        this.statusChanges.add(AppointmentStatus.ofScheduled());
    }

    public void invoiceAppointment() {
        this.status = AppointmentStatusEnum.INVOICED;
        this.statusChanges.add(AppointmentStatus.ofInvoiced());
    }
}
