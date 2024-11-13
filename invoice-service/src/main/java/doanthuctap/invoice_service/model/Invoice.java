package doanthuctap.invoice_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Long contractId;
//    private BigDecimal roomPrice;
    private YearMonth billMonth;
//    private BigDecimal totalService;
//    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private String Status;
    private LocalDate dueDate;

    @OneToMany(mappedBy = "invoice")
    @JsonIgnoreProperties("invoice")
    private List<Payment> payments;
}
