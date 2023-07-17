package pt.solutions.af.infra.schedule;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pt.solutions.af.appointment.application.AppointmentApplicationService;
import pt.solutions.af.invoice.application.InvoiceApplicationService;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final AppointmentApplicationService appointmentService;
    private final InvoiceApplicationService invoiceService;


    @Scheduled(fixedDelay = 30 * 60 * 100)  // every 30 min
    public void updateAllAppointmentStatuses() {
        appointmentService.updateAppointmentsStatusesWithExpiredExchangeRequest();
        appointmentService.updateAllAppointmentsStatuses();
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void issuesInvoicesForCurrentMonth() {
        invoiceService.issueInvoicesForConfirmedAppointments();
    }
}
