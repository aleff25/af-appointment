package pt.solutions.af.appointment.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static pt.solutions.af.appointment.AppointmentTestUtils.anAppointment;
import static pt.solutions.af.user.ProviderTestUtils.aProvider;
import static pt.solutions.af.util.TestUtils.randomUUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AppointmentRepositoryTest {


    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private TestEntityManager em;


    @Test
    @DisplayName("Should find one appointment with provider in start in period")
    public void shouldFindByProviderIdWithStartInPeriodTest() {
        //given
        var providerId = randomUUID();
        var provider = aProvider(providerId, "Provider", "Test", "provider@provider.com");
        var appointment = anAppointment(provider);

        //when

        var appointments = repository.findByProviderIdWithStartInPeriod(providerId, LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        //then

        assertThat(appointments.size()).isEqualTo(1);
        var appointmentFound = appointments.iterator().next();
        assertThat(appointmentFound).isEqualTo(appointment);
    }

    @Test
    @DisplayName("Should not find appointment with provider with start in period")
    public void shouldNotFindByProviderIdWithStartInPeriodTest() {
        //given
        var providerId = randomUUID();
        var provider = aProvider(providerId, "Provider", "Test", "provider@provider.com");
        anAppointment(provider);

        //when

        var appointments = repository.findByProviderIdWithStartInPeriod(randomUUID(), LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

        //then
        assertThat(appointments.size()).isEqualTo(1);
        var appointmentFound = appointments.iterator().next();
        var providerFoundId = appointmentFound.getProvider().getId();
        assertThat(providerFoundId).isNotEqualTo(providerId);
    }



}
