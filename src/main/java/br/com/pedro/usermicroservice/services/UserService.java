package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.dto.UserDto;
import br.com.pedro.usermicroservice.exception.CreateUserException;
import br.com.pedro.usermicroservice.exception.DeleteUserException;
import br.com.pedro.usermicroservice.model.UserEntity;
import br.com.pedro.usermicroservice.repository.UserRepository;
import br.com.pedro.usermicroservice.util.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private EntityManager entityManager;

    public UserEntity signUp(UserDto userDto) {
        try {
            Optional<UserEntity> verifyUsernameNotInUse = userRepository.findByUsername(userDto.getUsername());
            if (verifyUsernameNotInUse.isPresent()) {
                throw new CreateUserException("Username already in use");
            }
            String salt = RandomStringUtils.random(5, true, true);
            userDto.setPassword(generatePassword(userDto.getPassword(), salt));
            UserEntity user = modelMapper.map(userDto, UserEntity.class);
            user.setAuthority(Role.USER.getDescription());
            user.setSalt(salt);
            UserEntity userCreate = userRepository.save(user);
            log.info("User created");
            return userCreate;
        } catch (CreateUserException e) {
            log.error("Username " + userDto.getUsername() + " is already in use");
            throw new CreateUserException("Username already in use");
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

    public UserEntity findByUsername(String username) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UsernameNotFoundException("User doesn't exist");
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

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(String username) {
        Query query = entityManager.createQuery("delete from UserEntity user where user.username=:username");
        query.setParameter("username", username);
        int rowsAffected = query.executeUpdate();
        if (rowsAffected == 0) {
            throw new DeleteUserException("Failed to delete user");
        }
    }

    private String generatePassword(String password, String salt) throws NoSuchAlgorithmException {
        String passwordWithSalt = password + salt;

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] byteMessage = messageDigest.digest(passwordWithSalt.getBytes(StandardCharsets.UTF_8));

        StringBuilder passwordReturn = new StringBuilder();
        for (byte transformByteInMessage : byteMessage) {
            passwordReturn.append(transformByteInMessage);
        }
        return passwordReturn.toString();
    }
}
