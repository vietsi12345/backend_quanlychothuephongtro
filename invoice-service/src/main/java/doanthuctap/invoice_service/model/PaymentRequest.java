package doanthuctap.invoice_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String paymentMethod;
    private BigDecimal amount;
    private Long invoiceId;
}
