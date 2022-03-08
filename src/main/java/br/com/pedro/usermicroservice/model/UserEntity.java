package br.com.pedro.usermicroservice.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    //necessary to implement spring security
    private String username;
    private String password;
    private String authority;
    private String secret;
    //others user's data to statistics and utilities
    private String address;
    private Integer cep;
    private Integer age;
    private String gender;
    private String email;
    private BigInteger phone;
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id", referencedColumnName = "id")
    private Cart cart;
}
