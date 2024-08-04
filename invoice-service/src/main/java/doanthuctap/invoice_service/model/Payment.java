package doanthuctap.invoice_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String amount;

    @ManyToOne
    @JoinColumn(name = "invoiceId", nullable = false)
    @JsonIgnoreProperties("payments")
    private Invoice invoice;
}
