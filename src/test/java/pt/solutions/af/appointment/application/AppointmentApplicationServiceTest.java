package pt.solutions.af.appointment.application;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import pt.solutions.af.appointment.application.dto.RegisterAppointmentDTO;
import pt.solutions.af.commons.exception.ValidationException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pt.solutions.af.TestUtls.randomLong;
import static pt.solutions.af.TestUtls.randomUUID;
import static pt.solutions.af.appointment.AppointmentTestUtils.registerAppointment;
import static pt.solutions.af.user.UserTestUtils.registerProvider;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@RequiredArgsConstructor
class AppointmentApplicationServiceTest {

    private final AppointmentApplicationService service;
    private final TestEntityManager em;

    @Test
    @DisplayName("Should not create appointment in sundays")
    public void shouldNotCreateAppointmentOnSundayTest() {
        //given

        var startDate = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        var endDate = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        //when

        var exception = assertThrows(ValidationException.class, () -> service.create(newAppointment(startDate, endDate)));

        //then
        assertThat(exception.getMessage()).isEqualTo("Consulta fora do horário de funcionamento do estabelecimento");
    }

    @Test
    @DisplayName("Should not create appointment in business hour is closed")
    public void shouldNotCreateAppointmentWhenBusinessHourIsClosedTest() {
        //given

        var startDate = LocalDate.now().atTime(LocalTime.of(20, 0));
        var endDate = startDate.plusMinutes(30);
        //when

        var exception = assertThrows(ValidationException.class, () -> service.create(newAppointment(startDate, endDate)));

        //then
        assertThat(exception.getMessage()).isEqualTo("Consulta fora do horário de funcionamento do estabelecimento");
    }


    @Test
    @DisplayName("Should not create appointment in with less than 30 min before")
    public void shouldNotCreateAppointmentWithLessThan30MinBeforeTest() {
        //given

        var startDate = LocalDateTime.now().plusMinutes(15);
        var endDate = LocalDateTime.now().plusHours(1);

        //when
        var exception = assertThrows(ValidationException.class, () -> service.create(newAppointment(startDate, endDate)));

        //then
        assertThat(exception.getMessage()).isEqualTo("Consulta deve ser agendada com antecedência mínima de 30 minutos");
    }

    @Test
    @DisplayName("Should not create appointment unavailable")
    public void shouldNotCreateAppointmentNotAvailableTest() {
        //given

        var provider = registerProvider(em, "12345","Provider", "Test", "provider@provider.com");
        var appointment = registerAppointment(em, provider);
        var startDate = LocalDateTime.now().plusMinutes(30);
        var endDate = LocalDateTime.now().plusHours(1);

        //when
        var exception = assertThrows(ValidationException.class, () -> service.create(newAppointment(provider.getId(), startDate, endDate)));

        //then
        assertThat(exception.getMessage()).isEqualTo("Provedor já possui outra consulta agendada nesse mesmo horário");
    }


    private static RegisterAppointmentDTO newAppointment() {
        return RegisterAppointmentDTO.of(
                randomUUID(),
                randomUUID(),
                LocalDateTime.now().plusMinutes(30),
                LocalDateTime.now().plusHours(1),
                false,
                "Customer",
                "Doe",
                randomUUID(),
                randomLong().toString()
        );
    }

    private static RegisterAppointmentDTO newAppointment(LocalDateTime start, LocalDateTime end) {
        return RegisterAppointmentDTO.of(
                randomUUID(),
                randomUUID(),
                start,
                end,
                false,
                "Customer",
                "Doe",
                randomUUID(),
                randomLong().toString()
        );
    }

    private static RegisterAppointmentDTO newAppointment(String providerId, LocalDateTime start, LocalDateTime end) {
        return RegisterAppointmentDTO.of(
                randomUUID(),
                providerId,
                start,
                end,
                false,
                "Customer",
                "Doe",
                randomUUID(),
                randomLong().toString()
        );
    }

}