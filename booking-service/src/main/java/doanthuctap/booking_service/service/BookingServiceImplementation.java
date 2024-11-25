package doanthuctap.booking_service.service;

import doanthuctap.booking_service.model.Booking;
import doanthuctap.booking_service.model.Contract;
import doanthuctap.booking_service.model.NotificationDto;
import doanthuctap.booking_service.model.RoomDto;
import doanthuctap.booking_service.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImplementation implements BookingService{

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomService roomService;
    @Autowired
    private NotificationService notificationService;


    @Override
    public Booking createBooking(Booking booking) throws Exception {
        booking.setConfirmationCode(generateConfirmationCode());
        booking.setStatus("Đang chờ duyệt");
        booking.setCreateAt(LocalDateTime.now());
        //lưu booking
        Booking savedBooking = bookingRepository.save(booking);
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setType("CREATE_BOOKING");
        notificationDto.setContent("Khách hàng có userId:" +savedBooking.getUserId()+" Vừa tạo 1 đơn đặt phòng mới!!");
        notificationDto.setUserId(Long.valueOf(1));
        notificationDto.setCreatedAt(LocalDate.now());
        notificationService.createNotification(notificationDto);
        return savedBooking;
    }

    @Override
    public Booking getBookingById(Long id) throws Exception {
        return bookingRepository.findById(id).orElseThrow(() -> new Exception("Booking not found"));
    }

    @Override
    public List<Booking> getAllBooking() throws Exception {
        return bookingRepository.findAllByOrderByIdDesc();
    }

    @Override
    public List<Booking> getBookingsByStatus(String status) throws Exception {
        return bookingRepository.findByStatus(status);
    }

    @Override
    public List<Booking> getBookingsForUser(Long userId) throws Exception {
        return bookingRepository.findByUserIdOrderByIdDesc(userId);
    }

    @Override
    public Booking updateBookingStatus(Long id, String status) throws Exception {
        Booking booking = getBookingById(id);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> findApprovedBookingsWithoutContract() {
        return bookingRepository.findApprovedBookingsWithoutContract();
    }

//    @Override
//    public String generateConfirmationCode() {
//        return UUID.randomUUID().toString();
//    }
@Override
public String generateConfirmationCode() {
    int min = 10000000;
    int max = 99999999;
    int confirmationCode = (int) (Math.random() * (max - min + 1)) + min;

    // Kiểm tra xem mã xác nhận đã tồn tại trong cơ sở dữ liệu chưa
    while (bookingRepository.findByConfirmationCode(String.valueOf(confirmationCode)) != null) {
        confirmationCode = (int) (Math.random() * (max - min + 1)) + min;
    }

    return String.valueOf(confirmationCode);
}

    @Override
    public Integer getRemainingContractOfRoom(Long roomId) throws Exception {
        // Tìm booking theo roomId và status đã duyệt
        List <Booking> bookings = bookingRepository.findByRoomIdAndStatus(roomId, "đã duyệt");
        // Kiểm tra danh sách rỗng
        if (bookings == null || bookings.isEmpty()) {
            return -2; // phòng đang trống
        }
        // Lấy booking đầu tiên vì chỉ có 1 bookings duy nhất nếu có
        Booking booking = bookings.get(0);
        Contract contract = booking.getContract();
        // Kiểm tra contract
        if (contract == null) { // đã có người đặt rồi nhưng chưa đc tạo hợp đồng
            return -1;
        }
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = contract.getEndDate();
        // Nếu hợp đồng đã hết hạn
//        if (currentDate.isAfter(endDate)) {
//            return -1;
//        }
        // nếu hợp đồng đã chấm dứt
        if (contract.getStatus().equals("Đã chấm dứt")) {
            return -3;
        }
        // Tính số ngày còn lại
        int daysRemaining = (int) ChronoUnit.DAYS.between(currentDate, endDate);
        return daysRemaining;
    }
}
