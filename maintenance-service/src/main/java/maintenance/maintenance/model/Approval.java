package maintenance.maintenance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "approval")
public class Approval {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private Integer step;
    private Integer round;
    private LocalDateTime approvalAt;
    private String review;
    private Long handlerID;
    private Integer status;
    //0: decline, 1: in process, 2 approval
    @Lob
    @Column(name = "imageEnd", columnDefinition = "LONGBLOB")
    private byte[] imageEnd;

    //private Long MaintenanceID;

    @ManyToOne
    @JoinColumn(name = "maintenanceID", nullable = false)
    @JsonIgnoreProperties("maintenances")
    //@JsonIgnore
    private Maintenance maintenance;
}
