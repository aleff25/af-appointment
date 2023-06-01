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
@ApiModel(description = "Appointment with available hours")
public class AppointmentAvailableHoursDTO {

    private String customerId;

    @NotBlank(message = "{RegisterAppointmentDTO.providerId.NotBlank}")
    private String providerId;

    @NotBlank(message = "{RegisterAppointmentDTO.workId.NotBlank}")
    private String workId;

    @NotNull(message = "{RegisterAppointmentDTO.startDate.NotNull}")
    private LocalDateTime startDate;

    @NotNull(message = "{RegisterAppointmentDTO.endDate.NotNull}")
    private LocalDateTime endDate;

}
