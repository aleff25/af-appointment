package pt.solutions.af.user.application;

import org.springframework.stereotype.Service;
import pt.solutions.af.user.model.User;
import pt.solutions.af.user.repository.UserRepository;

import java.util.List;

@Service
public class UserApplicationService {

    private UserRepository userRepository;

    public List<User> findAllClients() {
        return userRepository.findAllByProviderIs(false);
    }

    public List<User> findAllProviders() {
        return userRepository.findAllByProviderIs(true);
    }

    public User findByName(String name) {
        return  userRepository.findByNameEqualsIgnoreCase(name);
    }

    public User findByEmail(String email) {
        return  userRepository.findByEmailEqualsIgnoreCase(email);
    }
}
