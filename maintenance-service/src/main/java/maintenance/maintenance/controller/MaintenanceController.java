package maintenance.maintenance.controller;


import maintenance.maintenance.model.ApprovalDTO;
import maintenance.maintenance.model.Maintenance;
import maintenance.maintenance.model.MaintenanceDTO;
import maintenance.maintenance.service.MaintenanceServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {
    @Autowired
    private MaintenanceServiceImplementation maintenanceServiceImplementation;

    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getMaintenanceByID(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok(maintenanceServiceImplementation.getMaintenanceById(id));
    }

    @GetMapping("/cancel/{id}")
    public ResponseEntity<String> cancelMaintenance(@PathVariable Long id) throws Exception{
        try {
            return ResponseEntity.ok(maintenanceServiceImplementation.cancelMaintenance(id));
        }
        catch (NullPointerException nullPointerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found maintenance " + id + "  to cancel");
        }
    }

    @GetMapping("/getByStatus")
    public ResponseEntity<List<Maintenance>> getByStatus(@RequestParam Integer status, @RequestParam Integer page)throws Exception{

        return ResponseEntity.ok(maintenanceServiceImplementation.getMaintenanceByStatus(status));
    }


    @PostMapping("/")
    public ResponseEntity<?> createMaintenance(@ModelAttribute MaintenanceDTO maintenanceDTO) throws Exception{
        return ResponseEntity.ok(maintenanceServiceImplementation.createMaintenanceUser(maintenanceDTO, 0));
    }

    @PutMapping("/resubmit/{id}")
    public ResponseEntity<String> resubmitMaintenance(@ModelAttribute MaintenanceDTO maintenanceDTO, @PathVariable Long id) throws Exception{
        try {
            return ResponseEntity.ok(maintenanceServiceImplementation.resubmitMaintenance(maintenanceDTO,id));
        }catch (NullPointerException nullPointerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Maintenance "+id+" is not present");
        }
    }

    //chua sua
    @PutMapping("/{id}")
    public ResponseEntity<String> updateMaintenance(@ModelAttribute MaintenanceDTO maintenanceDTO, @PathVariable Long id) throws Exception{
        try {
            return ResponseEntity.ok(maintenanceServiceImplementation.updateMaintenance(maintenanceDTO, id));
        }catch (NullPointerException nullPointerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Maintenance "+ id+" is not present");
        }
    }

    @PutMapping("/approval")
    public ResponseEntity<?> approvalMaintenanceStep1(@ModelAttribute ApprovalDTO approvalDTO) throws Exception{
        // Duyet o day
        try {
            return ResponseEntity.ok(maintenanceServiceImplementation.ApprovalMaintenance(approvalDTO));
        }catch (NullPointerException nullPointerException){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Maintenance "+approvalDTO.getIdMaintence() +" is not present");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMaintenanceByID(@PathVariable Long id) throws Exception{
        try {
            return ResponseEntity.ok(maintenanceServiceImplementation.deletMaintenance(id));
        }catch (NullPointerException nullPointerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Maintenance "+ id + " is not present");
        }

    }
}
