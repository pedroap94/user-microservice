package br.com.pedro.usermicroservice.controllers;

import br.com.pedro.usermicroservice.dto.UserDto;
import br.com.pedro.usermicroservice.model.UserEntity;
import br.com.pedro.usermicroservice.services.JWTAuthService;
import br.com.pedro.usermicroservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private JWTAuthService jwtAuthService;

    @GetMapping("test")
    public ResponseEntity<String> testSecurity() {
        return new ResponseEntity<>("Work!", HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<Void> signUp(@RequestBody UserDto userDto) {
        userService.signUp(userDto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(jwtAuthService.getJWT(userDto), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<UserEntity> findById(@RequestParam Long id){
        return new ResponseEntity<>(userService.findById(id), HttpStatus.ACCEPTED);
    }
}
