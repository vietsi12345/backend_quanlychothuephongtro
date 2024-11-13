package datt.chat_service.chat_service.service;

import datt.chat_service.chat_service.model.ChatRoom;
import datt.chat_service.chat_service.model.Message;
import datt.chat_service.chat_service.repository.ChatRoomRepository;
import datt.chat_service.chat_service.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private  ChatRoomRepository chatRoomRepository;
    @Autowired
    private  MessageRepository messageRepository;

    @Override
    public ChatRoom getOrCreateChatRoom(Long customerId) {
        return chatRoomRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    ChatRoom newRoom = new ChatRoom();
                    newRoom.setCustomerId(customerId);
                    newRoom.setLastMessageAt(LocalDateTime.now());
                    newRoom.setActive(true);
                    return chatRoomRepository.save(newRoom);
                });
    }

    @Override
    public List<ChatRoom> getAllChatRoomsForAdmin() {
        return chatRoomRepository.findAllByOrderByLastMessageAtDesc();
    }

    @Override
    @Transactional
    public Message sendMessage(Long chatRoomId, Long senderId, String content, String messageType) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        Message message = new Message();
        message.setChatRoom(chatRoom);
        message.setSenderId(senderId);
        message.setContent(content);
        message.setMessageType(messageType);
        message.setSentAt(LocalDateTime.now());
        message.setRead(false);

        // Cập nhật lastMessageAt của chatRoom
        chatRoom.setLastMessageAt(LocalDateTime.now());
        chatRoomRepository.save(chatRoom);

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getChatMessages(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));
        return messageRepository.findByChatRoomOrderBySentAtAsc(chatRoom);
    }

    @Override
    @Transactional
    public void markMessagesAsRead(Long chatRoomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        List<Message> unreadMessages = messageRepository
                .findByChatRoomAndIsReadFalseAndSenderIdNot(chatRoom, userId);

        unreadMessages.forEach(message -> {
            message.setRead(true);
            messageRepository.save(message);
        });
    }

    @Override
    public int getUnreadMessageCount(Long chatRoomId, Long userId) {
        return messageRepository.countUnreadMessagesByChatRoomIdAndUserId(chatRoomId, userId);
    }

    @Override
    public int getUnreadMessageCountForCustomer(Long customerId) {
        return messageRepository.countAllUnreadMessagesForCustomer(customerId);
    }

    @Override
    public int markMessagesAsReadForCustomer(Long customerId) {
        return messageRepository.markAllMessagesAsReadForCustomer(customerId);
    }
}