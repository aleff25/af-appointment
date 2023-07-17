package pt.solutions.af.chat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.commons.entity.BaseEntity;
import pt.solutions.af.user.model.User;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "messages")
public class ChatMessage extends BaseEntity implements Comparable<ChatMessage> {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String message;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;


    @Override
    public int compareTo(ChatMessage o) {
        return this.createdAt.compareTo(o.getCreatedAt());
    }
}
