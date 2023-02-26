package pt.solutions.af.appointment.application.dto;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pt.solutions.af.appointment.model.ServiceType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@ApiModel(description = "Basic information of the user")
public class RegisterAppointmentDTO {

    @NotBlank(message = "{RegisterAppointmentDTO.clientId.NotBlank}")
    private String clientId;

    @NotBlank(message = "{RegisterAppointmentDTO.providerId.NotBlank}")
    private String providerId;

    @NotNull(message = "{RegisterAppointmentDTO.startDate.NotNull}")
    private Instant startDate;

    @NotNull(message = "{RegisterAppointmentDTO.endDate.NotNull}")
    private Instant endDate;

    @NotBlank(message = "{RegisterAppointmentDTO.serviceType.NotBlank}")
    private String serviceType;

}

