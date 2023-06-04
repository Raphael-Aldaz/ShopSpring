package fr.fms.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String loginUser;
    @NotNull
    private String passwordUser;
    @NotNull
    private Boolean active = true;

    @OneToMany(mappedBy = "user")
    @Transient
    List<Customer> customers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns =  @JoinColumn(name = "role_id")
    )
    private List<Roles> roles;

    public User(String username, String password) {
        this.loginUser = username;
        this.passwordUser = password;
    }
}
