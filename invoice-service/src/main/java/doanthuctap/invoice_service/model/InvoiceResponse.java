package doanthuctap.invoice_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {
    private Long id;
    private Long contractId;
    private YearMonth billMonth;
    private LocalDateTime createdAt;
    private String Status;
    private LocalDate dueDate;
    private BigDecimal priceRoom;
    private BigDecimal priceService;
}
