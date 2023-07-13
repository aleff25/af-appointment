package pt.solutions.af.user.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pt.solutions.af.user.application.dto.CreateCustomerDTO;
import pt.solutions.af.user.application.dto.CreateProviderDto;
import pt.solutions.af.user.model.User;
import pt.solutions.af.user.model.customer.Customer;
import pt.solutions.af.user.model.customer.CustomerListView;
import pt.solutions.af.user.model.provider.Provider;
import pt.solutions.af.user.model.provider.ProviderListView;
import pt.solutions.af.user.repository.CustomerRepository;
import pt.solutions.af.user.repository.ProviderRepository;
import pt.solutions.af.user.repository.UserRepository;
import pt.solutions.af.workingplan.model.WorkingPlan;
import springfox.documentation.annotations.Cacheable;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserApplicationService implements UserDetailsService {

    private UserRepository userRepository;
    private CustomerRepository customerRepository;
    private ProviderRepository providerRepository;

    @Cacheable("customer_users")
    public Customer getCustomerById(String customerId) {
        return customerRepository.findById(customerId).get();
    }

    @Cacheable("provider_users")
    public Provider getProviderById(String providerId) {
        return providerRepository.findById(providerId)
                .get();
    }

    public void saveNewRetailCustomer(CreateCustomerDTO dto) {
        Customer retailCustomer = new Customer();
        retailCustomer.setId(UUID.randomUUID().toString());
        retailCustomer.setFirstName(dto.getFirstName());
        retailCustomer.setLastName(dto.getLastName());
        retailCustomer.setPhoneNumber(dto.getPhoneNumber());
        retailCustomer.setCity(dto.getCity());
        retailCustomer.setEmail(dto.getEmail());
        retailCustomer.setPostCode(dto.getPostCode());

        customerRepository.save(retailCustomer);
    }

    public void saveNewProvider(CreateProviderDto dto) {
        WorkingPlan workingPlan = WorkingPlan.generateDefaultWorkingPlan();
        Provider provider = new Provider();
        provider.setId(UUID.randomUUID().toString());
        provider.setFirstName(dto.getFirstName());
        provider.setLastName(dto.getLastName());
        provider.setPhoneNumber(dto.getPhoneNumber());
        provider.setCity(dto.getCity());
        provider.setEmail(dto.getEmail());
        provider.setPostCode(dto.getPostCode());
        provider.setProvider(true);
        provider.setWorkingPlan(workingPlan);

        providerRepository.save(provider);
    }

    //TODO: Add the exception
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow();
    }

    public List<CustomerListView> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public List<ProviderListView> getAllProviders() {
        return providerRepository.findAllProviders();
    }

    @Override
    @Cacheable("auth_users")
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByEmail(login);
    }
}
