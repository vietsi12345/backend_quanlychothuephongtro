package doanthuctap.invoice_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractDto {
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String note;
    private BigDecimal priceRoom;
    private BigDecimal deposit;
    private BookingDto booking;

}
