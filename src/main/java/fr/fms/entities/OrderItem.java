package fr.fms.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class OrderItem {
    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private int quantity;
    @NotNull
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    private Order order;


}
