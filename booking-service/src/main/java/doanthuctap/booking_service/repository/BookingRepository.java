package doanthuctap.booking_service.repository;

import doanthuctap.booking_service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository <Booking, Long> {
    List<Booking> findByStatus (String status);

    List<Booking> findAllByOrderByIdDesc ();

    List <Booking> findByUserIdOrderByIdDesc (Long userId);

//    // lấy danh sách booking ở trạng thái đã duyêtj nhưng chưa có hợp đồng
    @Query("SELECT b FROM Booking b WHERE b.status = 'Đã duyệt' AND b.contract IS NULL")
    List<Booking> findApprovedBookingsWithoutContract();
}
