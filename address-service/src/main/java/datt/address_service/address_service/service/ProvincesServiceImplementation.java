package datt.address_service.address_service.service;

import datt.address_service.address_service.model.Provinces;
import datt.address_service.address_service.reppository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvincesServiceImplementation implements ProvincesService{

    @Autowired
    private ProvinceRepository provinceRepository;
    @Override
    public List<Provinces> getAllProvinces() throws Exception {
        return provinceRepository.findAll();
    }

    @Override
    public Provinces getProvincesById(Long id) throws Exception {
        return provinceRepository.findById(id).orElseThrow(()-> new Exception("Không tìm thấy pronvinces"));
    }
}
