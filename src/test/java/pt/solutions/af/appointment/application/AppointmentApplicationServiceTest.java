package pt.solutions.af.appointment.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.solutions.af.appointment.application.validations.ProviderAvailableValidator;
import pt.solutions.af.appointment.application.validations.ProviderWithOtherAppointmentAtSameTimeValidator;
import pt.solutions.af.appointment.application.validations.WorkAvailableForCustomerValidator;
import pt.solutions.af.appointment.repository.AppointmentRepository;
import pt.solutions.af.commons.exception.ValidationException;
import pt.solutions.af.util.ApplicationConfigIT;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static pt.solutions.af.appointment.AppointmentTestUtils.anAppointment;
import static pt.solutions.af.user.ProviderTestUtils.aProvider;

class AppointmentApplicationServiceTest extends ApplicationConfigIT {

    @Autowired
    private AppointmentApplicationService service;

    @Autowired
    private AppointmentRepository repository;

    @MockBean
    private ProviderAvailableValidator providerAvailableValidator;

    @MockBean
    private ProviderWithOtherAppointmentAtSameTimeValidator providerWithOtherAppointmentAtSameTimeValidator;

    @MockBean
    private WorkAvailableForCustomerValidator workAvailableForCustomerValidator;

    @Test
    @DisplayName("Should not create appointment in sundays")
    public void shouldNotCreateAppointmentOnSundayTest() {
        //given

        var startDate = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        var endDate = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        //when

        var exception = assertThrows(ValidationException.class, () -> service.create(anAppointment(startDate,
                endDate)));

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

        var exception = assertThrows(ValidationException.class, () -> service.create(anAppointment(startDate,
                endDate)));

        //then
        assertThat(exception.getMessage()).isEqualTo("Consulta fora do horário de funcionamento do estabelecimento");
    }


    @Test
    @DisplayName("Should not create appointment in with less than 30 min before")
    public void shouldNotCreateAppointmentWithLessThan30MinBeforeTest() {
        //given
        var clock = Clock.fixed(Instant.parse("2023-12-22T10:15:30.00Z"), ZoneId.of("UTC"));
        var startDate = LocalDateTime.now(clock).plusMinutes(15);
        var endDate = LocalDateTime.now().plusHours(1);

        doNothing().when(providerAvailableValidator).validate(any());
        doNothing().when(providerWithOtherAppointmentAtSameTimeValidator).validate(any());
        doNothing().when(workAvailableForCustomerValidator).validate(any());

        //when
        var exception = assertThrows(ValidationException.class, () -> service.create(anAppointment(startDate,
                endDate)));

        //then
        assertThat(exception.getMessage()).isEqualTo("Consulta deve ser agendada com antecedência mínima de 30 minutos");
    }

    @Test
    @DisplayName("Should not create appointment unavailable")
    public void shouldNotCreateAppointmentNotAvailableTest() {
        //given

        var provider = aProvider("12345", "Provider", "Test", "provider@provider.com");
        var appointment = anAppointment(provider);
        var startDate = LocalDateTime.now().plusMinutes(30);
        var endDate = LocalDateTime.now().plusHours(1);
        when(repository.existsByProviderWithStartInPeriod(anyString(), any(), any())).thenReturn(false);

        //when
        var exception = assertThrows(ValidationException.class, () -> service.create(anAppointment(provider.getId(),
                startDate, endDate)));

        //then
        assertThat(exception.getMessage()).isEqualTo("Provedor já possui outra consulta agendada nesse mesmo horário");
    }








}