package maintenance.maintenance.service;

import maintenance.maintenance.model.ApprovalDTO;
import maintenance.maintenance.model.Maintenance;
import maintenance.maintenance.model.MaintenanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MaintenanceService {
    //get all, get theo contract, get theo status, get theo time, get theo nguoi xu ly, duyet

    Maintenance createMaintenanceUser(MaintenanceDTO maintenanceDTO, Integer type) throws Exception;

    String resubmitMaintenance(MaintenanceDTO dto, Long id) throws  Exception;

    List<Maintenance> getAllMaintenance() throws  Exception;


    Maintenance getMaintenanceById (Long id) throws  Exception;

    Maintenance updateMaintenance(MultipartFile imageFile, MaintenanceDTO maintenanceDTO, Long id) throws  Exception;

    Maintenance changeStatusMaintenance(Long id, Integer status) throws  Exception;

    List<Maintenance> getMaintenanceByContract(Long id) throws  Exception;

    List<Maintenance> getMaintenanceByStatus(Integer status) throws  Exception;

    List<Maintenance> getMaintenanceByTime(LocalDateTime start, LocalDateTime end) throws  Exception;

    List<Maintenance> getMaintenanceByHandler(Long userID) throws  Exception;

    Maintenance convertDTOToModelCreated(MaintenanceDTO dto, Integer type) throws Exception;

    Maintenance ApprovalMaintenance(ApprovalDTO dto) throws Exception;

    String cancelMaintenance(Long id) throws  Exception;

    String updateMaintenance(Long id, MaintenanceDTO maintenanceDTO) throws Exception;

    void convertDTOToModelUpdate(MaintenanceDTO dto, Maintenance maintenance) throws Exception;

    String updateMaintenance(MaintenanceDTO dto, Long id) throws Exception;
}