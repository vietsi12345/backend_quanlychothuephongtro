package maintenance.maintenance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/maintenance-service")
    public ResponseEntity<String> home (){
        return ResponseEntity.ok("Welcome to maintenance_service");
    }
}
