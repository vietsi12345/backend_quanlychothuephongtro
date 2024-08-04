package doanthuctap.invoice_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {
    private Long id;

    private String name;

    private String unit;

    private BigDecimal unitPrice;

    private String  description;
}
