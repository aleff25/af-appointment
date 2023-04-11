package pt.solutions.af.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.solutions.af.appointment.application.AppointmentApplicationService;
import pt.solutions.af.appointment.application.dto.RegisterAppointmentDTO;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentApplicationService service;

    @PostMapping()
    public ResponseEntity<Void> create(@Valid @RequestBody RegisterAppointmentDTO dto) {
        service.create(dto);

        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Void> updateAllAppointmentStatuses() {
        service.updateAllAppointmentsStatuses();
        return ResponseEntity.ok().build();
    }
}
