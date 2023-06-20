package pt.solutions.af.appointment.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.solutions.af.appointment.application.AppointmentApplicationService;
import pt.solutions.af.appointment.application.dto.RegisterAppointmentDTO;
import pt.solutions.af.appointment.model.AppointmentListView;
import pt.solutions.af.commons.entity.CollectionResponse;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentApplicationService service;


    @GetMapping()
    public ResponseEntity<CollectionResponse<AppointmentListView>> list(@RequestParam LocalDateTime startDate,
                                                                        @RequestParam LocalDateTime endDate) {
        var appointments = service.list(startDate, endDate);
        var response = new CollectionResponse(false, appointments);
        return ResponseEntity.ok(response);
    }

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
