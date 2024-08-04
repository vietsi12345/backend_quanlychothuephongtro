package doanthuctap.notification_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/notification-service")
    public ResponseEntity<String> home () {
        return  new ResponseEntity<>("welcome to notification service", HttpStatus.OK);
    }
}
