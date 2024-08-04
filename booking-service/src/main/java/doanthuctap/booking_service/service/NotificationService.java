package doanthuctap.booking_service.service;

import doanthuctap.booking_service.model.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NOTIFICATION-SERVICE", url ="http://localhost:8004")
public interface NotificationService {
    @PostMapping("/api/notifications")
    public ResponseEntity<NotificationDto> createNotification (@RequestBody NotificationDto notificationDto);
}
