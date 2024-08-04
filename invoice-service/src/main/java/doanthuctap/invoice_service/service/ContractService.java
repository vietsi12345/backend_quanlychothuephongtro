package doanthuctap.invoice_service.service;

import doanthuctap.invoice_service.model.BookingDto;
import doanthuctap.invoice_service.model.ContractDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "BOOKING-SERVICE", url ="http://localhost:8003")
public interface ContractService {
    @GetMapping("/api/contracts/{id}")
    public ContractDto getContractById (@PathVariable Long id) throws  Exception ;
}
