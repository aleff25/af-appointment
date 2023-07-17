package pt.solutions.af.exchange.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.appointment.model.AppointmentStatusEnum;
import pt.solutions.af.appointment.repository.AppointmentRepository;
import pt.solutions.af.exchange.model.ExchangeRequest;
import pt.solutions.af.exchange.repository.ExchangeRequestRepository;
import pt.solutions.af.exchange.model.ExchangeStatusEnum;
import pt.solutions.af.notification.application.NotificationApplicationService;
import pt.solutions.af.user.model.customer.Customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExchangeApplicationService {

    private final AppointmentRepository appointmentRepository;
    private final NotificationApplicationService notificationService;
    private final ExchangeRequestRepository repository;

    public boolean checkIfEligibleForExchange(String userId, String appointmentId) {
        Appointment appointment = appointmentRepository.getReferenceById(appointmentId);
        return appointment.getStartDate().minusHours(24).isAfter(LocalDateTime.now()) && appointment.getStatus().equals(AppointmentStatusEnum.SCHEDULED)
                && appointment.getCustomer().getId().equals(userId);
    }

    public List<Appointment> getEligibleAppointmentsForExchange(String appointmentId) {
        Appointment appointmentToExchange = appointmentRepository.getOne(appointmentId);
        return appointmentRepository.getEligibleAppointmentsForExchange(LocalDateTime.now().plusHours(24),
                appointmentToExchange.getCustomer().getId(), appointmentToExchange.getProvider().getId(),
                appointmentToExchange.getWork().getId());
    }

    public boolean checkIfExchangeIsPossible(String oldAppointmentId, String newAppointmentId, String userId) {
        Appointment oldAppointment = appointmentRepository.getReferenceById(oldAppointmentId);
        Appointment newAppointment = appointmentRepository.getReferenceById(newAppointmentId);
        if (Objects.equals(oldAppointment.getCustomer().getId(), userId)) {
            return oldAppointment.getWork().getId().equals(newAppointment.getWork().getId())
                    && oldAppointment.getProvider().getId().equals(newAppointment.getProvider().getId())
                    && oldAppointment.getStartDate().minusHours(24).isAfter(LocalDateTime.now())
                    && newAppointment.getStartDate().minusHours(24).isAfter(LocalDateTime.now());
        } else {
            throw new AccessDeniedException("Unauthorized");
        }
    }

    public boolean acceptExchange(String exchangeId, String userId) {
        ExchangeRequest exchangeRequest = repository.getReferenceById(exchangeId);
        Appointment requestor = exchangeRequest.getRequestor();
        Appointment requested = exchangeRequest.getRequested();
        Customer tempCustomer = requestor.getCustomer();
        requestor.setStatus(AppointmentStatusEnum.SCHEDULED);
        exchangeRequest.setStatus(ExchangeStatusEnum.ACCEPTED);
        requestor.setCustomer(requested.getCustomer());
        requested.setCustomer(tempCustomer);
        repository.save(exchangeRequest);
        appointmentRepository.save(requested);
        appointmentRepository.save(requestor);
        notificationService.newExchangeAcceptedNotification(exchangeRequest, true);
        return true;
    }

    public boolean rejectExchange(String exchangeId, String userId) {
        ExchangeRequest exchangeRequest = repository.getReferenceById(exchangeId);
        Appointment requestor = exchangeRequest.getRequestor();
        exchangeRequest.setStatus(ExchangeStatusEnum.REJECTED);
        requestor.setStatus(AppointmentStatusEnum.SCHEDULED);
        repository.save(exchangeRequest);
        appointmentRepository.save(requestor);
        notificationService.newExchangeRejectedNotification(exchangeRequest, true);
        return true;
    }

    public boolean requestExchange(String oldAppointmentId, String newAppointmentId, String userId) {
        if (checkIfExchangeIsPossible(oldAppointmentId, newAppointmentId, userId)) {
            Appointment oldAppointment = appointmentRepository.getReferenceById(oldAppointmentId);
            Appointment newAppointment = appointmentRepository.getReferenceById(newAppointmentId);
            oldAppointment.setStatus(AppointmentStatusEnum.EXCHANGE_REQUESTED);
            appointmentRepository.save(oldAppointment);

            ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                    .requestor(oldAppointment)
                    .requested(newAppointment)
                    .status(ExchangeStatusEnum.PENDING)
                    .build();

            repository.save(exchangeRequest);
            notificationService.newExchangeRequestedNotification(oldAppointment, newAppointment, true);
            return true;
        }
        return false;
    }
}
