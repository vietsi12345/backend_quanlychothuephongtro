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
@Table(name = "quanhuyen")
public class Districts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maqh")
    private Long id;

    private String name;

    private String type;

    @ManyToOne
    @JoinColumn(name = "matp", nullable = false)
    @JsonIgnoreProperties("districts")
    private Provinces province;

    @OneToMany(mappedBy = "district")
    @JsonIgnoreProperties("district")
    private List<Communes> communes;
}
