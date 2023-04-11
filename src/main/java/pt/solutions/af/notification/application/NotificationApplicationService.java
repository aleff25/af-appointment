package pt.solutions.af.notification.application;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.email.application.EmailApplicationService;
import pt.solutions.af.invoice.model.Invoice;
import pt.solutions.af.notification.model.Notification;
import pt.solutions.af.notification.repository.NotificationRepository;
import pt.solutions.af.user.application.UserApplicationService;
import pt.solutions.af.user.model.User;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class NotificationApplicationService {

    private NotificationRepository repository;
    private EmailApplicationService emailService;
    private UserApplicationService userService;

    @Value("${mailing.enabled:true}")
    private boolean mailingEnabled;

    @Autowired
    public NotificationApplicationService(NotificationRepository repository, EmailApplicationService emailService, UserApplicationService userService) {
        this.repository = repository;
        this.emailService = emailService;
        this.userService = userService;
    }

    public void newNotification(String title, String message, String url, User user) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setUrl(url);
        notification.setCreatedAt(LocalDate.now());
        notification.setMessage(message);
        notification.setUser(user);
        repository.save(notification);
    }


    public void markAsRead(String notificationId, String userId) {
        Notification notification = repository.getReferenceById(notificationId);
        if (notification.getUserId().equals(userId)) {
            notification.setRead(true);
            repository.save(notification);
        }
//        else {
//            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
//        }
    }

    public void markAllAsRead(String userId) {
        List<Notification> notifications = repository.getAllUnreadNotifications(userId);
        for (Notification notification : notifications) {
            notification.setRead(true);
            repository.save(notification);
        }
    }

    public Notification getNotificationById(String notificationId) {
        return repository.getOne(notificationId);
    }

    public List<Notification> getAll(String userId) {
        return userService.findById(userId).getNotifications();
    }

    public List<Notification> getUnreadNotifications(String userId) {
        return repository.getAllUnreadNotifications(userId);
    }

    public void newAppointmentFinishedNotification(Appointment appointment, boolean sendEmail) {
        String title = "Appointment Finished";
        String message =
                "Appointment finished, you can reject that it took place until " + appointment.getEndDate().plusHours(24).toString();
        String url = "/appointments/" + appointment.getId();
        newNotification(title, message, url, appointment.getCustomer());
        if (sendEmail && mailingEnabled) {
            emailService.sendAppointmentFinishedNotification(appointment);
        }

    }

    public void newAppointmentRejectionRequestedNotification(Appointment appointment, boolean sendEmail) {
        String title = "Appointment Rejected";
        String message = appointment.getCustomer().getFirstName() + " " + appointment.getCustomer().getLastName() + "rejected an appointment. Your approval is required";
        String url = "/appointments/" + appointment.getId();
        newNotification(title, message, url, appointment.getProvider());
        if (sendEmail && mailingEnabled) {
            emailService.sendAppointmentRejectionRequestedNotification(appointment);
        }
    }

    public void newAppointmentScheduledNotification(Appointment appointment, boolean sendEmail) {
        String title = "New appointment scheduled";
        String message =
                "New appointment scheduled with" + appointment.getCustomer().getFirstName() + " " + appointment.getProvider().getLastName() + " on " + appointment.getStartDate().toString();
        String url = "/appointments/" + appointment.getId();
        newNotification(title, message, url, appointment.getProvider());
        if (sendEmail && mailingEnabled) {
            emailService.sendNewAppointmentScheduledNotification(appointment);
        }
    }

    public void newAppointmentCanceledByCustomerNotification(Appointment appointment, boolean sendEmail) {
        String title = "Appointment Canceled";
        String message = appointment.getCustomer().getFirstName() + " " + appointment.getCustomer().getLastName() +
                " cancelled appointment scheduled at " + appointment.getStartDate().toString();
        String url = "/appointments/" + appointment.getId();
        newNotification(title, message, url, appointment.getProvider());
        if (sendEmail && mailingEnabled) {
            emailService.sendAppointmentCanceledByCustomerNotification(appointment);
        }
    }

    public void newAppointmentCanceledByProviderNotification(Appointment appointment, boolean sendEmail) {
        String title = "Appointment Canceled";
        String message = appointment.getProvider().getFirstName() + " " + appointment.getProvider().getLastName() +
                " cancelled appointment scheduled at " + appointment.getStartDate().toString();
        String url = "/appointments/" + appointment.getId();
        newNotification(title, message, url, appointment.getCustomer());
        if (sendEmail && mailingEnabled) {
            emailService.sendAppointmentCanceledByProviderNotification(appointment);
        }
    }

    public void newInvoice(Invoice invoice, boolean sendEmail) {
        String title = "New invoice";
        String message = "New invoice has been issued for you";
        String url = "/invoices/" + invoice.getId();
        newNotification(title, message, url, invoice.getAppointments().get(0).getCustomer());
        if (sendEmail && mailingEnabled) {
            emailService.sendInvoice(invoice);
        }
    }

    public void newExchangeRequestedNotification(Appointment oldAppointment, Appointment newAppointment, boolean sendEmail) {
        String title = "Request for exchange";
        String message = "One of the users sent you a request to exchange his appointment with your appointment";
        String url = "/appointments/" + newAppointment.getId();
        newNotification(title, message, url, newAppointment.getCustomer());
        if (sendEmail && mailingEnabled) {
            emailService.sendNewExchangeRequestedNotification(oldAppointment, newAppointment);
        }
    }

//    public void newExchangeAcceptedNotification(ExchangeRequest exchangeRequest, boolean sendEmail) {
//        String title = "Exchange request accepted";
//        String message = "Someone accepted your appointment exchange request from " + exchangeRequest.getRequested().getStart() + " to " + exchangeRequest.getRequestor().getStart();
//        String url = "/appointments/" + exchangeRequest.getRequested();
//        newNotification(title, message, url, exchangeRequest.getRequested().getCustomer());
//        if (sendEmail && mailingEnabled) {
//            emailService.sendExchangeRequestAcceptedNotification(exchangeRequest);
//        }
//    }
//
//    public void newExchangeRejectedNotification(ExchangeRequest exchangeRequest, boolean sendEmail) {
//        String title = "Exchange request rejected";
//        String message = "Someone rejected your appointment exchange request from " + exchangeRequest.getRequestor().getStart() + " to " + exchangeRequest.getRequested().getStart();
//        String url = "/appointments/" + exchangeRequest.getRequestor();
//        newNotification(title, message, url, exchangeRequest.getRequestor().getCustomer());
//        if (sendEmail && mailingEnabled) {
//            emailService.sendExchangeRequestRejectedNotification(exchangeRequest);
//        }
//    }

    public void newAppointmentRejectionAcceptedNotification(Appointment appointment, boolean sendEmail) {
        String title = "Rejection accepted";
        String message = "You provider accepted your rejection request";
        String url = "/appointments/" + appointment.getId();
        newNotification(title, message, url, appointment.getCustomer());
        if (sendEmail && mailingEnabled) {
            emailService.sendAppointmentRejectionAcceptedNotification(appointment);
        }
    }

//    public void newChatMessageNotification(ChatMessage chatMessage, boolean sendEmail) {
//        String title = "New chat message";
//        String message = "You have new chat message from " + chatMessage.getAuthor().getFirstName() + " regarding appointment scheduled at " + chatMessage.getAppointment().getStart();
//        String url = "/appointments/" + chatMessage.getAppointment().getId();
//        newNotification(title, message, url, chatMessage.getAuthor() == chatMessage.getAppointment().getProvider() ? chatMessage.getAppointment().getCustomer() : chatMessage.getAppointment().getProvider());
//        if (sendEmail && mailingEnabled) {
//            emailService.sendNewChatMessageNotification(chatMessage);
//        }
//    }
}
