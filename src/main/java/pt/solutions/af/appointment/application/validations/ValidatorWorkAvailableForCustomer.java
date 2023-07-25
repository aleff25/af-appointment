package pt.solutions.af.appointment.application.validations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pt.solutions.af.appointment.application.dto.RegisterAppointmentDTO;
import pt.solutions.af.commons.exception.ValidationException;
import pt.solutions.af.work.application.WorkApplicationService;

@Component("ValidatorWorkAvailableForCustomer")
@AllArgsConstructor
public class ValidatorWorkAvailableForCustomer implements AppointmentSchedulerValidator {

    private WorkApplicationService workService;

    @Override
    public void validate(RegisterAppointmentDTO dto) {
        if (!workService.isWorkForCustomer(dto.getWorkId(), dto.getCustomerId())) {
            throw new ValidationException("Agendamento não permitido deste tipo de serviço não permitido para o " +
                    "cliente");
        }
    }
}
