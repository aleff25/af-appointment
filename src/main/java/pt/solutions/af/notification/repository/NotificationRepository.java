package pt.solutions.af.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.solutions.af.notification.model.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {

    @Query("select N from Notification N where N.userId = :userId and N.isRead=false")
    List<Notification> getAllUnreadNotifications(@Param("userId") String userId);
}
