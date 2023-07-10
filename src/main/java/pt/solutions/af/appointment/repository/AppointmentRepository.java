package pt.solutions.af.appointment.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.appointment.model.AppointmentListView;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    @Query(value = "SELECT a.id," +
            "       a.start_date," +
            "       a.end_date," +
            "       Jsonb_agg(Jsonb_build_object('id', p.id, 'name', p.name)) AS provider," +
            "       Jsonb_agg(Jsonb_build_object('id', c.id, 'name', c.name)) AS customer," +
            "       Jsonb_agg(Jsonb_build_object('id', w.id, 'name', w.name)) AS work " +
            "FROM   appointments a" +
            "       inner join users p" +
            "               ON p.id = a.provider_id" +
            "       inner join users c" +
            "               ON c.id = a.customer_id" +
            "       inner join works w" +
            "               ON w.id = a.work_id" +
            "WHERE  a.start_date >= :start_date" +
            "       AND a.end_date <= :end_date" +
            "GROUP  BY a.id", nativeQuery = true)
    List<AppointmentListView> getAllBetweenDates(@Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);

    @Query("select a from Appointment a where a.provider.id = :providerId")
    List<Appointment> findByProviderId(@Param("providerId") String providerId);

    List<Appointment> findAllByProviderId(String providerId);

    List<Appointment> findAllByCustomerId(String customerId);

    @Query("select a from Appointment a where a.provider.id = :providerId and  a.startDate >=:dayStart and  a.startDate <=:dayEnd")
    List<Appointment> findByProviderIdWithStartInPeriod(@Param("providerId") String providerId, @Param("dayStart") LocalDateTime startDate, @Param("dayEnd") LocalDateTime endDate);

    @Query("select a from Appointment a where a.customer.id = :customerId and  a.startDate >=:dayStart and  a.startDate <=:dayEnd")
    List<Appointment> findByCustomerIdWithStartInPeriod(@Param("customerId") String customerId, @Param("dayStart") LocalDateTime startDate, @Param("dayEnd") LocalDateTime endDate);

    @Query("select a from Appointment a where a.status = 'CONFIRMED' and a.customer.id = :customerId")
    List<Appointment> findConfirmedByCustomerId(@Param("customerId") String customerId);

    @Query("select a from Appointment a where a.status = 'SCHEDULED' and :now >= a.endDate")
    List<Appointment> findScheduledWithEndBeforeDate(@Param("now") LocalDateTime now);

    @Query("select a from Appointment a where a.status = 'FINISHED' and :date >= a.endDate")
    List<Appointment> findFinishedWithEndBeforeDate(@Param("date") LocalDateTime date);
}
