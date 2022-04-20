package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.dto.UserDto;
import br.com.pedro.usermicroservice.exception.CreateUserException;
import br.com.pedro.usermicroservice.model.UserEntity;
import br.com.pedro.usermicroservice.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Create new user")
    void whenSendUserDtoToCreateIsSuccessful() {
        //Scenario preparation

        UserDto userTest = generateUserDto();
        UserEntity user = new UserEntity();
        user.setPassword(userTest.getPassword());
        user.setUsername(userTest.getUsername());

        //Prepare testing
        Mockito.when(modelMapper.map(userTest, UserEntity.class)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserEntity response = userService.signUp(userTest);
        Assertions.assertThat(response.getUsername()).isEqualTo(userTest.getUsername());
    }

    @Test
    void whenSendRepeatedUserEntityThrowException() {
        UserDto userTest = generateUserDto();
        UserEntity user = new UserEntity();
        user.setPassword(userTest.getPassword());
        user.setUsername(userTest.getUsername());
        Optional<UserEntity> userEntityOptional = Optional.of(user);

        Mockito.when(modelMapper.map(userTest, UserEntity.class)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(userEntityOptional);

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(CreateUserException.class, () -> {
            userService.signUp(userTest);
        });
        org.junit.jupiter.api.Assertions.assertTrue(exception.getMessage().contains("Username already in use"));
    }

    private UserDto generateUserDto() {
        return UserDto.builder()
                .username("teste")
                .password("testing")
                .build();
    }
}