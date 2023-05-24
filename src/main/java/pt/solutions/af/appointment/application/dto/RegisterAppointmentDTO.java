package pt.solutions.af.appointment.application.dto;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@ApiModel(description = "Basic information of the user")
public class RegisterAppointmentDTO {

    @NotBlank(message = "{RegisterAppointmentDTO.workId.NotBlank}")
    private String workId;

    @NotBlank(message = "{RegisterAppointmentDTO.providerId.NotBlank}")
    private String providerId;

    @NotBlank(message = "{RegisterAppointmentDTO.firstName.NotBlank}")
    private String firstName;

    @NotBlank(message = "{RegisterAppointmentDTO.lastName.NotBlank}")
    private String lastName;

    @NotNull(message = "{RegisterAppointmentDTO.date.NotNull}")
    private String date;

    @NotNull(message = "{RegisterAppointmentDTO.timeStart.NotNull}")
    private String timeStart;

    @NotNull(message = "{RegisterAppointmentDTO.timeEnd.NotNull}")
    private String timeEnd;

    private String customerId;
    private String nif;
}

