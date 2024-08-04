package com.doanthuctap.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping ("/house-service")
    public ResponseEntity<String> home () {
        return  new ResponseEntity<>("welcome to house service", HttpStatus.OK);
    }
}
