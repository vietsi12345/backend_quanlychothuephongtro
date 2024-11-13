package doanthuctap.booking_service.service;

import doanthuctap.booking_service.model.InfoUseResponse;
import doanthuctap.booking_service.model.InfoUser;

import java.util.List;

public interface InfoUserService {
    InfoUser createInfoUser (InfoUser infoUser) throws Exception;

    List<InfoUser> getInfoUsersByContractId (Long contractId) throws Exception ;

    InfoUser getInfoUserById (Long infoUserId) throws Exception;

    InfoUseResponse converInfoUserToinInfoUseResponse (InfoUser infoUser) throws Exception;
}
