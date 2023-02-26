package pt.solutions.af.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pt.solutions.af.user.model.User;
import pt.solutions.af.user.model.UserView;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllByProviderIs(boolean provider);

    User findByNameEqualsIgnoreCase(String name);

    User findByEmailEqualsIgnoreCase(String email);

//    @Transactional(readOnly = true)
//    UserView findOne(String id);
}
