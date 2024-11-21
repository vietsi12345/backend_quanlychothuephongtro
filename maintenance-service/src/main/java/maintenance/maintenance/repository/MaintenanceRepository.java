package maintenance.maintenance.repository;

import maintenance.maintenance.model.Maintenance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    Page<Maintenance> findAll(Pageable pageable);

    Page<Maintenance> findByContractId(Long contractId, Pageable pageable);
    Page<Maintenance> findByStatus(Integer status, Pageable pageable);
    //List<Maintenance> findByHandlerID(Long handlerID);

    /*@Procedure
    List<Maintenance> FindDateBetween(LocalDateTime startcreateAt, LocalDateTime end);*/

    @Query(value = "CALL FindDateBetween(:startDate, :endDate);", nativeQuery = true)
    List<Maintenance> FindDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    //List<Maintenance> findByAgeBetween(LocalDateTime startcreateAt, LocalDateTime end);
}
