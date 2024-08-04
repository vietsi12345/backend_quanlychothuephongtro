package doanthuctap.booking_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long id;

    private Long userId;

    private String content;

    private String type;

    private LocalDate createdAt;

    private boolean isRead;
}
