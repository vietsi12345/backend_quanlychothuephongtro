package doanthuctap.service_service.controller;

import doanthuctap.service_service.model.Service;
import doanthuctap.service_service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @PostMapping
    public ResponseEntity<Service> createService (@RequestBody Service service) {
        Service createService = serviceService.createService(service);
        return ResponseEntity.ok(createService);
    }

    @GetMapping
    public ResponseEntity<List<Service>> getAllService () {
        return ResponseEntity.ok (serviceService.getAlLService());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Service> getServiceById (@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService (@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.ok("Xóa thành công service có id là:" +id);
    }

    @PutMapping ("/{id}")
    public  ResponseEntity <Service> deleteService (@PathVariable Long id,
                                                    @RequestParam (value = "name",required = false) String name,
                                                    @RequestParam (value = "unit",required = false) String unit,
                                                    @RequestParam (value = "unitPrice",required = false) BigDecimal unitPrice,
                                                    @RequestParam (value = "description",required = false) String description) throws Exception {
        Service service = serviceService.updateService(id,name,unit,unitPrice,description);
        return ResponseEntity.ok(service);
    }
}
