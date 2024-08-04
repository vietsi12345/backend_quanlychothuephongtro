package com.doanthuctap.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HouseWithMinPrice {
    private Long id;
    private String name;
    private String imageBase64;
    private String description;
    private String street;
    private String ward;
    private String district;
    private String city;
    private BigDecimal minRoomPrice;
}
