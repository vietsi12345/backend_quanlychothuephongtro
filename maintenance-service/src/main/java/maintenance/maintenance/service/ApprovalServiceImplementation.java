package maintenance.maintenance.service;

import maintenance.maintenance.model.Approval;
import maintenance.maintenance.model.ApprovalDTO;
import maintenance.maintenance.model.Maintenance;
import maintenance.maintenance.repository.ApprovalRepository;
import maintenance.maintenance.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ApprovalServiceImplementation implements ApprovalService {
    @Autowired
    private ApprovalRepository approvalRepository;
    @Autowired
    private MaintenanceRepository maintenanceRepository;
//    @Autowired
//    private MaintenanceServiceImplementation  maintenanceServiceImplementation;

    @Override
    public Approval convertDTOtoModel(ApprovalDTO dto) throws Exception  {
        Approval approval = findCurrentApprovalMaintenance(dto.getIdMaintence());
        approval.setHandlerID(dto.getUserID());
        approval.setReview(dto.getReview());
        approval.setStatus(dto.getStatus());
        return approval;
    }



    @Override
    public ApprovalDTO convertModelToDTO(Approval approval) {
        ApprovalDTO dto = new ApprovalDTO();
        dto.setReview(approval.getReview());
        dto.setUserID(approval.getHandlerID());
        dto.setStatus(approval.getStatus());
        return dto;
    }

    @Override
    public List<Approval> findByHandler(Long userID) {
        return approvalRepository.findByHandlerID(userID);
    }

    @Override
    public Approval findByID(Long id) {
        Approval approval = null;
        if( approvalRepository.findById(id).isPresent()) {
            approval = approvalRepository.findById(id).get();
        }
        return approval;
    }

    @Override
    public List<Approval> findByMaintenance(Maintenance maintenance) throws Exception {
        return approvalRepository.findByMaintenance(maintenance);
    }

    public Approval save(Approval approval){
        return approvalRepository.save(approval);
    }

    @Override
    public Approval findCurrentApprovalMaintenance(Long maintenanceID) throws Exception {
        if(maintenanceRepository.findById(maintenanceID).isPresent()){
            Maintenance maintenance = maintenanceRepository.findById(maintenanceID).get();
            List<Approval> approvals = findByMaintenance(maintenance);
            for(Approval app : approvals){
                if(Objects.equals(app.getStep(), maintenance.getStep())){
                    return app;
                }
            }
        }

        return null;
    }
}