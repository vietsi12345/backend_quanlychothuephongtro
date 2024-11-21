package maintenance.maintenance.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contracts")
public class ContractDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String note;
    private BigDecimal priceRoom;
    private BigDecimal deposit;
//
//    @OneToOne
//    @JoinColumn(name = "booking_id", referencedColumnName = "id")
//    @JsonManagedReference
    private BookingDTO booking;

//    @OneToMany(mappedBy = "contract")
//    @JsonIgnoreProperties("contract")
//    private List<Maintenance> maintenances;
}
