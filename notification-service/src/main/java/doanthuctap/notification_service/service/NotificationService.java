package doanthuctap.notification_service.service;

import doanthuctap.notification_service.model.Notification;

import java.util.List;

public interface NotificationService {
    Notification createNotification (Notification notification);
    List<Notification> getNotificationForUser (Long userId);

    List<Notification> getUnreadNotificationForUser (Long userId);

    Notification markNotificationAsRead (Long notificationId);

    void deleteNotification (Long notificationId);
}
