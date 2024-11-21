package maintenance.maintenance.service;


import maintenance.maintenance.model.ContractDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "BOOKING-SERVICE", url ="http://localhost:8003")
public interface ContractService {
    @PostMapping("/api/contracts/{id}")
    public ResponseEntity<ContractDTO> getContractById (@PathVariable Long id);
}
