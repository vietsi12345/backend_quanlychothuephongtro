package doanthuctap.booking_service.service;

import doanthuctap.booking_service.model.Booking;
import doanthuctap.booking_service.model.NotificationDto;
import doanthuctap.booking_service.model.RoomDto;
import doanthuctap.booking_service.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public String generateConfirmationCode() {
        return UUID.randomUUID().toString();
    }
}
