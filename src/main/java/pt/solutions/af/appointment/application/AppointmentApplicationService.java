package pt.solutions.af.appointment.application;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.solutions.af.appointment.application.dto.RegisterAppointmentDTO;
import pt.solutions.af.appointment.application.validations.AppointmentSchedulerValidator;
import pt.solutions.af.appointment.exception.AppointmentNotFoundException;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.appointment.model.AppointmentListView;
import pt.solutions.af.appointment.model.AppointmentStatusEnum;
import pt.solutions.af.appointment.repository.AppointmentRepository;
import pt.solutions.af.chat.model.ChatMessage;
import pt.solutions.af.chat.repository.ChatMessageRepository;
import pt.solutions.af.notification.application.NotificationApplicationService;
import pt.solutions.af.sms.application.SmsService;
import pt.solutions.af.user.application.UserApplicationService;
import pt.solutions.af.user.model.customer.Customer;
import pt.solutions.af.user.model.provider.Provider;
import pt.solutions.af.work.application.WorkApplicationService;
import pt.solutions.af.work.model.Work;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class AppointmentApplicationService {

    private AppointmentRepository repository;

    private ChatMessageRepository chatMessageRepository;

    private UserApplicationService userService;

    private WorkApplicationService workService;

    private NotificationApplicationService notificationService;

    private SmsService smsService;

    private List<AppointmentSchedulerValidator> shedulingValidators;

    public Appointment getAppointmentById(String id) {
        return repository.findById(id)
                .orElseThrow(AppointmentNotFoundException::new);
    }

    public List<Appointment> getAppointmentByProviderId(String providerId) {
        return repository.findAllByProviderId(providerId);
    }

    public List<Appointment> getAppointmentByCustomerId(String customerId) {
        return repository.findAllByCustomerId(customerId);
    }


    @Transactional
    public void create(RegisterAppointmentDTO dto) {

        shedulingValidators.forEach(validator -> validator.validate(dto));

        Work work = workService.getById(dto.getWorkId());
        Customer customer;
        if (StringUtils.isNotBlank(dto.getCustomerId())) {
            customer = userService.getCustomerById(dto.getCustomerId());
        } else {
            customer = new Customer();
            customer.setFirstName(dto.getFirstName());
            customer.setLastName(dto.getLastName());
        }

        Provider provider = userService.getProviderById(dto.getProviderId());
        Appointment appointment = Appointment.builder()
                .customer(customer)
                .provider(provider)
                .work(work)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();

        appointment.newAppointment();

        log.info("Registering a new appointment Appointment=[{}]", appointment.toString());
        repository.save(appointment);
        notificationService.newAppointmentScheduledNotification(appointment, true);

        // FIXME: Paid Service
//        String message = String.format("Hello, you have an appointment scheduled for Service:%s - Price:%s - " +
//                        "Time:%s. ",
//                work.getName(), work.getPrice(), appointment.getStartDate().toString());
//        SmsRequest smsRequest = new SmsRequest(customer.getPhoneNumber(), message);
//        smsService.sendSms(smsRequest);
    }

    public void updateAllAppointmentsStatuses() {
        repository.findScheduledWithEndBeforeDate(LocalDateTime.now())
                .forEach(appointment -> {
                    appointment.setStatus(AppointmentStatusEnum.FINISHED);
                    update(appointment);
                    if (LocalDateTime.now().minusDays(1).isBefore(appointment.getEndDate())) {
                        notificationService.newAppointmentFinishedNotification(appointment, true);
                    }
                });

        repository.findFinishedWithEndBeforeDate(LocalDateTime.now().minusDays(1))
                .forEach(appointment -> {
                    appointment.setStatus(AppointmentStatusEnum.CONFIRMED);
                    update(appointment);
                });
    }


    public void update(Appointment appointment) {
        repository.save(appointment);
    }

    public List<Appointment> getConfirmedAppointmentsByCustomerId(String customerId) {
        return repository.findConfirmedByCustomerId(customerId);
    }

    public List<AppointmentListView> list(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.getAllBetweenDates(startDate, endDate);
    }

    public void addMessageToAppointmentChat(String appointmentId, String authorId, ChatMessage chatMessage) {
        Appointment appointment = getAppointmentById(appointmentId);
        if (appointment.getProvider().getId().equals(authorId) || appointment.getCustomer().getId().equals(authorId)) {
            chatMessage.setAuthor(userService.findById(authorId));
            chatMessage.setAppointment(appointment);
            chatMessage.setCreatedAt(LocalDateTime.now());
            chatMessageRepository.save(chatMessage);
            notificationService.newChatMessageNotification(chatMessage, true);
        } else {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
    }

    public void updateAppointmentsStatusesWithExpiredExchangeRequest() {
        repository.findExchangeRequestWithStartBefore(LocalDateTime.now().plusDays(1))
                .forEach(appointment -> {
                    appointment.setStatus(AppointmentStatusEnum.SCHEDULED);
                    update(appointment);
                });
    }
}
