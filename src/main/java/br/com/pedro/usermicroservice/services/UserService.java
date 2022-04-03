package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.dto.UserDto;
import br.com.pedro.usermicroservice.exception.CreateUserException;
import br.com.pedro.usermicroservice.model.UserEntity;
import br.com.pedro.usermicroservice.repository.UserRepository;
import br.com.pedro.usermicroservice.util.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private EntityManager entityManager;

    public UserEntity signUp(UserDto userDto) {
        try {
            String encodedPassword = passwordEncoder.encode(userDto.getPassword());
            userDto.setPassword(encodedPassword);
            UserEntity user = modelMapper.map(userDto, UserEntity.class);
            user.setAuthority(Role.USER.getDescription());
            UserEntity userCreate = userRepository.save(user);
            log.info("User created");
            return userCreate;
        } catch (Exception e) {
            log.error("Failed to create user " + userDto.getUsername());
            throw new CreateUserException("Failed to create user");
        }
    }

    public UserEntity userToCart(String username) {
        try {
            Optional<UserEntity> userEntity = userRepository.findByUsername(username);
            if (userEntity.isPresent()) {
                return userEntity.get();
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
        return null;
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(UserDto userDto) {
        Query query = entityManager.createQuery("update UserEntity user set user.age=:age, " +
                "user.address=:address," +
                "user.cep=:cep," +
                "user.email=:email," +
                "user.phone=:phone," +
                "user.gender=:gender" +
                " where user.username=:username");
        query.setParameter("age", userDto.getAge());
        query.setParameter("address", userDto.getAddress());
        query.setParameter("cep", userDto.getCep());
        query.setParameter("email", userDto.getEmail());
        query.setParameter("phone", userDto.getPhone());
        query.setParameter("gender", userDto.getGender());
        query.setParameter("username", userDto.getUsername());
        int rowsAffected = query.executeUpdate();
        if (rowsAffected == 0) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
    }
}
