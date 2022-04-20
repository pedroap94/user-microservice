package br.com.pedro.usermicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_of_user")
    private Long idOfUser;
    //necessary to implement spring security
    private String username;
    private String password;
    private String authority;
    private String salt;
    //others user's data to statistics and utilities
    private String address;
    private Integer cep;
    private Integer age;
    private String gender;
    private String email;
    private BigInteger phone;
    @OneToMany(mappedBy = "usuarioId")
    private List<Cart> cart = new ArrayList<>();

}
