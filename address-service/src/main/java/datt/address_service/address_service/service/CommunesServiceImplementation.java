package datt.address_service.address_service.service;

import datt.address_service.address_service.model.Communes;
import datt.address_service.address_service.reppository.CommuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CommunesServiceImplementation implements CommunesService {

    @Autowired
    private CommuneRepository communeRepository;

    @Override
    public List<Communes> getAllCommunes() throws Exception {
        return communeRepository.findAll() ;
    }

    @Override
    public Communes getCommunesById(Long id) throws Exception {
        return communeRepository.findById(id).orElseThrow(() -> new Exception("Commune not founds"));
    }
}
