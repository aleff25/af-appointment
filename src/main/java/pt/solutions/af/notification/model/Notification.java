package pt.solutions.af.notification.model;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.solutions.af.commons.entity.BaseEntity;
import pt.solutions.af.user.model.User;

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
