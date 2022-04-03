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
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private JWTAuthService jwtAuthService;

    @PostMapping()
    public ResponseEntity<Void> signUp(@RequestBody UserDto userDto) {
        userService.signUp(userDto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(jwtAuthService.getJWT(userDto), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<UserEntity> findById(@RequestParam String username) {
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody UserDto userDto) {
        userService.updateUser(userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
