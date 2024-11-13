package doanthuctap.service_service.service;

import doanthuctap.service_service.model.Service;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceService {
    Service createService (Service service);

   List<Service> getAlLService ();
    List<Service> getAlLServiceIsActive ();

    Service  getServiceById (Long id) throws  Exception ;

    Service deleteService (Long id) throws Exception;

    Service updateService (Long id, String name, String unit, BigDecimal unitPrice,String description) throws Exception;
}
