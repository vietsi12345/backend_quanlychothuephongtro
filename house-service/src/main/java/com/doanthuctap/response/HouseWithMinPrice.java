package com.doanthuctap.response;

import com.doanthuctap.model.Room;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class HouseWithMinPrice {
    private Long id;
    private String name;
    private String imageBase64;
    private String description;
    private Long idCommune;
    private Boolean isActive;
    private String addressDetails;
    private String commune;
    private String district;
    private String province;
    private BigDecimal minRoomPrice;
}
