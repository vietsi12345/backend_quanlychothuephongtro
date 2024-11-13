package com.doanthuctap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvincesDto {
    private Long id;
    private String name;
    private String type;
}
