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
    private String name;
    private String description;
    private Long contractID;
    private MultipartFile imageFile;
}