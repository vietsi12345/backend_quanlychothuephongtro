package doanthuctap.booking_service.service;

import doanthuctap.booking_service.model.RoomDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "HOUSE-SERVICE", url ="http://localhost:8002")
public interface RoomService {
    @PutMapping("/api/rooms/update-status/{id}")
    public RoomDto updateStatus(@PathVariable Long id,
                                                @RequestParam("status") String status) throws Exception;

    @GetMapping("/api/rooms/{id}")
    public  RoomDto getRoomById(@PathVariable Long id) throws Exception;
}
