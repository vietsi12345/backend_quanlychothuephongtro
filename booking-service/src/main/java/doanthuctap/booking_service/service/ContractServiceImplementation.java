package doanthuctap.booking_service.service;

import doanthuctap.booking_service.model.Contract;
import doanthuctap.booking_service.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContractServiceImplementation implements ContractService {

    @Autowired
    private ContractRepository contractRepository;
    @Override
    public Contract createContract(Contract contract) throws Exception {
        contract.setStatus("Còn hợp đồng");
        return contractRepository.save(contract);
    }

    @Override
    public List<Contract> getAllContract() throws Exception {
        return contractRepository.findAllByOrderByIdDesc();
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
    public List<Contract> getContractForUser(Long userId) {
        return contractRepository.findByBookingUserIdOrderByIdDesc(userId);
    }
}
