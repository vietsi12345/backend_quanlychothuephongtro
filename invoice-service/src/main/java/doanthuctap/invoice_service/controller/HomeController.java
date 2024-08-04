package doanthuctap.invoice_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/invoice-service")
    public ResponseEntity<String> homeController () {
        return ResponseEntity.ok("Welcome invoice service !!");
    }


}
