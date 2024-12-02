package doanthuctap.booking_service.service;

import doanthuctap.booking_service.model.*;
import doanthuctap.booking_service.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContractServiceImplementation implements ContractService {

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private RoomService roomService;
    @Autowired
    private InfoUserService infoUserService;

    @Override
    public Contract createContract(Contract contract) throws Exception {
        contract.setStatus("Còn hợp đồng");
        // lấy giá phòng
        Long roomId= contract.getBooking().getRoomId();
        RoomDto room = roomService.getRoomById(roomId);
        contract.setPriceRoom(room.getPrice());
        ///
        return contractRepository.save(contract);
    }

    @Override
    public List<ContractResponse> getAllContract() throws Exception {
        List<Contract> contracts = contractRepository.findAllByOrderByIdDesc(); // Lấy tất cả các hợp đồng
        List<ContractResponse> contractResponses = new ArrayList<>(); // Khởi tạo danh sách ContractResponse

        for (Contract contract : contracts) {
            ContractResponse contractResponse = convertContractResponse(contract); // Chuyển đổi Contract thành ContractResponse
            contractResponses.add(contractResponse); // Thêm vào danh sách ContractResponse
        }

        return contractResponses; // Trả về danh sách ContractResponse
    }

    @Override
    public Contract getContractById(Long id) throws Exception {
        return contractRepository.findById(id).orElseThrow(()->new Exception("Không tìm thấy contract"));
    }

    @Override
    public Contract updateContract(Long id, LocalDate startDate, LocalDate endDate, String note) throws Exception {
        Contract contract = getContractById(id);
        if (startDate != null) {
            contract.setStartDate(startDate);
        }
        if (endDate != null) {
            contract.setEndDate(endDate);
        }
        if (note != null && !note.isEmpty()) {
            contract.setNote(note);
        }
        return contractRepository.save(contract);
    }


    @Override
    public Contract updateStatusContract(Long id) throws Exception {
        Contract contract = getContractById(id);
        contract.setStatus("Hết hợp đồng");
        return contractRepository.save(contract);
    }

    @Override
    public List<ContractResponse> getContractForUser(Long userId) throws Exception {
        List<Contract> contracts = contractRepository.findByBookingUserIdOrderByIdDesc(userId); // Lấy tất cả các hợp đồng của user
        List<ContractResponse> contractResponses = new ArrayList<>(); // Khởi tạo danh sách ContractResponse

        for (Contract contract : contracts) {
            ContractResponse contractResponse = convertContractResponse(contract); // Chuyển đổi Contract thành ContractResponse
            contractResponses.add(contractResponse); // Thêm vào danh sách ContractResponse
        }

        return contractResponses; // Trả về danh sách ContractResponse
    }

    @Override
    public ContractResponse convertContractResponse(Contract contract) throws Exception {
        ContractResponse contractResponse = new ContractResponse();
        contractResponse.setId(contract.getId());
        contractResponse.setStartDate(contract.getStartDate());
        contractResponse.setEndDate(contract.getEndDate());
        contractResponse.setStatus(contract.getStatus());
        contractResponse.setNote(contract.getNote());
        contractResponse.setPriceRoom(contract.getPriceRoom());
        contractResponse.setDeposit(contract.getDeposit());
        contractResponse.setBooking(contract.getBooking());
        // Khởi tạo danh sách InfoUseResponse
        List<InfoUseResponse> infoUseResponses = new ArrayList<>();

        for (InfoUser infoUser : contract.getInfoUsers()) {
            InfoUseResponse infoUseResponse = infoUserService.converInfoUserToinInfoUseResponse(infoUser);
            infoUseResponses.add(infoUseResponse); // Thêm vào danh sách
        }

        // Đặt danh sách InfoUseResponses vào ContractResponse
        contractResponse.setInfoUseResponses(infoUseResponses);

        return contractResponse;
    }
}
