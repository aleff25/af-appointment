package pt.solutions.af.user;

import pt.solutions.af.user.model.provider.Provider;
import pt.solutions.af.user.repository.ProviderRepository;

import static pt.solutions.af.user.UserTestUtils.aUser;

public class ProviderTestUtils {

    public static void persist(ProviderRepository repository, Provider user) {
        repository.save(user);
    }

    public static Provider aProvider(String id, String firstName, String lastName,
                                     String email) {
        var user = aUser(id, firstName, lastName, email, true);
        var provider = new Provider();
        provider.setId(user.getId());
        return provider;
    }
}
