package doanthuctap.booking_service.service;

import doanthuctap.booking_service.model.CommunesDto;
import doanthuctap.booking_service.model.InfoUseResponse;
import doanthuctap.booking_service.model.InfoUser;
import doanthuctap.booking_service.repository.InfoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoUserServiceImplementation implements InfoUserService{

    @Autowired
    private InfoUserRepository infoUserRepository;
    @Autowired
    private CommunesService communesService;

    @Override
    public InfoUser createInfoUser(InfoUser infoUser) throws Exception {
        return infoUserRepository.save(infoUser);
    }

    @Override
    public List<InfoUser> getInfoUsersByContractId(Long contractId) throws Exception {
        return infoUserRepository.findByContractId(contractId);
    }

    @Override
    public InfoUser getInfoUserById(Long infoUserId) throws Exception {
        return infoUserRepository.findById(infoUserId).orElseThrow(() -> new Exception("Not found infoUser by Id"));
    }

    @Override
    public InfoUseResponse converInfoUserToinInfoUseResponse(InfoUser infoUser) throws Exception {
        InfoUseResponse infoUseResponse = new InfoUseResponse();
        CommunesDto  communesDto = communesService.getCommunesById(infoUser.getCommuneId());
        //
        infoUseResponse.setId(infoUser.getId());
        infoUseResponse.setFullName(infoUser.getFullName());
        infoUseResponse.setBirthday(infoUser.getBirthday());
        infoUseResponse.setBirthday(infoUser.getBirthday());
        infoUseResponse.setCccd(infoUser.getCccd());
        infoUseResponse.setGender(infoUser.getGender());
        infoUseResponse.setCommuneId(infoUser.getCommuneId());
        infoUseResponse.setAddressDetails(infoUser.getAddressDetails());
        infoUseResponse.setCommune(communesDto.getName());
        infoUseResponse.setDistrict(communesDto.getDistrict().getName());
        infoUseResponse.setProvince(communesDto.getDistrict().getProvince().getName());

        return infoUseResponse;
    }
}
