package datt.address_service.address_service.reppository;

import datt.address_service.address_service.model.Districts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<Districts,Long> {
}
