package pt.solutions.af.appointment.application.validations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pt.solutions.af.commons.exception.ValidationException;
import pt.solutions.af.util.ApplicationConfigTest;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pt.solutions.af.appointment.AppointmentTestUtils.anAppointment;

class BusinessOpeningHourValidatorTest extends ApplicationConfigTest {

    @Autowired
    private BusinessOpeningHourValidator validator;

    @Test
    @DisplayName("Should not create appointment on sundays")
    public void shouldNotCreateAppointmentOnSundayTest() {
        //given

        var startDate = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        var endDate = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        //when

        var exception = assertThrows(ValidationException.class, () -> validator.validate(anAppointment(startDate,
                endDate)));

        //then
        assertThat(exception.getMessage()).isEqualTo("Consulta fora do horário de funcionamento do estabelecimento");
    }

    @Test
    @DisplayName("Should create appointment in week days")
    public void shouldCreateAppointmentOnWeekTest() {
        //given

        var clock = Clock.fixed(Instant.parse("2023-12-22T10:15:30.00Z"), ZoneId.of("UTC"));
        var startDate = LocalDateTime.now(clock).with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        var endDate = LocalDateTime.now(clock).with(TemporalAdjusters.next(DayOfWeek.FRIDAY));

        //when then
        assertDoesNotThrow(() -> validator.validate(anAppointment(startDate, endDate)));
    }

}