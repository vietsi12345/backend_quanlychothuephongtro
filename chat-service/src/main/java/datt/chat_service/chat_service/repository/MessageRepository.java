package datt.chat_service.chat_service.repository;

import datt.chat_service.chat_service.model.Message;
import datt.chat_service.chat_service.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoomOrderBySentAtAsc(ChatRoom chatRoom);
    List<Message> findByChatRoomAndIsReadFalseAndSenderIdNot(ChatRoom chatRoom, Long senderId);


    @Query("SELECT COUNT(m) FROM Message m WHERE m.chatRoom.id = :chatRoomId AND m.senderId <> :userId AND m.isRead = false")
    int countUnreadMessagesByChatRoomIdAndUserId(@Param("chatRoomId") Long chatRoomId, @Param("userId") Long userId);

    @Query("SELECT COUNT(m) FROM Message m " +
            "WHERE m.chatRoom IN (SELECT c FROM ChatRoom c WHERE c.customerId = :customerId) " +
            "AND m.senderId <> :customerId " +
            "AND m.isRead = false")
    int countAllUnreadMessagesForCustomer(@Param("customerId") Long customerId);

    // đánh dấu tất cả tin nhắn chưa đọc của customer thành đã đọc
    @Query("UPDATE Message m SET m.isRead = true " +
            "WHERE m.chatRoom IN (SELECT c FROM ChatRoom c WHERE c.customerId = :customerId) " +
            "AND m.senderId <> :customerId " +
            "AND m.isRead = false")
    @Modifying
    int markAllMessagesAsReadForCustomer(@Param("customerId") Long customerId);
}