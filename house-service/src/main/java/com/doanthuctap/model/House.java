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
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Lob
    @Column(length = 10000)
    private String description;

    @Embedded
    private Address address;

//    @OneToMany(mappedBy = "house")
//    @JsonManagedReference
//    private List<Room> rooms;
    @OneToMany(mappedBy = "house")
    @JsonIgnoreProperties("house")
    private List<Room> rooms;
}
