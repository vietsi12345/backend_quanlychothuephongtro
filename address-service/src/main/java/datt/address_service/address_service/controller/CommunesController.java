package datt.address_service.address_service.controller;

import datt.address_service.address_service.model.Communes;
import datt.address_service.address_service.service.CommunesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/communes")
public class CommunesController {
    @Autowired
    private CommunesService communesService;

    @GetMapping
    public ResponseEntity<List<Communes>> getAllCommunes () throws Exception {
        List<Communes> allCommunes = communesService.getAllCommunes();
        return ResponseEntity.ok(allCommunes);
    }
    @GetMapping ("/{id}")
    public ResponseEntity<Communes> getCommunesById (@PathVariable Long id )throws Exception {
        return ResponseEntity.ok(communesService.getCommunesById(id));
    }
}
