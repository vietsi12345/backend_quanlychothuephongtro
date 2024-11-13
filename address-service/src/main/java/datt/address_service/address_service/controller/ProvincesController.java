package datt.address_service.address_service.controller;

import datt.address_service.address_service.model.Districts;
import datt.address_service.address_service.model.Provinces;
import datt.address_service.address_service.service.DistrictsService;
import datt.address_service.address_service.service.ProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/provinces")
public class ProvincesController {
    @Autowired
    private ProvincesService provincesService;

    @GetMapping
    public ResponseEntity<List<Provinces>> getAllProvinces () throws  Exception {
        return ResponseEntity.ok(provincesService.getAllProvinces());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Provinces> getProvincesById (@PathVariable Long id) throws  Exception {
        return ResponseEntity.ok(provincesService.getProvincesById(id));
    }
}
