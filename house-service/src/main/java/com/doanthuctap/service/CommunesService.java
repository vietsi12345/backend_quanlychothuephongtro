package com.doanthuctap.service;

import com.doanthuctap.model.CommunesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ADDRESS-SERVICE",url = "http://localhost:8007")
public interface CommunesService {
    @GetMapping ("/api/communes/{id}")
    public CommunesDto getCommunesById(@PathVariable Long id) throws Exception;
}
