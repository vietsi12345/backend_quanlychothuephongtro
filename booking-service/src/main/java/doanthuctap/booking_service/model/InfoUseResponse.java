package doanthuctap.booking_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoUseResponse {
    private Long id;

    private String fullName;
    private LocalDate birthday;
    private String addressDetails;
    private Boolean gender;
    private String cccd;

    private Long communeId;
    private String commune;
    private String district;
    private String province;
}
