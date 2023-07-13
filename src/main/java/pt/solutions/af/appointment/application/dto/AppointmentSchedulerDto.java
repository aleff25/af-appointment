package pt.solutions.af.appointment.application.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentSchedulerDto(

        String providerId,
        @NotNull
        String customerId,

        @NotNull
        @Future
        LocalDateTime startDate,

        @NotNull
        @Future
        LocalDateTime endDate) {
}
