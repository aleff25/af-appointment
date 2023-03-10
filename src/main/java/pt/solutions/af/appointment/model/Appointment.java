package pt.solutions.af.appointment.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pt.solutions.af.user.model.User;
import pt.solutions.af.user.model.UserView;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Table(name = "appointments")
@Entity
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String providerId;

    @JoinColumn(name = "provider_id", insertable = false, updatable = false)
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private UserView provider;

    private String custumerId;

    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private UserView customer;


}
