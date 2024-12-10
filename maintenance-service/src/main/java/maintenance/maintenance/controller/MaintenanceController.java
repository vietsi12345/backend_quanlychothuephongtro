package maintenance.maintenance.controller;


import maintenance.maintenance.exeption.InvalidMaintenanceStatusException;
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

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Maintenance> cancelMaintenance(@PathVariable Long id) throws Exception{
        try {
            return ResponseEntity.ok(maintenanceServiceImplementation.cancelMaintenance(id));
        }
        catch (NullPointerException nullPointerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (InvalidMaintenanceStatusException invalidMaintenanceStatusException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/mymaintenance")
    public ResponseEntity<List<Maintenance>> getMaintenanceByIDUser(@RequestParam Long userid) throws Exception{
        return ResponseEntity.ok(maintenanceServiceImplementation.getMyMaintenance(userid));
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
    public ResponseEntity<Maintenance> resubmitMaintenance(@ModelAttribute MaintenanceDTO maintenanceDTO, @PathVariable Long id) throws Exception{
        try {
            return ResponseEntity.ok(maintenanceServiceImplementation.resubmitMaintenance(maintenanceDTO,id));
        }catch (NullPointerException nullPointerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (InvalidMaintenanceStatusException invalidMaintenanceStatusException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //chua sua
    @PutMapping("/{id}")
    public ResponseEntity<Maintenance> updateMaintenance(@ModelAttribute MaintenanceDTO maintenanceDTO, @PathVariable Long id) throws Exception{
        try {
            return ResponseEntity.ok(maintenanceServiceImplementation.updateMaintenance(maintenanceDTO, id));
        }catch (NullPointerException nullPointerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (InvalidMaintenanceStatusException invalidMaintenanceStatusException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/approval")
    public ResponseEntity<Maintenance> approvalMaintenanceStep1(@ModelAttribute ApprovalDTO approvalDTO) throws Exception{
        // Duyet o day
        try {
            return ResponseEntity.ok(maintenanceServiceImplementation.ApprovalMaintenance(approvalDTO));
        }catch (NullPointerException nullPointerException){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (InvalidMaintenanceStatusException invalidMaintenanceStatusException){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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
