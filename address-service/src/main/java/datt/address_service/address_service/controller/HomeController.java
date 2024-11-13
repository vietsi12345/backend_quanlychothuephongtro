package datt.address_service.address_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/address-service")
    public ResponseEntity<String> home () {
        return  new ResponseEntity<>("welcome to address service", HttpStatus.OK);
    }
}
