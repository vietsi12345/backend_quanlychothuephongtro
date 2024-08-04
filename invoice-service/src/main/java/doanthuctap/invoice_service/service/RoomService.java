package doanthuctap.invoice_service.service;

import doanthuctap.invoice_service.model.RoomDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOUSE-SERVICE", url ="http://localhost:8002")
public interface RoomService {
    @GetMapping("/api/rooms/{id}")
    public RoomDto getRoomById(@PathVariable Long id) throws Exception;
}
