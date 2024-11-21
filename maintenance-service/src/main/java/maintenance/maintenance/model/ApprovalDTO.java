package maintenance.maintenance.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class ApprovalDTO implements Serializable {
    private Long userID;
    private String review;
    private Long idMaintence;
    private Integer status;//0: decline, 1: in process, 2 approval

    //=========field for approval b2
    private Long totalMoney;
    private MultipartFile imageFile;

}
