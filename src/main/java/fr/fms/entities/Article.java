package fr.fms.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;


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
    private double price;
    @NotNull
    private int quantity = 1;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
}
