package doanthuctap.booking_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private String adults;

    private String children;

    private String confirmationCode;

    private String status ; // đang chờ duyệt, đã duyệt, đã hủy

    private Long userId;

    private Long roomId;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonBackReference
    private Contract contract;
}


