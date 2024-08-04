package doanthuctap.invoice_service.service;

import doanthuctap.invoice_service.model.ServiceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SERVICE-SERVICE", url ="http://localhost:8005")
public interface ServiceService {
    @GetMapping("/api/services/{id}")
    public ServiceDto getServiceById (@PathVariable Long id) throws Exception;
}
