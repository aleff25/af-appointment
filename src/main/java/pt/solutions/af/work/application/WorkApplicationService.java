package pt.solutions.af.work.application;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.solutions.af.user.application.UserApplicationService;
import pt.solutions.af.user.model.customer.Customer;
import pt.solutions.af.work.exception.WorkNotFoundException;
import pt.solutions.af.work.model.Work;
import pt.solutions.af.work.repository.WorkRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class WorkApplicationService {

    private WorkRepository repository;

    private UserApplicationService userService;

    public Work getById(String workId) {
        return repository.findById(workId).orElseThrow(WorkNotFoundException::new);
    }

    public List<Work> getAll() {
        return repository.findAll();
    }

    public void create(Work work) {
        repository.save(work);
    }

    public void update(Work workUpdateData) {
        Work work = getById(workUpdateData.getId());
        work.setName(workUpdateData.getName());
        work.setPrice(workUpdateData.getPrice());
        work.setDuration(workUpdateData.getDuration());
        work.setDescription(workUpdateData.getDescription());
        work.setTargetCustomer(workUpdateData.getTargetCustomer());
        repository.save(work);
    }

    public void deleteById(String workId) {
        repository.deleteById(workId);
    }

    public boolean isWorkForCustomer(String workId, String customerId) {
//        Customer customer = (Customer) userService.findById(customerId);
        Work work = getById(workId);
//        if (customer.hasRole("ROLE_CUSTOMER_RETAIL") && !work.getTargetCustomer().equals("retail")) {
//            return false;
//        } else return !customer.hasRole("ROLE_CUSTOMER_CORPORATE") || work.getTargetCustomer().equals("corporate");

        if (!work.getTargetCustomer().equals("retail")) {
            return false;
        } else return work.getTargetCustomer().equals("corporate");
    }

    public List<Work> getWorksByProviderId(String providerId) {
        return repository.findByProviderId(providerId);
    }

    public List<Work> getRetailCustomerWorks() {
        return repository.findByTargetCustomer("retail");
    }

    public List<Work> getCorporateCustomerWorks() {
        return repository.findByTargetCustomer("corporate");
    }

    public List<Work> getWorksForRetailCustomerByProviderId(String providerId) {
        return repository.findByTargetCustomerAndProviderId("retail", providerId);
    }

    public List<Work> getWorksForCorporateCustomerByProviderId(String providerId) {
        return repository.findByTargetCustomerAndProviderId("corporate", providerId);
    }
}
