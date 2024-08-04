package doanthuctap.notification_service.repository;

import doanthuctap.notification_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository <Notification,Long> {
    List<Notification> findByUserIdOrderByIdDesc (Long userId);

    List <Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc (Long userId);

}
