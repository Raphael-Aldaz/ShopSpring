package fr.fms.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor  @ToString
public class Roles {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotNull
    private String role;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
