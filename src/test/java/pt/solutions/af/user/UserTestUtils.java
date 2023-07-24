package pt.solutions.af.user;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pt.solutions.af.user.model.User;
import pt.solutions.af.user.model.provider.Provider;

public class UserTestUtils {

    public static Provider registerProvider(TestEntityManager em, String id, String firstName, String lastName, String email) {
        var user = registerUser(em, id, firstName, lastName, email, true);
        var provider = new Provider();
        provider.setId(user.getId());
        em.persist(provider);
        return provider;
    }

    private static User registerUser(TestEntityManager em, String id, String firstName, String lastName, String email, boolean provider) {
        var user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .provider(provider)
                .build();
        user.setId(id);
        em.persist(user);
        return user;
    }

}
