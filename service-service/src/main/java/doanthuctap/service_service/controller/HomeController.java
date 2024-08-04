package doanthuctap.service_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping ("/service-service")
    public ResponseEntity<String> homeController () {
        return ResponseEntity.ok("Welcome service service");
    }
}
