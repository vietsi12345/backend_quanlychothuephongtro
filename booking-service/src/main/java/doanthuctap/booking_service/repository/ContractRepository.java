package doanthuctap.booking_service.repository;

import doanthuctap.booking_service.model.Contract;
import doanthuctap.booking_service.model.ContractResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract,Long> {
    List<Contract> findAllByOrderByIdDesc ();

    List<Contract> findByBookingUserIdOrderByIdDesc(Long userId);

    Contract findByBookingId (Long bookingId);

}
