package maintenance.maintenance.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class MaintenanceDTO implements Serializable {
    private Long idCreator;
    private String name;
    private String description;
    private Long roomID;
    private MultipartFile imageFile;
    private Long totalMoney;
}
