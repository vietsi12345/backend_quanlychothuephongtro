package datt.chat_service.chat_service.service;

import datt.chat_service.chat_service.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE", url ="http://localhost:8001")
public interface UserService {
    @GetMapping("/api/users/{id}")
    public UserDto getUserById(@PathVariable Long id) throws Exception;
}
