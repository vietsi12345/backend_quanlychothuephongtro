package doanthuctap.booking_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private  Long id;

    private BigDecimal price;

    @Lob
    @Column(length = 10000)
    private String description ;
    private String availability;
    @Lob
    @Column( columnDefinition = "LONGBLOB")
    private byte[] image;

    private Long houseId;
    private Long roomTypeId;
}
