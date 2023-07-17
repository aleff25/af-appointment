package pt.solutions.af.appointment.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pt.solutions.af.chat.model.ChatMessage;
import pt.solutions.af.commons.entity.BaseEntity;
import pt.solutions.af.invoice.model.Invoice;
import pt.solutions.af.user.model.customer.Customer;
import pt.solutions.af.user.model.provider.Provider;
import pt.solutions.af.work.model.Work;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "appointments")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Appointment extends BaseEntity implements Comparable<Appointment> {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private LocalDateTime canceledAt;

    @Enumerated(EnumType.STRING)
    private AppointmentStatusEnum status;

    @Type(JsonType.class)
    @Column(columnDefinition = "json", name = "status_changes")
    @Builder.Default
    private List<AppointmentStatus> statusChanges = new ArrayList<>();


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

    @OneToMany(mappedBy = "appointment")
    private List<ChatMessage> chatMessages;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @LastModifiedBy
    private LocalDateTime lastModifiedBy;

    @Builder
    public Appointment(LocalDateTime startDate, LocalDateTime endDate,
                       AppointmentStatusEnum status, Provider provider, Customer customer, Work work, Invoice invoice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.provider = provider;
        this.customer = customer;
        this.work = work;
        this.invoice = invoice;
    }

    public void newAppointment() {
        this.status = AppointmentStatusEnum.SCHEDULED;
        this.statusChanges.add(AppointmentStatus.ofScheduled());
    }

    public void invoiceAppointment() {
        this.status = AppointmentStatusEnum.INVOICED;
        this.statusChanges.add(AppointmentStatus.ofInvoiced());
    }

    @Override
    public int compareTo(Appointment o) {
        return this.getStartDate().compareTo(o.getStartDate());
    }
}
