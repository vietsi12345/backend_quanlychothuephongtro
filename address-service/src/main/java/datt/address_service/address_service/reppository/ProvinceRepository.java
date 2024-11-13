package datt.address_service.address_service.reppository;

import datt.address_service.address_service.model.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Provinces,Long> {
}
