package pt.solutions.af.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pt.solutions.af.appointment.application.AppointmentApplicationService;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.exchange.application.ExchangeApplicationService;
import pt.solutions.af.user.model.auth.AuthUserView;

import java.util.List;

@Controller
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeApplicationService exchangeService;
    private final AppointmentApplicationService appointmentService;

    @GetMapping("/{oldAppointmentId}")
    public String showEligibleAppointmentsToExchange(@PathVariable("oldAppointmentId") String oldAppointmentId, Model model) {
        List<Appointment> eligibleAppointments = exchangeService.getEligibleAppointmentsForExchange(oldAppointmentId);
        model.addAttribute("appointmentId", oldAppointmentId);
        model.addAttribute("numberOfEligibleAppointments", eligibleAppointments.size());
        model.addAttribute("eligibleAppointments", eligibleAppointments);
        return "exchange/listProposals";
    }

    @GetMapping("/{oldAppointmentId}/{newAppointmentId}")
    public String showExchangeSummaryScreen(@PathVariable("oldAppointmentId") String oldAppointmentId,
                                            @PathVariable("newAppointmentId") String newAppointmentId, Model model,
                                            @AuthenticationPrincipal AuthUserView currentUser) {
        if (exchangeService.checkIfExchangeIsPossible(oldAppointmentId, newAppointmentId, currentUser.getId())) {
            model.addAttribute("oldAppointment", appointmentService.getAppointmentById(oldAppointmentId));
            model.addAttribute("newAppointment", appointmentService.getAppointmentById(newAppointmentId));
        } else {
            return "redirect:/appointments/all";
        }

        return "exchange/exchangeSummary";
    }

    @PostMapping()
    public String processExchangeRequest(@RequestParam("oldAppointmentId") String oldAppointmentId,
                                         @RequestParam("newAppointmentId") String newAppointmentId,
                                         Model model,
                                         @AuthenticationPrincipal AuthUserView currentUser) {
        boolean result = exchangeService.requestExchange(oldAppointmentId, newAppointmentId, currentUser.getId());
        if (result) {
            model.addAttribute("message", "Exchange request successfully sent!");
        } else {
            model.addAttribute("message", "Error! Exchange not sent!");
        }
        return "exchange/requestConfirmation";
    }

    @PostMapping("/accept")
    public String processExchangeAcceptation(@RequestParam("exchangeId") String exchangeId, Model model, @AuthenticationPrincipal AuthUserView currentUser) {
        exchangeService.acceptExchange(exchangeId, currentUser.getId());
        return "redirect:/appointments/all";
    }

    @PostMapping("/reject")
    public String processExchangeRejection(@RequestParam("exchangeId") String exchangeId, Model model, @AuthenticationPrincipal AuthUserView currentUser) {
        exchangeService.rejectExchange(exchangeId, currentUser.getId());
        return "redirect:/appointments/all";
    }
}
