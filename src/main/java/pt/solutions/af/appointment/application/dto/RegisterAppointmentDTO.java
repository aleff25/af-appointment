package pt.solutions.af.appointment.application.dto;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@ApiModel(description = "Create an appointment")
public class RegisterAppointmentDTO {

    @NotBlank(message = "{RegisterAppointmentDTO.workId.NotBlank}")
    private String workId;

    @NotBlank(message = "{RegisterAppointmentDTO.providerId.NotBlank}")
    private String providerId;

    @NotNull(message = "{RegisterAppointmentDTO.date.NotNull}")
    private LocalDateTime startDate;

    @NotNull(message = "{RegisterAppointmentDTO.date.NotNull}")
    private LocalDateTime endDate;

    private boolean newClient;

    private String firstName;

    private String lastName;

    private String customerId;

    private String nif;
}

