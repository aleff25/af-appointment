package pt.solutions.af.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.solutions.af.user.model.User;

public interface UserRepository extends JpaRepository<User, String> {


    User findByEmailEqualsIgnoreCase(String email);

//    @Transactional(readOnly = true)
//    UserView findOne(String id);
}
