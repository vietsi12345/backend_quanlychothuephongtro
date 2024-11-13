package datt.address_service.address_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "xaphuongthitran")
public class Communes {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "xaid")
    private Long id;

    private String name;

    private String type;

    @ManyToOne
    @JoinColumn(name = "maqh", nullable = false)
    @JsonIgnoreProperties("communes")
    private Districts district;


}
