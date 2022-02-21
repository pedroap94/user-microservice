package br.com.pedro.usermicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
//    @OneToOne
    @Convert(converter = Converters.class)
    private List<Cart> cart;
}
