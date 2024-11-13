package datt.address_service.address_service.reppository;

import datt.address_service.address_service.model.Communes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuneRepository extends JpaRepository<Communes, Long> {
}
