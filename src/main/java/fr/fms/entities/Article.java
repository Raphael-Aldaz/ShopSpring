package fr.fms.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String description;
    @NotNull
    private String brand;
    @NotNull
    @DecimalMin("0")
    @DecimalMax("1500")
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)

    private Category category;
}
