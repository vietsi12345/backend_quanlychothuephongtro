package datt.chat_service.chat_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chat_rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // Đảm bảo mỗi khách hàng chỉ có 1 chat room
    private Long customerId;
    private LocalDateTime lastMessageAt;
    private boolean isActive;
    @OneToMany(mappedBy = "chatRoom")
    @JsonIgnoreProperties("chatRoom")
    private List<Message> messages;
}
