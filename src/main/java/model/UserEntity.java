package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
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
    private List<String> address;
    private Integer CEP;
    private Integer age;
    private String gender;
}
