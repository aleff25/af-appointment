package pt.solutions.af.appointment;

import pt.solutions.af.appointment.application.dto.RegisterAppointmentDTO;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.appointment.repository.AppointmentRepository;
import pt.solutions.af.user.model.provider.Provider;

import java.time.LocalDateTime;

import static pt.solutions.af.util.TestUtils.randomLong;
import static pt.solutions.af.util.TestUtils.randomUUID;

public class AppointmentTestUtils {

    public static void persist(AppointmentRepository repository, Appointment user) {
        repository.save(user);
    }

    public static RegisterAppointmentDTO anAppointment() {
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

    public static RegisterAppointmentDTO anAppointment(LocalDateTime start, LocalDateTime end) {
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

    public static Appointment anAppointment(Provider provider) {
        var appointment = Appointment.builder()
                .startDate(LocalDateTime.now().plusMinutes(30))
                .endDate(LocalDateTime.now().plusHours(1))
                .provider(provider)
                .build();
        return appointment;
    }

    public static RegisterAppointmentDTO anAppointment(String providerId, LocalDateTime start, LocalDateTime end) {
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
