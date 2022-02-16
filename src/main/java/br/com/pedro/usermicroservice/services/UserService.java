package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.dto.UserDto;
import br.com.pedro.usermicroservice.exception.CreateUserException;
import br.com.pedro.usermicroservice.model.UserEntity;
import br.com.pedro.usermicroservice.repository.UserRepository;
import br.com.pedro.usermicroservice.util.Role;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UserEntity signUp(UserDto userDto) {
        try {
            String encodedPassword = passwordEncoder.encode(userDto.getPassword());
            userDto.setPassword(encodedPassword);
            UserEntity user = modelMapper.map(userDto, UserEntity.class);
            user.setAuthority(Role.USER.getRole());
            return userRepository.save(user);
        } catch (Exception e){
            throw new CreateUserException("Failed to create user");
        }
    }
}
