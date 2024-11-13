package doanthuctap.booking_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictsDto {
    private Long id;
    private String name;
    private String type;
    private ProvincesDto province;
}
