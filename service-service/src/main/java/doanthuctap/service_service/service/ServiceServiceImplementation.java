package doanthuctap.service_service.service;

import doanthuctap.service_service.model.Service;
import doanthuctap.service_service.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImplementation implements ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;
    @Override
    public Service createService(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public List<Service> getAlLService() {
        return serviceRepository.findAll();
    }

    @Override
    public Service getServiceById(Long id) throws Exception {
        return serviceRepository.findById(id).orElseThrow(()->new Exception("service not found"));
    }

    @Override
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public Service updateService(Long id,String name, String unit, BigDecimal unitPrice, String description) throws Exception {
        Service service= getServiceById(id);
        if (name != null && !name.isEmpty()) {
            service.setName(name);
        }
        if (unit != null && !unit.isEmpty()) {
            service.setUnit(unit);
        }
        if (unitPrice != null ) {
            service.setUnitPrice(unitPrice);
        }
        if (description != null && !description.isEmpty()) {
            service.setDescription(description);
        }
        return serviceRepository.save(service);
    }
}
