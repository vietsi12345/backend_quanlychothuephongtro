package datt.chat_service.chat_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/chat-service")
    public ResponseEntity<String> homeController () {
        return ResponseEntity.ok("Welcome to chat service !!");
    }
}
