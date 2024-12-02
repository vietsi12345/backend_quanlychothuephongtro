package maintenance.maintenance.repository;


import maintenance.maintenance.model.Approval;
import maintenance.maintenance.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByHandlerID(Long handlerID);
    List<Approval> findByMaintenance(Maintenance maintenance);

    @Query("SELECT MAX(a.round) FROM Approval a WHERE a.maintenance.id = :maintenanceId")
    Integer findMaxRoundByMaintenanceId(@Param("maintenanceId") Long maintenanceId);

    @Query("SELECT MAX(a.step) FROM Approval a WHERE a.maintenance.id = :maintenanceId AND a.round = :round")
    Integer findMaxStepByMaintenanceIdAndRound(@Param("maintenanceId") Long maintenanceId, @Param("round") Integer round);

    @Query("SELECT a.handlerID FROM Approval a WHERE a.maintenance.id = :maintenanceId AND a.step = 0 AND a.round = 1")
    Long findHandlerIdByStepAndRound(@Param("maintenanceId") Long maintenanceId);

    @Query("SELECT a FROM Approval a WHERE a.maintenance.id = :maintenanceId AND a.step = 0 AND a.round = :round")
    Approval getApprovalStep0(@Param("maintenanceId") Long maintenanceId, @Param("round") Integer round);
}
