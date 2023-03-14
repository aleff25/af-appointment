package pt.solutions.af.user.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pt.solutions.af.commons.entity.BaseEntity;
import pt.solutions.af.notification.model.Notification;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Table(name = "users")
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean provider;
    private String street;
    private String city;
    private String postCode;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @Builder
    public User(String id, String firstName, String lastName, String email, String phoneNumber, boolean provider,
                String street, String city, String postCode) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.provider = provider;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
    }
}
