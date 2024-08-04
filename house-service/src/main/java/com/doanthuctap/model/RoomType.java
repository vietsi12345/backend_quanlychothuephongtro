package com.doanthuctap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "roomTypes")
public class RoomType {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    @Column(length = 10000)
    private String description;

//    @OneToMany(mappedBy = "roomType")
//    @JsonManagedReference
//    private List<Room> rooms;
    @OneToMany(mappedBy = "roomType")
    @JsonIgnoreProperties("roomType")
    private List<Room> rooms;
}
