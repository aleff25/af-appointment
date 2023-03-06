package pt.solutions.af.serviceType.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.solutions.af.appointment.application.dto.RegisterAppointmentDTO;
import pt.solutions.af.serviceType.application.ServiceTypeApplicationService;
import pt.solutions.af.serviceType.application.dto.RegisterServiceTypeDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/services-type")
public class ServiceTypeController {

    @Autowired
    private ServiceTypeApplicationService service;

    @PostMapping()
    public ResponseEntity<Void> create(@Valid @RequestBody RegisterServiceTypeDTO dto) {
        service.register(dto);

        return ResponseEntity.ok().build();
    }

}