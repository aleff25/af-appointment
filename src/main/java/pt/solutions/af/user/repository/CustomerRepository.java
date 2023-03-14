package pt.solutions.af.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.solutions.af.user.model.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
