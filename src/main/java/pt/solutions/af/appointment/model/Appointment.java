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

    @JoinColumn(name = "providerId", insertable = false, updatable = false)
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private UserView provider;

    private String clientId;

    @JoinColumn(name = "clientId", insertable = false, updatable = false)
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private UserView client;

    private String serviceTypeId;

    @Builder
    public Appointment(final LocalDateTime startDate, final LocalDateTime endDate, final String providerId,
                       final UserView provider, final String clientId, final UserView client,
                       final String serviceTypeId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.providerId = providerId;
        this.provider = provider;
        this.clientId = clientId;
        this.client = client;
        this.serviceTypeId = serviceTypeId;
    }
}
