package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.dto.UserDto;
import br.com.pedro.usermicroservice.exception.CreateUserException;
import br.com.pedro.usermicroservice.model.Cart;
import br.com.pedro.usermicroservice.model.UserEntity;
import br.com.pedro.usermicroservice.repository.UserRepository;
import br.com.pedro.usermicroservice.util.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private CartService cartService;

    public UserEntity signUp(UserDto userDto) {
        try {
            String encodedPassword = passwordEncoder.encode(userDto.getPassword());
            userDto.setPassword(encodedPassword);
            UserEntity user = modelMapper.map(userDto, UserEntity.class);
            user.setAuthority(Role.USER.getRole());
            userRepository.save(user);
            Cart cart = cartService.createCart(user);
            user.setCart(cart);
            UserEntity userCreator = userRepository.save(user);
            log.info("User created");
            return userCreator;
        } catch (Exception e) {
            log.error("Failed to create user " + userDto.getUsername());
            throw new CreateUserException("Failed to create user");
        }
    }

    public UserEntity userToCart(List<String> list) {
        try {
            Optional<UserEntity> userEntity = userRepository.findByUsername(list.get(0));
            if (userEntity.isPresent()) {
                return userEntity.get();
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
        return null;
    }
}
