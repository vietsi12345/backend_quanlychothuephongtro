package doanthuctap.booking_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String note;
    private BigDecimal priceRoom;
    private BigDecimal deposit;

    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    @JsonManagedReference
    private Booking booking;

    @OneToMany(mappedBy = "contract")
    @JsonIgnoreProperties("contract")
    private List<InfoUser> infoUsers;
}

