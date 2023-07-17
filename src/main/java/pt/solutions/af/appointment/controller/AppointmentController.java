package pt.solutions.af.appointment.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pt.solutions.af.appointment.application.AppointmentApplicationService;
import pt.solutions.af.appointment.application.dto.RegisterAppointmentDTO;
import pt.solutions.af.appointment.model.AppointmentListView;
import pt.solutions.af.chat.model.ChatMessage;
import pt.solutions.af.commons.entity.CollectionResponse;
import pt.solutions.af.user.model.auth.AuthUserView;

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

    @PostMapping("/messages/new")
    public String addNewChatMessage(@ModelAttribute("chatMessage") ChatMessage chatMessage, @RequestParam("appointmentId") String appointmentId, @AuthenticationPrincipal AuthUserView currentUser) {
        String authorId = currentUser.getId();
        service.addMessageToAppointmentChat(appointmentId, authorId, chatMessage);
        return "redirect:/appointments/" + appointmentId;
    }
}
