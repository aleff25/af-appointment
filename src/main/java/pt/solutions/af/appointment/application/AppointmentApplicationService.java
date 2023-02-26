package pt.solutions.af.appointment.application;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.solutions.af.appointment.application.dto.RegisterAppointmentDTO;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.appointment.model.ServiceType;
import pt.solutions.af.appointment.repository.AppointmentRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@AllArgsConstructor
@Log4j2
public class AppointmentApplicationService {

    private AppointmentRepository repository;

    public void register(RegisterAppointmentDTO dto) {

        Appointment appointment = Appointment.builder()
                .clientId(dto.getClientId())
                .providerId(dto.getProviderId())
                .serviceType(ServiceType.valueOf(dto.getServiceType()))
                .startDate(LocalDateTime.ofInstant(dto.getStartDate(), ZoneId.systemDefault()))
                .endDate(LocalDateTime.ofInstant(dto.getEndDate(), ZoneId.systemDefault()))
                .build();

        log.info("Registering a new appointment Appointment=[{}]", appointment.toString());

        repository.save(appointment);
    }
}
