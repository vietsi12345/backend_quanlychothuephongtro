package maintenance.maintenance.controller;

import maintenance.maintenance.model.Maintenance;
import maintenance.maintenance.model.MaintenanceDTO;
import maintenance.maintenance.service.MaintenanceServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/admin/maintenance")
public class MaintenanceAdminController {
    @Autowired
    private MaintenanceServiceImplementation maintenanceServiceImplementation;

    @GetMapping("/getByTime")
    public ResponseEntity<List<Maintenance>> getAllByTime(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) throws Exception{
        //Pageable pageable = PageRequest.of(page, 20);
        return ResponseEntity.ok(maintenanceServiceImplementation.getMaintenanceByTime(startDate, endDate));
    }

    @PostMapping("/")
    public ResponseEntity<?> createMaintenance(@ModelAttribute MaintenanceDTO maintenanceDTO) throws Exception{
        return ResponseEntity.ok(maintenanceServiceImplementation.createMaintenanceUser(maintenanceDTO,1));
    }

    @GetMapping("/getByRoom/{id}")
    public ResponseEntity<List<Maintenance>> getByContract(@PathVariable Long id)throws Exception{
        return ResponseEntity.ok(maintenanceServiceImplementation.getMaintenanceByContract(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Maintenance>> getAllMaintenance() throws  Exception{
        return ResponseEntity.ok(maintenanceServiceImplementation.getAllMaintenance());
    }

    //chua test
    @PutMapping("/{id}")
    public ResponseEntity<String> updateMaintenance(@ModelAttribute MaintenanceDTO maintenanceDTO, @PathVariable Long id) throws Exception{
        try {
            return ResponseEntity.ok(maintenanceServiceImplementation.updateMaintenance(maintenanceDTO, id));
        }catch (NullPointerException nullPointerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Maintenance "+ id+" is not present");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getMaintenanceByID(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok(maintenanceServiceImplementation.getMaintenanceById(id));
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