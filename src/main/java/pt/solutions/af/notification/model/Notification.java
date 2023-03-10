package pt.solutions.af.notification.model;


import lombok.Data;
import pt.solutions.af.commons.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {

    private String title;
    private String message;
    private String url;
    private boolean isRead;
    private LocalDate createdAt;
}
