package doanthuctap.service_service.service;

import doanthuctap.service_service.model.Service;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceService {
    Service createService (Service service);

   List<Service> getAlLService ();

    Service  getServiceById (Long id) throws  Exception ;

    void deleteService (Long id) ;

    Service updateService (Long id, String name, String unit, BigDecimal unitPrice,String description) throws Exception;
}
