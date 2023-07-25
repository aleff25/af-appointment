package pt.solutions.af.user;

import pt.solutions.af.user.model.User;
import pt.solutions.af.user.repository.UserRepository;

public class UserTestUtils {


    public static void persist(UserRepository repository, User user) {
        repository.save(user);
    }

    public static User aUser(String id, String firstName, String lastName, String email, boolean provider) {
        var user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .provider(provider)
                .build();
        user.setId(id);
        return user;
    }

}
