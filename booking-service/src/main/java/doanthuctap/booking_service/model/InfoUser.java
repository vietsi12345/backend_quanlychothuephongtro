package doanthuctap.booking_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "InfoUsers")
public class InfoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private LocalDate birthday;
    private String addressDetails;
    private Boolean gender;
    private String cccd;

    private Long communeId;

    @ManyToOne
    @JoinColumn(name = "contractId", nullable = false)
    @JsonIgnoreProperties("infoUsers")
    private Contract contract;
}
