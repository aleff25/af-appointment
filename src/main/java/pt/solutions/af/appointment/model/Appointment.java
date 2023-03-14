package pt.solutions.af.appointment.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import pt.solutions.af.commons.entity.BaseEntity;
import pt.solutions.af.user.model.User;
import pt.solutions.af.user.model.UserView;
import pt.solutions.af.user.model.customer.Customer;
import pt.solutions.af.user.model.provider.Provider;

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

    @JoinColumn(name = "provider_id", insertable = false, updatable = false)
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private Provider provider;

    private String custumerId;

    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private Customer customer;


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
