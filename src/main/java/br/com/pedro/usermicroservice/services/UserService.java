package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.dto.UserDto;
import br.com.pedro.usermicroservice.exception.CreateUserException;
import br.com.pedro.usermicroservice.model.UserEntity;
import br.com.pedro.usermicroservice.repository.UserRepository;
import br.com.pedro.usermicroservice.util.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

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

    public UserEntity updateUser(UserDto userDto) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("br.com.userentity");
        EntityManager em = emf.createEntityManager();
        Optional<UserEntity> userInDatabase = userRepository.findByUsername(userDto.getUsername());
        UserEntity userUpdated = modelMapper.map(userDto, UserEntity.class);
        if (userInDatabase.isPresent()) {
            userUpdated.setIdOfUser(userInDatabase.get().getIdOfUser());
        } else {
            throw new NoSuchElementException("User doesn't exist");
        }
        em.getTransaction().begin();
        em.merge(userUpdated);
        em.getTransaction().commit();
        em.close();
        emf.close();
        return userRepository.findByUsername(userUpdated.getUsername()).get();
    }
}
