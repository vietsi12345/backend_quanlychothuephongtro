package doanthuctap.booking_service.repository;

import doanthuctap.booking_service.model.InfoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoUserRepository extends JpaRepository<InfoUser, Long> {
    List<InfoUser> findByContractId(Long contractId);
}
