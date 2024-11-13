package datt.address_service.address_service.service;

import datt.address_service.address_service.model.Communes;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CommunesService {
    List<Communes> getAllCommunes () throws Exception ;

    Communes getCommunesById (Long id) throws Exception;
}
