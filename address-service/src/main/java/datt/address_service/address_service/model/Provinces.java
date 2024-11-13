package datt.address_service.address_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tinhthanhpho")
public class Provinces {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matp")
    private Long id;

    private String name;

    private String type;

    @OneToMany(mappedBy = "province")
    @JsonIgnoreProperties("province")
    private List<Districts> districts;
}
