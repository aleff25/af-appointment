package pt.solutions.af.user.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.solutions.af.commons.entity.CollectionResponse;
import pt.solutions.af.user.application.UserApplicationService;
import pt.solutions.af.user.application.dto.CreateCustomerDTO;
import pt.solutions.af.user.application.dto.CreateProviderDto;
import pt.solutions.af.user.model.customer.CustomerListView;
import pt.solutions.af.user.model.provider.ProviderListView;


@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserApplicationService service;

    @PostMapping()
    public ResponseEntity<Void> create(@Valid @RequestBody CreateProviderDto dto) {
        service.saveNewProvider(dto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/customers")
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CreateCustomerDTO dto) {
        service.saveNewRetailCustomer(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/providers")
    public ResponseEntity<CollectionResponse<ProviderListView>> listProviders() {
        var providers = service.getAllProviders();
        var response = new CollectionResponse<>(false, providers);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customers")
    public ResponseEntity<CollectionResponse<CustomerListView>> listCustomers() {
        var customers = service.getAllCustomers();
        var response = new CollectionResponse<>(false, customers);
        return ResponseEntity.ok(response);
    }
}
