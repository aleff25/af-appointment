package pt.solutions.af.appointment.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.solutions.af.appointment.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    @Query(value = "from Appointment t where t.startDate >=  FORMATDATETIME(:startDate, 'yyyy-MM-dd hh:mm') AND t.endDate <= FORMATDATETIME(:endDate, 'yyyy-MM-dd hh:mm')")
    List<Appointment> getAllBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
