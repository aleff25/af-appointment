package pt.solutions.af.notification.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import pt.solutions.af.commons.entity.BaseEntity;
import pt.solutions.af.user.model.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "notifications")
@NoArgsConstructor
public class Notification extends BaseEntity {

    private String title;
    private String message;
    private String url;
    private boolean isRead;
    private LocalDate createdAt;
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
}
