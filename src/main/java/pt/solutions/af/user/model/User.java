package pt.solutions.af.user.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.solutions.af.commons.entity.BaseEntity;
import pt.solutions.af.notification.model.Notification;
import pt.solutions.af.user.model.auth.AuthUserView;
import pt.solutions.af.user.model.auth.Role;
import pt.solutions.af.user.model.auth.UserRole;

import java.util.List;

@Table(name = "users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean provider;
    private String street;
    private String city;
    private String postCode;
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Notification> notifications;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles;

    public User(AuthUserView user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    @Builder
    public User(String firstName, String lastName, String email, String phoneNumber, boolean provider,
                String street, String city, String postCode, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.provider = provider;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
        this.password = password;
    }

}
