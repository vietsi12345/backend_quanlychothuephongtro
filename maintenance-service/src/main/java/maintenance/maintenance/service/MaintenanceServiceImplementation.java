package maintenance.maintenance.service;

import maintenance.maintenance.model.*;
import maintenance.maintenance.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImplementation implements MaintenanceService{
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private ContractService contractServiceImplementation;
    @Autowired
    private ApprovalServiceImplementation approvalService;
//    @Autowired
//    private BookingServiceImplementation bookingServiceImplementation;

    @Override
    public Maintenance convertDTOToModelCreated(MaintenanceDTO dto, Integer type) throws Exception{
        Maintenance maintenance = new Maintenance();

        maintenance.setName(dto.getName());
        maintenance.setDescription(dto.getDescription());
        maintenance.setStatus(1);
        maintenance.setStep(1);
        maintenance.setCreateAt(LocalDateTime.now());
        maintenance.setModifyAt(null);
        maintenance.setRound(1);

        maintenance.setImageBefore(dto.getImageFile().getBytes());
        maintenance.setRoomID(dto.getRoomID());


        maintenance= maintenanceRepository.save(maintenance);
        createApprovaStepCreated(maintenance, dto.getIdCreator());
        if(type == 0) {
            createApprovalByStep(maintenance);
        }


        return maintenance;
    }

    @Override
    public Maintenance ApprovalMaintenance(ApprovalDTO dto) throws Exception {
        try {
            Approval approval = approvalService.findCurrentApprovalMaintenance(dto.getIdMaintence());
            approval.setHandlerID(dto.getUserID());
            approval.setReview(dto.getReview());
            approval.setStatus(dto.getStatus());
            approval.setApprovalAt(LocalDateTime.now());

            Maintenance maintenance = getMaintenanceById(dto.getIdMaintence());

            if (maintenance.getStep() == 1) {
                switch (dto.getStatus()) {//0: decline, 1: in process 2: approval, 3: RQUD
                    case 0:
                        maintenance.setStatus(4);
                        //0: cancel, 1 in process, 2: Declined, 3: Request Update , 4: completed
                        break;
                    case 2:
                        maintenance.setStep(2);
                        createApprovalByStep(maintenance);
                        break;
                    case 3:
                        maintenance.setStatus(3);
                        break;

                }
            } else if (maintenance.getStep() == 2) {
                switch (dto.getStatus()) {///0: decline, 1: in process 2: approval, 3: RQUD, 4: cancel
                /*case 0:
                    maintenance.setStatus(2);
                    //0: cancel, 1 in process, 2: Declined, 3: Request Update , 4: completed
                    break;*/
                    case 2:

                        maintenance.setTotalMoney(dto.getTotalMoney());
                        approval.setImageEnd(dto.getImageFile().getBytes());
                        maintenance.setStep(3);
                        createApprovalByStep(maintenance);
                        break;
                    case 3:
                        maintenance.setStatus(3);
                        break;
                }
            } else if (maintenance.getStep() == 3) {//0: decline, 1: in process 2: approval, 3: RQUD
                switch (dto.getStatus()) {
                    //0: cancel, 1 in process, 2: Declined, 3: Request Update , 4: completed
                    case 2:
                        maintenance.setStatus(4);
                        break;
                    case 3:
                        maintenance.setStep(1);
                        createApprovalByStep(maintenance);
                        break;
                }
            }

            approvalService.save(approval);


            return maintenanceRepository.save(maintenance);
        }
        catch (NullPointerException nullPointerException){
            System.err.println(nullPointerException.getMessage());
            throw  nullPointerException;
        }
    }

    @Override
    public String cancelMaintenance(Long id) throws Exception {
        try{
            Maintenance maintenance = getMaintenanceById(id);
            if(maintenance.getStep() != 1){
                return "Can't cancel maintenance " + id + " because wrong status";
            }
            maintenance.setStatus(0);

            maintenanceRepository.save(maintenance);
            return "Cancel maintenance " + id + " successfully";
        }
        catch (NullPointerException nullPointerException){
            System.err.println(nullPointerException.getMessage());
            throw nullPointerException;
            //return "Not found maintenance " + id + "  to cancel";
        }
    }

    @Override
    public String updateMaintenance(Long id, MaintenanceDTO maintenanceDTO) throws Exception {
        try {
            Maintenance maintenance = getMaintenanceById(id);
            if(maintenance.getStatus() == 3) {
                convertDTOToModelUpdate(maintenanceDTO, maintenance);
                return "Update maintenance " + id+ " successfully";
            }
            return "Can't update maintenance";
        } catch (NullPointerException nullPointerException){
            System.err.println(nullPointerException.getMessage());
            throw  nullPointerException;
        }
    }

    public void createApprovalByStep(Maintenance maintenance) throws Exception{
        Approval approval = new Approval();
        approval.setStep(maintenance.getStep());
        approval.setMaintenance(maintenance);
        approval.setStatus(1);
        approval.setRound(maintenance.getRound());

        approvalService.save(approval);
    }

    public void createApprovaStepCreated(Maintenance maintenance, Long idCreator) throws Exception{
        Approval approval = new Approval();
        approval.setRound(1);
        approval.setStep(0);
        approval.setApprovalAt(LocalDateTime.now());
        approval.setReview("Created");
        approval.setHandlerID(idCreator);
        approval.setMaintenance(maintenance);
        approval.setStatus(1);

        approvalService.save(approval);
    }

    public void createApproval(Maintenance maintenance, Long idCreator) throws Exception {
        for(int i =1; i< 3;i++){
            Approval approval =  new Approval();
            approval.setStep(i);
            approval.setMaintenance(maintenance);
            approval.setStatus(1);
            approval.setRound(maintenance.getRound());
            approvalService.save(approval);
        }

        Approval approval =  new Approval();
        approval.setStep(3);
        approval.setMaintenance(maintenance);
        approval.setRound(maintenance.getRound());
        approval.setStatus(1);
        approval.setHandlerID(idCreator);
        approvalService.save(approval);
    }

    public Long getUserID(ContractDTO contract) throws Exception {
        return contractServiceImplementation.getContractById(contract.getId()).getBody().getBooking().getUserId();
    }

    @Override
    public void convertDTOToModelUpdate(MaintenanceDTO dto,  Maintenance maintenance) throws Exception {
        maintenance.setName(dto.getName());
        maintenance.setDescription(dto.getDescription());
        maintenance.setStatus(1);
        maintenance.setModifyAt(LocalDateTime.now());
        maintenance.setImageBefore(dto.getImageFile().getBytes());
        maintenance.setRoomID(dto.getRoomID());
    }

    @Override
    public Maintenance createMaintenanceUser(MaintenanceDTO maintenanceDTO, Integer type) throws Exception {
        Maintenance maintenance = convertDTOToModelCreated(maintenanceDTO, type);
        return maintenance;
    }


    @Override
    public String resubmitMaintenance(MaintenanceDTO dto, Long id) throws Exception {
        try {
            Maintenance maintenance = getMaintenanceById(id);
            if (maintenance.getStatus() != 3 && maintenance.getStatus() != 0) {
                return "Wrong status";
            }

            maintenance.setName(dto.getName());
            maintenance.setDescription(dto.getDescription());
            maintenance.setStatus(1);
            maintenance.setStep(1);
            createApprovalByStep(maintenance);
            maintenance.setModifyAt(null);
            maintenance.setRound(maintenance.getRound() + 1);

            maintenance.setImageBefore(dto.getImageFile().getBytes());

            maintenanceRepository.save(maintenance);

            return "Resubmit successfully";
        }
        catch (NullPointerException nullPointerException){
            System.err.println(nullPointerException.getMessage());
            throw nullPointerException;
        }
    }

    @Override
    public String updateMaintenance(MaintenanceDTO dto, Long id) throws Exception {
        try {
            Maintenance maintenance = getMaintenanceById(id);
            if (maintenance.getStep() != 1) {
                return "Wrong step";
            }

            maintenance.setName(dto.getName());
            maintenance.setDescription(dto.getDescription());
            maintenance.setStatus(1);
            maintenance.setStep(1);
            createApprovalByStep(maintenance);
            maintenance.setModifyAt(null);
            maintenance.setRound(maintenance.getRound() + 1);

            maintenance.setImageBefore(dto.getImageFile().getBytes());

            maintenanceRepository.save(maintenance);

            return "Resubmit successfully";
        }
        catch (NullPointerException nullPointerException){
            System.err.println(nullPointerException.getMessage());
            throw nullPointerException;
        }
    }


    @Override
    public List<Maintenance> getAllMaintenance() throws Exception {
        //Pageable pageable = PageRequest.of(page,size);
        return maintenanceRepository.findAll();
    }



    @Override
    public Maintenance getMaintenanceById(Long id) throws Exception {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if(maintenance.isPresent()){
            return maintenance.get();
        }else{
            return null;
        }
    }

    @Override
    public Maintenance updateMaintenance(MultipartFile imageFile, MaintenanceDTO maintenanceDTO, Long id) throws Exception {
        Maintenance maintenance = getMaintenanceById(id);
        convertDTOToModelUpdate(maintenanceDTO, maintenance);
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance changeStatusMaintenance(Long id, Integer status) throws Exception {
        Maintenance maintenance = getMaintenanceById(id);

        maintenance.setStatus(status);

        return maintenanceRepository.save(maintenance);
    }

    @Override
    public List<Maintenance> getMaintenanceByContract(Long id) throws Exception {
        //Pageable pageable = PageRequest.of(page,size);
        return maintenanceRepository.findByRoomID(id);
    }

    @Override
    public List<Maintenance> getMaintenanceByStatus(Integer status) throws Exception {
        //Pageable pageable = PageRequest.of(page,size);
        return maintenanceRepository.findByStatus(status);
    }

    @Override
    public List<Maintenance> getMaintenanceByTime(LocalDateTime start, LocalDateTime end) throws Exception {
        return maintenanceRepository.FindDateBetween(start, end);
       // List<Maintenance> maintenanceList = maintenanceRepository.FindDateBetween(start, end);

        /*int startPos = (int) pageable.getOffset();
        int endPos = Math.min(startPos + pageable.getPageSize(), maintenanceList.size());

        List<Maintenance> splitList = maintenanceList.subList(startPos,endPos);*/

        //return new PageImpl<>(splitList, pageable, maintenanceList.size());

    }

    @Override
    public List<Maintenance> getMaintenanceByHandler(Long userID) throws Exception {
        return null;
    }

}
