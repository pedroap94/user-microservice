package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.dto.UserDto;
import br.com.pedro.usermicroservice.model.UserEntity;
import br.com.pedro.usermicroservice.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup(){
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


        //Prepare testing
        Mockito.when(passwordEncoder.encode(userTest.getPassword())).thenReturn(userTest.getPassword());
        Mockito.when(modelMapper.map(userTest, UserEntity.class)).thenReturn();
        userService.signUp(userTest);
        Assertions.assertThat(userRepository.findByUsername(userTest.getUsername())).isEqualTo(userTest);


    }
}