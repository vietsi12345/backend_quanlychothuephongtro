package datt.chat_service.chat_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message { //yjt
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private LocalDateTime sentAt;
    private boolean isRead;
    private String messageType;

    @ManyToOne
    @JoinColumn(name = "chatRoomId", nullable = false)
    @JsonIgnoreProperties("messages")
    private ChatRoom chatRoom;
}
