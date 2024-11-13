package datt.chat_service.chat_service.controller;

import datt.chat_service.chat_service.model.ChatRoom;
import datt.chat_service.chat_service.model.Message;
import datt.chat_service.chat_service.service.ChatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@CrossOrigin
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/room/{customerId}")
    public ResponseEntity<ChatRoom> getOrCreateChatRoom(@PathVariable Long customerId) {
        return ResponseEntity.ok(chatService.getOrCreateChatRoom(customerId));
    }

    @GetMapping("/admin/rooms")
    public ResponseEntity<List<ChatRoom>> getAllChatRoomsForAdmin() {
        return ResponseEntity.ok(chatService.getAllChatRoomsForAdmin());
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> sendMessage(
            @RequestParam Long chatRoomId,
            @RequestParam Long senderId,
            @RequestParam String content,
            @RequestParam(defaultValue = "text") String messageType) {
        return ResponseEntity.ok(chatService.sendMessage(chatRoomId, senderId, content, messageType));
    }

    @PostMapping("/messages/img")
    public ResponseEntity<Message> sendMessageImg(
            @RequestParam Long chatRoomId,
            @RequestParam Long senderId,
            @RequestParam MultipartFile multipartFile,
            @RequestParam(defaultValue = "image") String messageType) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        String base64Content = Base64.getEncoder().encodeToString(bytes);
        return ResponseEntity.ok(chatService.sendMessage(chatRoomId, senderId, base64Content, messageType));
    }

    @GetMapping("/messages/{chatRoomId}")
    public ResponseEntity<List<Message>> getChatMessages(@PathVariable Long chatRoomId) {
        return ResponseEntity.ok(chatService.getChatMessages(chatRoomId));
    }

    @PutMapping("/messages/read")
    public ResponseEntity<Void> markMessagesAsRead(
            @RequestParam Long chatRoomId,
            @RequestParam Long userId) {
        chatService.markMessagesAsRead(chatRoomId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/messages/unread-count")
    public ResponseEntity<Integer> getUnreadMessageCount(
            @RequestParam Long chatRoomId,
            @RequestParam Long userId) {
        int unreadCount = chatService.getUnreadMessageCount(chatRoomId, userId);
        return ResponseEntity.ok(unreadCount);
    }

    @GetMapping("/customer/unread-count")
    public ResponseEntity<Integer> getUnreadCountForCustomer(@RequestParam Long customerId) {
        int count = chatService.getUnreadMessageCountForCustomer(customerId);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/messages/read/customer")
    @Transactional
    public ResponseEntity<Integer> markMessagesAsRead(@RequestParam Long customerId) {
        int updatedCount = chatService.markMessagesAsReadForCustomer(customerId);
        return ResponseEntity.ok(updatedCount);
    }
}