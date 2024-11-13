package datt.address_service.address_service.service;

import datt.address_service.address_service.model.Districts;

import java.util.List;

public interface DistrictsService {
    List<Districts> getAllDistricts () throws Exception ;

    Districts getDistrictsById (Long id) throws Exception;
}
