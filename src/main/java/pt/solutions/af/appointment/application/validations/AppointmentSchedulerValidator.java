package pt.solutions.af.appointment.application.validations;

import pt.solutions.af.appointment.application.dto.RegisterAppointmentDTO;

public interface AppointmentSchedulerValidator {

    void validate(RegisterAppointmentDTO dto);

}
