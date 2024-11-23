package maintenance.maintenance.service;

import maintenance.maintenance.model.Approval;
import maintenance.maintenance.model.ApprovalDTO;
import maintenance.maintenance.model.Maintenance;

import java.util.List;

public interface ApprovalService {
    public Approval convertDTOtoModel(ApprovalDTO dto) throws Exception ;

    public ApprovalDTO convertModelToDTO(Approval approval);

    public List<Approval> findByHandler(Long userID);

    public Approval findByID(Long id);

    public List<Approval> findByMaintenance(Maintenance maintenance) throws Exception;

    public Approval findCurrentApprovalMaintenance(Long maintenanceID) throws Exception;

    public void deleteByMaintenance(Maintenance maintenanceID) throws Exception;
}
