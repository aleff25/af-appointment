package pt.solutions.af.appointment;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import pt.solutions.af.appointment.repository.AppointmentRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static pt.solutions.af.appointment.AppointmentTestUtils.registerAppointment;
import static pt.solutions.af.user.UserTestUtils.registerProvider;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AppointmentTest {


    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private TestEntityManager em;


    @Test
    @DisplayName("Should find one appointment with provider in start in period")
    public void shouldFindByProviderIdWithStartInPeriodTest() {
        //given

        var provider = registerProvider(em, "12345","Provider", "Test", "provider@provider.com");
        var appointment = registerAppointment(em, provider);

        //when

        var appointments = repository.findByProviderIdWithStartInPeriod("12345", LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        //then

        assertThat(appointments.size()).isEqualTo(1);
        var appointmentFound = appointments.iterator().next();
        assertThat(appointmentFound).isEqualTo(appointment);
    }

    @Test
    @DisplayName("Should not find appointment with provider with start in period")
    public void shouldNotFindByProviderIdWithStartInPeriodTest() {
        //given

        var provider = registerProvider(em, "12345","Provider", "Test", "provider@provider.com");
        var appointment = registerAppointment(em, provider);

        //when

        var appointments = repository.findByProviderIdWithStartInPeriod("23456", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

        //then
        assertThat(appointments.size()).isEqualTo(1);
        var appointmentFound = appointments.iterator().next();
        var providerId = appointmentFound.getProvider().getId();
        assertThat(providerId).isNotEqualTo(provider.getId());
    }



}
