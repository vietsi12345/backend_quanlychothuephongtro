package maintenance.maintenance.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "maintenance")
/*@NamedStoredProcedureQuery(name = "datt_booking_service.FindDateBetween",
        procedureName = "FindDateBetween", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "startDate", type = LocalDateTime.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "endDate", type = LocalDateTime.class)})*/
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private String name;
    private String description;
    private Integer status;
    //0: cancel, 1 in process, 2: Declined, 3: Request Update , 4: completed
    private Integer step;//1, 2, 3
    private LocalDateTime createAt;
    private LocalDateTime modifyAt;//tgian cancel
    private Long totalMoney;
    //private LocalDateTime approvalAt;
    //private LocalDateTime doneAt;
    private Integer Round; // so vong bao tri
    //private Long handlerID;

    @Lob
    @Column(name = "imageBefore", columnDefinition = "LONGBLOB")
    private byte[] imageBefore;

//    @ManyToOne
//    @JoinColumn(name = "contractId", nullable = false)
//    @JsonIgnore
     private Long roomID;

    /*@OneToMany(mappedBy = "maintenance")
    @JsonIgnoreProperties("maintenance")
    private List<Review> reviews;*/

    @OneToMany(mappedBy = "maintenance", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("maintenance")
    private List<Approval> approvals;
}
