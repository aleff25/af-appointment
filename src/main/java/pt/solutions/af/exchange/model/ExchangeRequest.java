package pt.solutions.af.exchange.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.commons.entity.BaseEntity;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exchanges")
@Builder
public class ExchangeRequest extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ExchangeStatusEnum status;

    @OneToOne
    @JoinColumn(name = "appointment_requestor_id")
    private Appointment requestor;

    @OneToOne
    @JoinColumn(name = "appointment_requested_id")
    private Appointment requested;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
}
