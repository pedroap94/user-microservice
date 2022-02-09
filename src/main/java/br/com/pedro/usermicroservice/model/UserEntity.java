package br.com.pedro.usermicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;

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
}