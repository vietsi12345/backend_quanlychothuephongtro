package doanthuctap.notification_service.controller;

import doanthuctap.notification_service.model.Notification;
import doanthuctap.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> createNotification (@RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.createNotification(notification));
    }

    @GetMapping ("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsForUser (@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationForUser(userId));
    }

    @GetMapping ("/user/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotificationsForUser (@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUnreadNotificationForUser(userId));
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Notification> markNotificationAsRead (@PathVariable Long notificationId){
        return ResponseEntity.ok(notificationService.markNotificationAsRead(notificationId));
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok().build();
    }
}
