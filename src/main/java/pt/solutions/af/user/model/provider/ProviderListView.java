package pt.solutions.af.user.model.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pt.solutions.af.user.model.User;
import pt.solutions.af.user.model.auth.Role;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProviderListView {

    public static final String FULL_NAME = "pt.solutions.af.user.model.provider.ProviderListView";

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String street;
    private String city;
    private String postCode;
    private List<String> roles;

    public ProviderListView(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.street = user.getStreet();
        this.city = user.getCity();
        this.postCode = user.getPostCode();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
    }
}
