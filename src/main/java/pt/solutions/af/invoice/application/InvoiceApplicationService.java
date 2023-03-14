package pt.solutions.af.invoice.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.solutions.af.appointment.application.AppointmentApplicationService;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.invoice.model.Invoice;
import pt.solutions.af.invoice.repository.InvoiceRepository;
import pt.solutions.af.notification.application.NotificationApplicationService;
import pt.solutions.af.user.application.UserApplicationService;
import pt.solutions.af.user.model.User;
import pt.solutions.af.user.model.customer.Customer;
import pt.solutions.af.utils.PdfGeneratorUtil;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class InvoiceApplicationService {

    private final InvoiceRepository repository;
    private final PdfGeneratorUtil pdfGeneratorUtil;
    private final AppointmentApplicationService appointmentService;
    private final NotificationApplicationService notificationService;
    private final UserApplicationService userService;

    public String generateInvoiceNumber() {
        List<Invoice> invoices =
                repository.findAllIssuedInCurrentMonth(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay());
        int nextInvoiceNumber = invoices.size() + 1;
        LocalDateTime today = LocalDateTime.now();
        return "FV/" + today.getYear() + "/" + today.getMonthValue() + "/" + nextInvoiceNumber;
    }

    public void createNewInvoice(Invoice invoice) {
        repository.save(invoice);
    }

    public Invoice getInvoiceByAppointmentId(String appointmentId) {
        return repository.findByAppointmentId(appointmentId);
    }

    public Invoice getInvoiceById(String invoiceId) {
        return repository.findById(invoiceId)
                .orElseThrow(RuntimeException::new);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public List<Invoice> getAllInvoices() {
        return repository.findAll();
    }

    public File generatePdfForInvoice(String invoiceId) {
//        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Invoice invoice = repository.getReferenceById(invoiceId);
//        if (!isUserAllowedToDownloadInvoice(currentUser, invoice)) {
//            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
//        }
        return pdfGeneratorUtil.generatePdfFromInvoice(invoice);
    }

    public boolean isUserAllowedToDownloadInvoice(User user, Invoice invoice) {
        String userId = user.getId();
//        if (user.hasRole("ROLE_ADMIN")) {
//            return true;
//        }
        for (Appointment a : invoice.getAppointments()) {
            if (a.getProvider().getId().equals(userId) || a.getCustomer().getId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public void changeInvoiceStatusToPaid(String invoiceId) {
        Invoice invoice = repository.getReferenceById(invoiceId);
        invoice.setStatus("paid");
        repository.save(invoice);
    }

    @Transactional
    public void issueInvoicesForConfirmedAppointments() {
        List<Customer> customers = userService.getAllCustomers();
        for (Customer customer : customers) {
            List<Appointment> appointmentsToIssueInvoice = appointmentService.getConfirmedAppointmentsByCustomerId(customer.getId());
            if (!appointmentsToIssueInvoice.isEmpty()) {
                for (Appointment appointment : appointmentsToIssueInvoice) {
                    appointment.invoiceAppointment();
                    appointmentService.update(appointment);
                }
                Invoice invoice = Invoice.builder()
                        .number(generateInvoiceNumber())
                        .status("issued")
                        .issued(LocalDateTime.now())
                        .appointments(appointmentsToIssueInvoice)
                        .build();
                repository.save(invoice);
                notificationService.newInvoice(invoice, true);
            }

        }
    }
}
