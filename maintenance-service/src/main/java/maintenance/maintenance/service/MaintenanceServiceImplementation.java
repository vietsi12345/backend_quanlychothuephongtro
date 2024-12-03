package maintenance.maintenance.service;

import maintenance.maintenance.exeption.InvalidMaintenanceStatusException;
import maintenance.maintenance.model.*;
import maintenance.maintenance.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        //maintenance.setStep(1);
        //maintenance.setCreateAt(LocalDateTime.now());
        maintenance.setModifyAt(null);
        //maintenance.setRound(1);
        //maintenance.setImageBefore(dto.getImageFile().getBytes());
        maintenance.setRoomID(dto.getRoomID());
        maintenance.setType(type);
        maintenance.setStatus(2);
        maintenance= maintenanceRepository.save(maintenance);

        createApprovaStepCreated(maintenance, dto.getIdCreator(), dto.getImageFile().getBytes());

        if(type == 0) {//0: user, 1: admin
            createApproval(maintenance, 1, 1, null);
        }



        return maintenance;
    }

    public Long getCreator(Long maintenanceID) throws Exception{
        return approvalService.getHandlerId(maintenanceID);
    }

    @Override
    public Maintenance ApprovalMaintenance(ApprovalDTO dto) throws Exception {
        try {
            Maintenance maintenance = getMaintenanceById(dto.getIdMaintence());

            if (maintenance.getStatus() != 1){
                throw new InvalidMaintenanceStatusException ("Invalid status");
            }

            Approval approval = approvalService.findCurrentApprovalMaintenance(dto.getIdMaintence());
            approval.setHandlerID(dto.getUserID());
            approval.setReview(dto.getReview());
            approval.setStatus(dto.getStatus());
            approval.setApprovalAt(LocalDateTime.now());


            Integer currentStep = getStep(maintenance.getID());
            Integer currentRound = getRound(maintenance.getID());

            if (currentStep == 1) {
                switch (dto.getStatus()) {//0: decline, 1: in process 2: approval, 3: RQUD
                    case 0:
                        maintenance.setStatus(4);
                        //0: cancel, 1 in process, 2: Declined, 3: Request Update , 4: completed
                        break;
                    case 2:
                        createApproval(maintenance, 2, currentRound,null);
                        break;
                    case 3:
                        maintenance.setStatus(3);
                        break;

                }
            } else if (currentStep == 2) {
                switch (dto.getStatus()) {///0: decline, 1: in process 2: approval, 3: RQUD
                /*case 0:
                    maintenance.setStatus(2);
                    //0: cancel, 1 in process, 2: Declined, 3: Request Update , 4: completed
                    break;*/
                    case 2:
                        maintenance.setTotalMoney(dto.getTotalMoney());
                        approval.setImageEnd(dto.getImageFile().getBytes());

                        createApproval(maintenance, 3, currentRound, getCreator(maintenance.getID()));
                        break;
                    case 3:
                        maintenance.setStatus(3);
                        break;
                }
            } else if (currentStep == 3) {//0: decline, 1: in process 2: approval, 3: RQUD
                if(Objects.equals(getCreator(maintenance.getID()), dto.getUserID())) {
                    switch (dto.getStatus()) {
                        //0: cancel, 1 in process, 2: Declined, 3: Request Update , 4: completed
                        case 2:
                            maintenance.setStatus(4);
                            break;
                        case 3:
                            maintenance.setStatus(3);
                            //createApproval(maintenance, 1, currentRound + 1, null);
                            break;
                    }
                }
            }

            approvalService.save(approval);


             maintenanceRepository.save(maintenance);
            return getMaintenanceById(maintenance.getID());
        }
        catch (NullPointerException nullPointerException){
            System.err.println(nullPointerException.getMessage());
            throw  nullPointerException;
        }
    }

    @Override
    public Maintenance cancelMaintenance(Long id) throws Exception {
        try{
            Maintenance maintenance = getMaintenanceById(id);
            if(getStep(maintenance.getID()) != 1){
                throw new InvalidMaintenanceStatusException(
                        "Cannot cancel maintenance request. Current step is invalid for resubmission."
                );
            }
            maintenance.setStatus(0);

            return maintenanceRepository.save(maintenance);
            // return getMaintenanceRespnoseById(maintenance.getID());
        }
        catch (NullPointerException nullPointerException){
            System.err.println(nullPointerException.getMessage());
            throw nullPointerException;
            //return "Not found maintenance " + id + "  to cancel";
        }
    }

//
//    public Maintenance updateMaintenance(Long id, MaintenanceDTO maintenanceDTO) throws Exception {
//        try {
//            Maintenance maintenance = getMaintenanceById(id);
//            if(maintenance.getStatus() == 3) {
//                 convertDTOToModelUpdate(maintenanceDTO, maintenance);
//                return maintenance;
//                //return getMaintenanceRespnoseById(maintenance.getID());
//            }
//            throw new InvalidMaintenanceStatusException(
//                    "Cannot update maintenance request. Current status is invalid for resubmission."
//            );
//        } catch (NullPointerException nullPointerException){
//            System.err.println(nullPointerException.getMessage());
//            throw  nullPointerException;
//        }
//    }

    public void createApproval(Maintenance maintenance, Integer step, Integer round, Long handlerID) throws Exception{
        Approval approval = new Approval();
        approval.setStep(step);
        approval.setMaintenance(maintenance);
        approval.setStatus(1);
        approval.setRound(round);
        approval.setHandlerID(handlerID);

        approvalService.save(approval);
    }

    public void createApprovaStepCreated(Maintenance maintenance, Long idCreator, byte[] image) throws Exception{
        Approval approval = new Approval();
        approval.setRound(1);
        approval.setStep(0);
        approval.setApprovalAt(LocalDateTime.now());
        approval.setReview("Created");
        approval.setHandlerID(idCreator);
        approval.setMaintenance(maintenance);
        approval.setStatus(1);
        approval.setImageEnd(image);

        approvalService.save(approval);
    }




//    public void createApproval(Maintenance maintenance, Long idCreator) throws Exception {
//        for(int i =1; i< 3;i++){
//            Approval approval =  new Approval();
//            approval.setStep(i);
//            approval.setMaintenance(maintenance);
//            approval.setStatus(1);
//            approval.setRound(getRound(maintenance.getID()));
//            approvalService.save(approval);
//        }
//
//        Approval approval =  new Approval();
//        approval.setStep(3);
//        approval.setMaintenance(maintenance);
//        approval.setRound(getRound(maintenance.getID()));
//        approval.setStatus(1);
//        approval.setHandlerID(idCreator);
//        approvalService.save(approval);
//    }

    public Long getUserID(ContractDTO contract) throws Exception {
        return contractServiceImplementation.getContractById(contract.getId()).getBody().getBooking().getUserId();
    }


//    public void convertDTOToModelUpdate(MaintenanceDTO dto,  Maintenance maintenance) throws Exception {
//        maintenance.setName(dto.getName());
//        maintenance.setDescription(dto.getDescription());
//        maintenance.setStatus(1);
//        maintenance.setModifyAt(LocalDateTime.now());
//        maintenance.setImageBefore(dto.getImageFile().getBytes());
//        maintenance.setRoomID(dto.getRoomID());
//    }

    @Override
    public String deletMaintenance(Long id) throws Exception {
        try {
            approvalService.deleteByMaintenance(getMaintenanceById(id));
            maintenanceRepository.deleteById(id);
            
            return "Delete maintenance "+ id + " successfully";
        }catch (NullPointerException nullPointerException){
            System.err.println("Error delete maintenance");
            throw nullPointerException;
        }
    }

    @Override
    public Maintenance createMaintenanceUser(MaintenanceDTO maintenanceDTO, Integer type) throws Exception {//0: user, 1: admin
        return convertDTOToModelCreated(maintenanceDTO, type);
        //return getMaintenanceRespnoseById(maintenance.getID());
    }

    @Override
    public Maintenance resubmitMaintenance(MaintenanceDTO dto, Long id) throws Exception {
        try {
            Maintenance maintenance = getMaintenanceById(id);
            if (maintenance.getStatus() != 3 && maintenance.getStatus() != 0) {
                throw new InvalidMaintenanceStatusException(
                        "Cannot resubmit maintenance request. Current status is invalid for resubmission."
                );
            }

            maintenance.setName(dto.getName());
            maintenance.setDescription(dto.getDescription());
            maintenance.setStatus(1);
            //maintenance.setStep(1);
            createApproval(maintenance, 1, getRound(maintenance.getID())+ 1,null);
            maintenance.setModifyAt(LocalDateTime.now());
            //maintenance.setRound(maintenance.getRound() + 1);

            Approval approval = getApprovalStep0(maintenance.getID(), 1);
            approval.setImageEnd(dto.getImageFile().getBytes());

//            maintenance.setImageBefore(dto.getImageFile().getBytes());

            return maintenanceRepository.save(maintenance);

            //return getMaintenanceRespnoseById(maintenance.getID());
        }
        catch (NullPointerException nullPointerException){
            System.err.println(nullPointerException.getMessage());
            throw nullPointerException;
        }
    }

    public Approval getApprovalStep0(Long maintenanceID, Integer round)throws Exception{
        return approvalService.getApprovalStep0(maintenanceID, round);
    }

    @Override
    public Maintenance updateMaintenance(MaintenanceDTO dto, Long id) throws Exception {
        try {
            Maintenance maintenance = getMaintenanceById(id);
            if (getStep(maintenance.getID()) != 1 && maintenance.getType() == 0) {
                throw new InvalidMaintenanceStatusException("Update not succesfully, please make sure step is 1");
            }

            maintenance.setName(dto.getName());
            maintenance.setDescription(dto.getDescription());
            maintenance.setStatus(1);
            //maintenance.setStep(1);
            maintenance.setModifyAt(null);
            //maintenance.setRound(maintenance.getRound() + 1);
            maintenance.setModifyAt(LocalDateTime.now());

            //maintenance.setImageBefore(dto.getImageFile().getBytes());

            Approval approval = getApprovalStep0(maintenance.getID(), 1);
            approval.setImageEnd(dto.getImageFile().getBytes());

            if(maintenance.getType() == 1){
                maintenance.setTotalMoney(dto.getTotalMoney());
            }

            return maintenanceRepository.save(maintenance);

            //return getMaintenanceRespnoseById(maintenance.getID());
        }
        catch (NullPointerException nullPointerException){
            System.err.println(nullPointerException.getMessage());
            throw nullPointerException;
        }
    }

    @Override
    public List<Maintenance> getMyMaintenance(Long creator) throws Exception {
        List<Approval> approvals = approvalService.getStep0ByCreator(creator);
        List<Maintenance> result = new ArrayList<>();
        for(Approval a : approvals){
            result.add(getMaintenanceById(a.getMaintenance().getID()));
        }

        return result;
    }


    @Override
    public List<Maintenance> getAllMaintenance() throws Exception {
        //Pageable pageable = PageRequest.of(page,size);
        //maintenanceRepository.findAllMaintenanceResponse();
        return maintenanceRepository.findAll();
    }

    @Override
    public Integer getRound(Long idMaintenance) throws Exception {
        return approvalService.findRound(idMaintenance);
    }

    @Override
    public Integer getStep(Long idMaintenance) throws Exception {
        return approvalService.findStep(idMaintenance); // Trả về null nếu không tìm thấy round nào
    }

//    @Override
//    public List<MaintenanceResponse> getAllMaintenance() throws Exception {
//        return maintenanceRepository.findAllMaintenanceResponse().stream()
//                .map(p -> new MaintenanceResponse(
//                        p.getId(),
//                        p.getCreate_at(),
//                        p.getDescription(),
//                        p.getImage_before(),
//                        p.getModify_at(),
//                        p.getName(),
//                        p.getStatus(),
//                        p.getTotal_money(),
//                        p.getRoomid(),
//                        p.getType(),
//                        p.getRound(),
//                        p.getStep()
//                ))
//                .collect(Collectors.toList());
//    }



    @Override
    public Maintenance getMaintenanceById(Long id) throws Exception {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        return maintenance.orElse(null);
    }

//    public MaintenanceResponse getMaintenanceRespnoseById(Long id) throws Exception {
//        return maintenanceRepository.findByIDSP(id);
//
//    }


//    public Maintenance updateMaintenance(MultipartFile imageFile, MaintenanceDTO maintenanceDTO, Long id) throws Exception {
//        Maintenance maintenance = getMaintenanceById(id);
//        convertDTOToModelUpdate(maintenanceDTO, maintenance);
//        return maintenanceRepository.save(maintenance);
//        //return getMaintenanceRespnoseById(maintenance.getID());
//    }

    @Override
    public Maintenance changeStatusMaintenance(Long id, Integer status) throws Exception {
        Maintenance maintenance = getMaintenanceById(id);

        maintenance.setStatus(status);

        return maintenanceRepository.save(maintenance);
        //return getMaintenanceRespnoseById(maintenance.getID());
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
