package maintenance.maintenance.repository;


import maintenance.maintenance.model.Approval;
import maintenance.maintenance.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByHandlerID(Long handlerID);
    List<Approval> findByMaintenance(Maintenance maintenance);
}
