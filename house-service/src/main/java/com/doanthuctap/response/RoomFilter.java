package com.doanthuctap.response;

import com.doanthuctap.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomFilter {
    private  Long id;

    private String name;
    private BigDecimal price;
    private int area;
    private String imageBase64;
    private String description;
    private String status;
    private House house;
    private CommunesDto commune;
}
