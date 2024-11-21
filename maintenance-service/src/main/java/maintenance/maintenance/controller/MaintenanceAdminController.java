package maintenance.maintenance.controller;

import maintenance.maintenance.model.Maintenance;
import maintenance.maintenance.model.MaintenanceDTO;
import maintenance.maintenance.service.MaintenanceServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
}