package datt.address_service.address_service.controller;

import datt.address_service.address_service.model.Districts;
import datt.address_service.address_service.service.DistrictsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
public class DistrictsController {
    @Autowired
    private DistrictsService districtsService;

    @GetMapping
    public ResponseEntity<List<Districts>> getAllDistricts () throws  Exception {
        return ResponseEntity.ok(districtsService.getAllDistricts());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Districts> getDistrictsById (@PathVariable Long id) throws  Exception {
        return ResponseEntity.ok(districtsService.getDistrictsById(id));
    }
}
