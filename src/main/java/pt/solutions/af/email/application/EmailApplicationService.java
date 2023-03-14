package pt.solutions.af.email.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.invoice.model.Invoice;
import pt.solutions.af.utils.PdfGeneratorUtil;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
@Slf4j
public class EmailApplicationService {

    private JavaMailSender javaMailSender;
    private SpringTemplateEngine templateEngine;
    private PdfGeneratorUtil pdfGeneratorUtil;
    private String baseUrl;

    @Async
    public void sendEmail(String to, String subject, String templateName, Context templateContext, File attachment) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            String html = templateEngine.process("email/" + templateName, templateContext);

            helper.setTo(to);
            helper.setFrom("aesrodriguesoliveira@gmail.com");
            helper.setSubject(subject);
            helper.setText(html, true);

            if (attachment != null) {
                helper.addAttachment("invoice", attachment);
            }

            javaMailSender.send(message);

        } catch (MessagingException e) {
            log.error("Error while adding attachment to email, error is {}", e.getLocalizedMessage());
        }

    }

    @Async
    public void sendAppointmentFinishedNotification(Appointment appointment) {
        Context context = new Context();
        context.setVariable("appointment", appointment);
//        context.setVariable("url", baseUrl + "/appointments/reject?token=" + jwtTokenService.generateAppointmentRejectionToken(appointment));
        context.setVariable("url", baseUrl + "/appointments/reject");
        sendEmail(appointment.getCustomer().getEmail(), "Finished appointment summary", "appointmentFinished", context, null);
    }

    @Async
    public void sendAppointmentRejectionRequestedNotification(Appointment appointment) {
        Context context = new Context();
        context.setVariable("appointment", appointment);
//        context.setVariable("url", baseUrl + "/appointments/acceptRejection?token=" + jwtTokenService.generateAcceptRejectionToken(appointment));
        context.setVariable("url", baseUrl + "/appointments/acceptRejection");
        sendEmail(appointment.getProvider().getEmail(), "Rejection requested", "appointmentRejectionRequested", context, null);
    }

    @Async
    public void sendNewAppointmentScheduledNotification(Appointment appointment) {
        Context context = new Context();
        context.setVariable("appointment", appointment);
        sendEmail(appointment.getProvider().getEmail(), "New appointment booked", "newAppointmentScheduled", context, null);
    }

    @Async
    public void sendAppointmentCanceledByCustomerNotification(Appointment appointment) {
        Context context = new Context();
        context.setVariable("appointment", appointment);
        context.setVariable("canceler", "customer");
        sendEmail(appointment.getProvider().getEmail(), "Appointment canceled by Customer", "appointmentCanceled", context, null);
    }

    @Async
    public void sendAppointmentCanceledByProviderNotification(Appointment appointment) {
        Context context = new Context();
        context.setVariable("appointment", appointment);
        context.setVariable("canceler", "provider");
        sendEmail(appointment.getCustomer().getEmail(), "Appointment canceled by Provider", "appointmentCanceled", context, null);
    }

    @Async
    public void sendInvoice(Invoice invoice) {
        Context context = new Context();
        context.setVariable("customer", invoice.getAppointments().get(0).getCustomer().getFirstName() + " " + invoice.getAppointments().get(0).getCustomer().getLastName());
        try {
            File invoicePdf = pdfGeneratorUtil.generatePdfFromInvoice(invoice);
            sendEmail(invoice.getAppointments().get(0).getCustomer().getEmail(), "Appointment invoice", "appointmentInvoice", context, invoicePdf);
        } catch (Exception e) {
            log.error("Error while generating pdf, error is {}", e.getLocalizedMessage());
        }

    }

    @Async
    public void sendAppointmentRejectionAcceptedNotification(Appointment appointment) {
        Context context = new Context();
        context.setVariable("appointment", appointment);
        sendEmail(appointment.getCustomer().getEmail(), "Rejection request accepted", "appointmentRejectionAccepted", context, null);
    }

//    @Async
//    public void sendNewChatMessageNotification(ChatMessage chatMessage) {
//        Context context = new Context();
//        User recipent = chatMessage.getAuthor() == chatMessage.getAppointment().getProvider() ? chatMessage.getAppointment().getCustomer() : chatMessage.getAppointment().getProvider();
//        context.setVariable("recipent", recipent);
//        context.setVariable("appointment", chatMessage.getAppointment());
//        context.setVariable("url", baseUrl + "/appointments/" + chatMessage.getAppointment().getId());
//        sendEmail(recipent.getEmail(), "New chat message", "newChatMessage", context, null);
//    }

    @Async
    public void sendNewExchangeRequestedNotification(Appointment oldAppointment, Appointment newAppointment) {
        Context context = new Context();
        context.setVariable("oldAppointment", oldAppointment);
        context.setVariable("newAppointment", newAppointment);
        context.setVariable("url", baseUrl + "/appointments/" + newAppointment.getId());
        sendEmail(newAppointment.getCustomer().getEmail(), "New Appointment Exchange Request", "newExchangeRequest", context, null);
    }

//    public void sendExchangeRequestAcceptedNotification(ExchangeRequest exchangeRequest) {
//        Context context = new Context();
//        context.setVariable("exchangeRequest", exchangeRequest);
//        context.setVariable("url", baseUrl + "/appointments/" + exchangeRequest.getRequested().getId());
//        sendEmail(exchangeRequest.getRequested().getCustomer().getEmail(), "Exchange request accepted", "exchangeRequestAccepted", context, null);
//    }
//
//    public void sendExchangeRequestRejectedNotification(ExchangeRequest exchangeRequest) {
//        Context context = new Context();
//        context.setVariable("exchangeRequest", exchangeRequest);
//        context.setVariable("url", baseUrl + "/appointments/" + exchangeRequest.getRequestor().getId());
//        sendEmail(exchangeRequest.getRequestor().getCustomer().getEmail(), "Exchange request rejected", "exchangeRequestRejected", context, null);
//    }
}
