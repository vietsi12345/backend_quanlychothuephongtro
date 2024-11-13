package doanthuctap.booking_service.controller;

import doanthuctap.booking_service.model.InfoUser;
import doanthuctap.booking_service.service.ContractService;
import doanthuctap.booking_service.service.InfoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/infoUsers")
public class InfoUserController {
    @Autowired
    private InfoUserService infoUserService;

    @Autowired
    private ContractService contractService;

    @PostMapping
    ResponseEntity <InfoUser> createInfoUser (@RequestBody InfoUser infoUser) throws Exception {
        Long idContract = infoUser.getContract().getId();
        infoUser.setContract(contractService.getContractById(idContract));
        return ResponseEntity.ok(infoUserService.createInfoUser(infoUser));
    }

    @GetMapping ("/contract/{idContract}")
    ResponseEntity <List<InfoUser>> getInfoUserByContract (@PathVariable Long idContract) throws Exception {
        return ResponseEntity.ok(infoUserService.getInfoUsersByContractId(idContract));
    }

    @GetMapping ("/{idInfoUser}")
    ResponseEntity <InfoUser> getInfoUser (@PathVariable Long idInfoUser) throws Exception {
        return ResponseEntity.ok(infoUserService.getInfoUserById(idInfoUser));
    }
}
