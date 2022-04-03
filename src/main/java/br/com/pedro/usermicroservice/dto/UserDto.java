package br.com.pedro.usermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@AllArgsConstructor
@Data
@Builder
public class UserDto {
    private String username;
    private String password;
    private String address;
    private Integer cep;
    private Integer age;
    private String gender;
    private String phone;
    private String email;
}
