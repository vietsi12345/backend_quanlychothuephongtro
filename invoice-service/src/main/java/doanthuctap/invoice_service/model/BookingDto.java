package doanthuctap.invoice_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private String adults;

    private String children;

    private String confirmationCode;

    private String status ; // đang chờ duyệt, đã duyệt, đã hủy

    private Long userId;

    private Long roomId;

}
