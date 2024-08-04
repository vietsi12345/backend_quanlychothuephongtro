package doanthuctap.booking_service.controller;

import doanthuctap.booking_service.model.Booking;
import doanthuctap.booking_service.model.Contract;
import doanthuctap.booking_service.model.NotificationDto;
import doanthuctap.booking_service.repository.BookingRepository;
import doanthuctap.booking_service.service.ContractService;
import doanthuctap.booking_service.service.NotificationService;
import doanthuctap.booking_service.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingController bookingController;
    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<?> createContract(@RequestBody Contract contract) throws Exception {
        // Xác minh booking có tồn tại không
        Booking booking = bookingRepository.findById(contract.getBooking().getId())
                .orElseThrow(() -> new Exception("Booking not found"));
        if (booking.getContract()!=null){ // nó đangg có hợp đồng mà mình tạo thêm
            return ResponseEntity.status(226).body("Đơn đặt phòng này đã có hợp đồng rồi!");
        }
        else {
            contract.setBooking(booking);
            Contract createContract = contractService.createContract(contract);
            // gửi thông báo tạo hợp đồng thành công tới user
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setType("CREATE_CONTRACT");
            notificationDto.setContent("Đơn tạo thành công hợp đồng cho đơn đặt phòng có id là "+createContract.getBooking().getId());
            notificationDto.setUserId(booking.getUserId());
            notificationDto.setCreatedAt(LocalDate.now());
            notificationService.createNotification(notificationDto);
            /////
            return ResponseEntity.ok(createContract);
        }
    }


    @GetMapping
    public ResponseEntity<List<Contract>>getAllContract () throws Exception {
        List<Contract> contracts = contractService.getAllContract();
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById (@PathVariable Long id) throws Exception {
        Contract contract = contractService.getContractById(id);
        return ResponseEntity.ok(contract);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<Contract> updateContractStatus (@PathVariable Long id) throws  Exception {
        Contract contract =  contractService.updateStatusContract(id);
        // cập nhật lại trạng thái phiếu đặt
        bookingController.updateBookingStatus(contract.getBooking().getId(),"Đã hủy");
        // gửi thông báo tạo chấm dứt hợp đồng  tới user
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setType("CANCEL_CONTRACT");
        notificationDto.setContent("Hợp đồng có id là "+contract.getId() +" đã chấm dứt !!");
        notificationDto.setUserId(contract.getBooking().getUserId());
        notificationDto.setCreatedAt(LocalDate.now());
        notificationService.createNotification(notificationDto);
        /////
        return  ResponseEntity.ok(contract);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contract> updateContract(
            @PathVariable Long id,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String note) {
        try {
            Contract updatedContract = contractService.updateContract(id, startDate, endDate, note);
            return ResponseEntity.ok(updatedContract);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Contract>> getContractForUser (@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getContractForUser(id));
    }
}
