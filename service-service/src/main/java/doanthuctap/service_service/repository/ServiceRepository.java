package doanthuctap.service_service.repository;

import doanthuctap.service_service.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository <Service, Long> {
    List<Service> findByIsActiveTrue ();

}
