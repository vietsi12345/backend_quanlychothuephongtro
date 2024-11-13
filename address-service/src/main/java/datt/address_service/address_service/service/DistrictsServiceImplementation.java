package datt.address_service.address_service.service;

import datt.address_service.address_service.model.Districts;
import datt.address_service.address_service.reppository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictsServiceImplementation implements DistrictsService{

    @Autowired
    private DistrictRepository districtRepository;
    @Override
    public List<Districts> getAllDistricts() throws Exception {
        return districtRepository.findAll();
    }

    @Override
    public Districts getDistrictsById(Long id) throws Exception {
        return districtRepository.findById(id).orElseThrow(()->new Exception("Không tìm thấy districts"));
    }
}
