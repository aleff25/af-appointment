package pt.solutions.af.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.solutions.af.user.model.User;
import pt.solutions.af.user.model.auth.AuthUserView;

public interface UserRepository extends JpaRepository<User, String> {


    User findByEmailEqualsIgnoreCase(String email);


    @Query("SELECT new pt.solutions.af.user.model.auth.AuthUserView(u.id, u.firstName, u.lastName, u.email, u.password, r" +
            ".name )" +
            " FROM User u JOIN u.userRoles ur JOIN ur.role r WHERE u.email = :login")
    AuthUserView findByEmail(@Param("login") String login);

//    @Transactional(readOnly = true)
//    UserView findOne(String id);
}
