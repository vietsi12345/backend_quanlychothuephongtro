package datt.chat_service.chat_service.repository;

import datt.chat_service.chat_service.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    Optional<ChatRoom> findByCustomerId(Long customerId);
    List<ChatRoom> findAllByOrderByLastMessageAtDesc();
}
