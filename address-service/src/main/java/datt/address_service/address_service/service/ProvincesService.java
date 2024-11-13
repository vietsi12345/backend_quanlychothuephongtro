package datt.address_service.address_service.service;
import datt.address_service.address_service.model.Provinces;

import java.util.List;

public interface ProvincesService {
    List<Provinces> getAllProvinces () throws Exception ;

    Provinces getProvincesById (Long id) throws Exception;
}
