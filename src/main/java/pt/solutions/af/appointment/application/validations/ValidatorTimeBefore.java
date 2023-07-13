package pt.solutions.af.appointment.application.validations;

import org.springframework.stereotype.Component;
import pt.solutions.af.appointment.application.dto.RegisterAppointmentDTO;
import pt.solutions.af.commons.exception.ValidationException;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidatorTimeBefore")
public class ValidatorTimeBefore implements AppointmentSchedulerValidator {

    public void validate(RegisterAppointmentDTO dto) {
        var dataConsulta = dto.getStartDate();
        var dateNow = LocalDateTime.now();
        var diffInMinutes = Duration.between(dateNow, dataConsulta).toMinutes();

        if (diffInMinutes < 30) {
            throw new ValidationException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }

    }
}
