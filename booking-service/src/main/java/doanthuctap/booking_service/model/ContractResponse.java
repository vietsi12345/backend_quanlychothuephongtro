package doanthuctap.booking_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponse {
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String note;
    private BigDecimal priceRoom;
    private BigDecimal deposit;
    private Booking booking;
    private List<InfoUseResponse> infoUseResponses;
}
