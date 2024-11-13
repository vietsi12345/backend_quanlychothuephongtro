package doanthuctap.booking_service.controller;

import doanthuctap.booking_service.model.Booking;
import doanthuctap.booking_service.model.Contract;
import doanthuctap.booking_service.model.NotificationDto;
import doanthuctap.booking_service.model.RoomDto;
import doanthuctap.booking_service.repository.BookingRepository;
import doanthuctap.booking_service.repository.ContractRepository;
import doanthuctap.booking_service.service.BookingService;
import doanthuctap.booking_service.service.NotificationService;
import doanthuctap.booking_service.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class    BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private RoomService roomService;

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) throws Exception {
        RoomDto roomDto = roomService.getRoomById(booking.getRoomId());
        if ("AVAILABLE".equals(roomDto.getStatus())) {
            Booking pendingBooking = bookingRepository.findByUserIdAndRoomIdAndStatus(
                    booking.getUserId(), booking.getRoomId(), "ĐANG CHỜ DUYỆT"
            );
            if (pendingBooking != null ) {
                return ResponseEntity.status(226).body("Bạn đã đặt phòng này rồi, đơn của bạn đang được giải quyết !!");
            }
            else {
                Booking createBooking = bookingService.createBooking(booking);
                return ResponseEntity.ok(createBooking);
            }
        } else if ("MAINTENANCE".equals(roomDto.getStatus())) {
            return ResponseEntity.status(226).body("Phòng này đang bảo trì!!");
        }
        else {
            return ResponseEntity.status(226).body("Phòng này đã được thuê");
        }
    }

    @GetMapping
    public ResponseEntity <List<Booking>> getAllBooking () throws Exception {
        List<Booking> allBooking = bookingService.getAllBooking();
        return ResponseEntity.ok(allBooking);
    }

    @GetMapping("/{id}")
    public ResponseEntity <Booking> getBookingById (@PathVariable Long id) throws  Exception {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @GetMapping ("/status")
    public ResponseEntity <List<Booking>> getBookingsByStatus (@RequestParam("status") String status) throws  Exception {
        List<Booking> bookings = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping ("/user/{userId}")
    public ResponseEntity <List<Booking>> getBookingsByStatus (@PathVariable Long userId) throws  Exception {
        List<Booking> bookings = bookingService.getBookingsForUser(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping ("/booking-success-status-and-without")
    public ResponseEntity <List<Booking>> findApprovedBookingsWithoutContract () throws  Exception {
        List<Booking> bookings = bookingService.findApprovedBookingsWithoutContract();
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/update-status/{id}")
    public ResponseEntity <?> updateBookingStatus (
            @PathVariable Long id,
            @RequestParam("status") String status) throws  Exception {
        Booking bookingOld = bookingService.getBookingById(id);
        RoomDto roomDto = roomService.getRoomById(bookingOld.getRoomId());
        if (bookingOld.getStatus().equals("Đã hủy")) {
            return ResponseEntity.status(226).body("Phiếu đặt này đã hủy rồi!!!");
        }  else if (bookingOld.getStatus().equals("Đang chờ duyệt")) {
            if (status.equals("Đã duyệt")) { // trường hợp từ đang chờ duyệt thành đã duyệt
                // duyệt đơn đặt phòng
                Booking booking = bookingService.updateBookingStatus(id,status);
                // gửi thông báo thành công đến khách hàng
                NotificationDto ntfSuccess = new NotificationDto();
                ntfSuccess.setType("UPDATE_BOOKING");
                ntfSuccess.setContent("Chúc mừng bạn, đơn đặt phòng có ID là "+id +" đã được duyệt!");
                ntfSuccess.setUserId(booking.getUserId());
                ntfSuccess.setCreatedAt(LocalDate.now());
                notificationService.createNotification(ntfSuccess);
                // cập nhật lại trạng thái phòng thành "RENTED"
                RoomDto room = roomService.updateStatus(roomDto.getId(),"RENTED");
                // hủy các đơn đặt phòng trùng khác
                List<Booking> bookingsNeedCancel = bookingRepository.findByRoomIdAndStatus(booking.getRoomId(), "Đang chờ duyệt");
                for (Booking bookingNeedCancel : bookingsNeedCancel){
                    bookingService.updateBookingStatus(bookingNeedCancel.getId(),"Đã hủy");
                    // gửi thông báo đến khách hàng
                    NotificationDto ntfCancel = new NotificationDto();
                    ntfCancel.setType("UPDATE_BOOKING");
                    ntfCancel.setContent("Xin lỗi bạn !!Đơn đặt phòng có ID là "+bookingNeedCancel.getId() +" đã bị hủy!");
                    ntfCancel.setUserId(booking.getUserId());
                    ntfCancel.setCreatedAt(LocalDate.now());
                    notificationService.createNotification(ntfCancel);
                }
                return ResponseEntity.ok(booking);
            } else { // từ đang chờ duyệt chuyển sang đã hủy
                Booking booking = bookingService.updateBookingStatus(id,status);
                // Gửi thông báo đã hủy đơn tới user
                NotificationDto notificationDto = new NotificationDto();
                notificationDto.setType("UPDATE_BOOKING");
                notificationDto.setContent("Xin lỗi bạn !!Đơn đặt phòng có ID là "+id +" đã bị hủy!");
                notificationDto.setUserId(booking.getUserId());
                notificationDto.setCreatedAt(LocalDate.now());
                notificationService.createNotification(notificationDto);
                /////
                return ResponseEntity.ok(booking);
            }
        } else { // trường hợp booking đang ở trạng thái đã duyệt
            if (status.equals("Đã hủy")) {
                if (bookingOld.getContract() != null){
                    return ResponseEntity.status (226).body("Đã có hợp đồng rồi, vui lòng hủy hợp đồng để hủy booking này!");
                }
                else {
                    Booking booking = bookingService.updateBookingStatus(id, status);
                    RoomDto room = roomService.updateStatus(booking.getRoomId(), "AVAILABLE");
                    // Gửi thông báo đã hủy đơn tới user
                    NotificationDto notificationDto = new NotificationDto();
                    notificationDto.setType("UPDATE_BOOKING");
                    notificationDto.setContent("Xin lỗi bạn !!Đơn đặt phòng có ID là "+id +" đã bị hủy!");
                    notificationDto.setUserId(booking.getUserId());
                    notificationDto.setCreatedAt(LocalDate.now());
                    notificationService.createNotification(notificationDto);
                    return ResponseEntity.ok(booking);
                }
            } else {
                return ResponseEntity.status (226).body("Chỉ có thể hủy đơn đặt phòng này thôi!!");
            }
        }
    }
}
