package pt.solutions.af.appointment.application.validations;

import org.springframework.stereotype.Component;
import pt.solutions.af.appointment.application.dto.RegisterAppointmentDTO;
import pt.solutions.af.commons.exception.ValidationException;

import java.time.DayOfWeek;

@Component
public class ValidatorBusinessOpeningHour implements AppointmentSchedulerValidator {

    public void validate(RegisterAppointmentDTO dto) {
        var appointmentDate = dto.getStartDate();

        var sunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeOpen = appointmentDate.getHour() < 7;
        var beforeClosed = appointmentDate.getHour() > 18;
        if (sunday || beforeOpen || beforeClosed) {
            throw new ValidationException("Consulta fora do horário de funcionamento da clínica");
        }

    }

}
