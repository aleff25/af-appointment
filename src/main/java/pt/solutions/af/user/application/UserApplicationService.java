package pt.solutions.af.user.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pt.solutions.af.user.model.User;
import pt.solutions.af.user.model.customer.Customer;
import pt.solutions.af.user.model.provider.Provider;
import pt.solutions.af.user.repository.CustomerRepository;
import pt.solutions.af.user.repository.ProviderRepository;
import pt.solutions.af.user.repository.UserRepository;
import pt.solutions.af.workingplan.model.WorkingPlan;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserApplicationService {

    private UserRepository userRepository;
    private CustomerRepository customerRepository;
    private ProviderRepository providerRepository;

    public Customer getCustomerById(String customerId) {
        return customerRepository.getReferenceById(customerId);
    }

    public void saveNewProvider() {
        WorkingPlan workingPlan = WorkingPlan.generateDefaultWorkingPlan();
        Provider provider = (Provider) Provider.builder().build();
        provider.setWorkingPlan(workingPlan);
        providerRepository.save(provider);
    }

    //TODO: Add the exception
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
