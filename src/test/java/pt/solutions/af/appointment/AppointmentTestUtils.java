package pt.solutions.af.appointment;

import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.appointment.repository.AppointmentRepository;
import pt.solutions.af.user.model.provider.Provider;

import java.time.LocalDateTime;

public class AppointmentTestUtils {

    public static void persist(AppointmentRepository repository, Appointment user) {
        repository.save(user);
    }

    public static Appointment anAppointment(Provider provider) {
        var appointment = Appointment.builder()
                .startDate(LocalDateTime.now().plusMinutes(30))
                .endDate(LocalDateTime.now().plusHours(1))
                .provider(provider)
                .build();
        return appointment;
    }

}
