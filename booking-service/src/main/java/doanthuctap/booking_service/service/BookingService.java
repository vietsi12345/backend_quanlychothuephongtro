package doanthuctap.booking_service.service;

import doanthuctap.booking_service.model.Booking;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking) throws Exception;

    Booking getBookingById (Long id) throws  Exception;

    List<Booking> getAllBooking () throws  Exception;

    List<Booking> getBookingsByStatus  (String status) throws  Exception;

    List<Booking> getBookingsForUser  (Long userId) throws  Exception;

    Booking updateBookingStatus (Long id, String status) throws  Exception;

    List <Booking> findApprovedBookingsWithoutContract();

    String generateConfirmationCode();

    Integer getRemainingContractOfRoom (Long roomId) throws Exception;
}
