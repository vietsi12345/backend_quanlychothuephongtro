package com.doanthuctap.response;

import com.doanthuctap.model.Room;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeDto {
    private Long id;
    private String name;
    private String description;
    private List<RoomDto> rooms;
}
