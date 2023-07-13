package pt.solutions.af.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pt.solutions.af.user.model.customer.Customer;
import pt.solutions.af.user.model.customer.CustomerListView;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query("SELECT new " + CustomerListView.FULL_NAME + "(u)" +
            " FROM User u JOIN u.userRoles ur JOIN ur.role r WHERE u.provider = false")
    List<CustomerListView> getAllCustomers();

}
