package doanthuctap.booking_service.service;

import doanthuctap.booking_service.model.Contract;

import java.time.LocalDate;
import java.util.List;

public interface ContractService {
    Contract createContract (Contract contract) throws Exception;

    List<Contract> getAllContract () throws  Exception;

    Contract getContractById (Long id) throws  Exception;

    Contract updateContract (Long id, LocalDate startDate,  LocalDate endDate,  String note) throws Exception;


    Contract updateStatusContract (Long id) throws Exception;

    List<Contract> getContractForUser (Long userId) ;
}
