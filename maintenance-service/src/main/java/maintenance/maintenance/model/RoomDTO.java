package maintenance.maintenance.model;


import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private  Long id;

    private BigDecimal price;

    @Lob
    @Column(length = 10000)
    private String description ;
    private String status;
    @Lob
    @Column( columnDefinition = "LONGBLOB")
    private byte[] image;

    private Long houseId;
}