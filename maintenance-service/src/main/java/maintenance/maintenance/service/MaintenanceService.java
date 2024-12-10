package maintenance.maintenance.service;

import maintenance.maintenance.model.ApprovalDTO;
import maintenance.maintenance.model.Maintenance;
import maintenance.maintenance.model.MaintenanceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MaintenanceService {
    //get all, get theo contract, get theo status, get theo time, get theo nguoi xu ly, duyet

    Maintenance createMaintenanceUser(MaintenanceDTO maintenanceDTO, Integer type) throws Exception;

    Maintenance resubmitMaintenance(MaintenanceDTO dto, Long id) throws  Exception;

    List<Maintenance> getAllMaintenance() throws  Exception;

    Integer getRound(Long idMaintenance) throws Exception;

    Integer getStep(Long idMaintenance) throws Exception;

    Maintenance getMaintenanceById (Long id) throws  Exception;

    //Maintenance updateMaintenance(MultipartFile imageFile, MaintenanceDTO maintenanceDTO, Long id) throws  Exception;

    Maintenance changeStatusMaintenance(Long id, Integer status) throws  Exception;

    List<Maintenance> getMaintenanceByContract(Long id) throws  Exception;

    List<Maintenance> getMaintenanceByStatus(Integer status) throws  Exception;

    List<Maintenance> getMaintenanceByTime(LocalDateTime start, LocalDateTime end) throws  Exception;

    List<Maintenance> getMaintenanceByHandler(Long userID) throws  Exception;

    Maintenance convertDTOToModelCreated(MaintenanceDTO dto, Integer type) throws Exception;

    Maintenance ApprovalMaintenance(ApprovalDTO dto) throws Exception;

    Maintenance cancelMaintenance(Long idMantenance, Long idCancel) throws  Exception;

    //Maintenance updateMaintenance(Long id, MaintenanceDTO maintenanceDTO) throws Exception;

    //void convertDTOToModelUpdate(MaintenanceDTO dto, Maintenance maintenance) throws Exception;

    String deletMaintenance(Long id) throws Exception;

    Maintenance updateMaintenance(MaintenanceDTO maintenanceDTO, Long id) throws Exception;

    List<Maintenance> getMyMaintenance(Long creator) throws  Exception;
}