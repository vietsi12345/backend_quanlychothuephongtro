package maintenance.maintenance.repository;

import maintenance.maintenance.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    List<Maintenance> findByRoomID(Long roomID);
    List<Maintenance> findByStatus(Integer status);
    //List<Maintenance> findByHandlerID(Long handlerID);

    /*@Procedure
    List<Maintenance> FindDateBetween(LocalDateTime startcreateAt, LocalDateTime end);*/

    @Query(value = "CALL FindDateBetween(:startDate, :endDate);", nativeQuery = true)
    List<Maintenance> FindDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    //List<Maintenance> findByAgeBetween(LocalDateTime startcreateAt, LocalDateTime end);

    /*@Query(value = "call datt_maintenance_service.getMaintenanceByID(:idmaintenance);", nativeQuery = true)
    MaintenanceResponse findByIDSP(@Param("idmaintenance") Long idmaintenance);

    @Query(value = "SELECT * FROM datt_maintenance_service.maintenancewithlatestapproval;", nativeQuery = true)
    List<MaintenanceResponseProjection>  findAllMaintenanceResponse();*/
}
