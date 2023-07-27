package pt.solutions.af.appointment.application.validations;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pt.solutions.af.appointment.application.dto.RegisterAppointmentDTO;
import pt.solutions.af.appointment.repository.AppointmentRepository;
import pt.solutions.af.commons.exception.ValidationException;

@Component
@AllArgsConstructor
public class ProviderWithOtherAppointmentAtSameTimeValidator implements AppointmentSchedulerValidator {

    private AppointmentRepository repository;

    public void validate(RegisterAppointmentDTO dto) {
        var providerAlreadyHasOtherAppointment = repository.existsByProviderWithStartInPeriod(dto.getProviderId(),
                dto.getStartDate(), dto.getEndDate());
        if (providerAlreadyHasOtherAppointment) {
            throw new ValidationException("Provedor já possui outra consulta agendada nesse mesmo horário");
        }
    }

}
