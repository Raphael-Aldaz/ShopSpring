package fr.fms.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity(name = "`Order`")
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Order {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date;
    @NotNull
    private double totalAmount;

    @OneToMany(mappedBy = "order")
    private Collection<OrderItem> orderItems;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


}
