package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.dto.UserDto;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

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

        UserDto userTest = UserDto.builder()
                .username("teste")
                .password("testing")
                .build();
        UserEntity user = new UserEntity();
        user.setPassword(userTest.getPassword());
        user.setUsername(userTest.getUsername());

        //Prepare testing
        Mockito.when(passwordEncoder.encode(userTest.getPassword())).thenReturn(userTest.getPassword());
        Mockito.when(modelMapper.map(userTest, UserEntity.class)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserEntity response = userService.signUp(userTest);
        Assertions.assertThat(response.getPassword()).isEqualTo(userTest.getPassword());
        Assertions.assertThat(response.getUsername()).isEqualTo(userTest.getUsername());
    }
}