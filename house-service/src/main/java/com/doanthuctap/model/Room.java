package com.doanthuctap.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Blob;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private BigDecimal price;

    @Lob
    @Column(length = 10000)
    private String description ;
    private String availability;
    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

//    @ManyToOne
//    @JoinColumn(name = "houseId", nullable = false)
//    @JsonBackReference
//    private House house;
//
//    @ManyToOne
//    @JoinColumn(name = "roomTypeId", nullable = false)
//    @JsonBackReference
//    private RoomType roomType;
    @ManyToOne
    @JoinColumn(name = "houseId", nullable = false)
    @JsonIgnoreProperties("rooms")
    private House house;

    @ManyToOne
    @JoinColumn(name = "roomTypeId", nullable = false)
    @JsonIgnoreProperties("rooms")
    private RoomType roomType;
}
