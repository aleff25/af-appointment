package pt.solutions.af.appointment;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.user.model.provider.Provider;

import java.time.LocalDateTime;

public class AppointmentTestUtils {

    public static Appointment registerAppointment(TestEntityManager em, Provider provider) {
        var appointment = Appointment.builder()
                .startDate(LocalDateTime.now().plusMinutes(30))
                .endDate(LocalDateTime.now().plusHours(1))
                .provider(provider)
                .build();
        em.persist(appointment);
        return appointment;
    }

}
