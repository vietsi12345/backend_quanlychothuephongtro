package datt.chat_service.chat_service.service;

import datt.chat_service.chat_service.model.ChatRoom;
import datt.chat_service.chat_service.model.Message;

import java.util.List;

public interface ChatService {
    ChatRoom getOrCreateChatRoom(Long customerId);

    List<ChatRoom> getAllChatRoomsForAdmin();
    Message sendMessage(Long chatRoomId, Long senderId, String content, String messageType);
    List<Message> getChatMessages(Long chatRoomId);
    void markMessagesAsRead(Long chatRoomId, Long userId);

    // lấy số lượng tin nhắn chưa xem theo chat room và không phải của người dùng hiện tại
    int getUnreadMessageCount(Long chatRoomId, Long userId);

    // lấy số lượng tin nhắn chưa xem của customer
    int getUnreadMessageCountForCustomer (Long customerId);
    //đánh dấu những tin nhắn chưa đọc của customer thành đã đọc
    int markMessagesAsReadForCustomer(Long customerId) ;
}
