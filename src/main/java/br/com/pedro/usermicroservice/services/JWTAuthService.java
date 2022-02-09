package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.dto.UserDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JWTAuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private static Long TIME_TO_EXPIRE_TOKEN = 5400000L;
    private static String SECRET = "secret";

    public String getJWT(UserDto userDto) {
        try {
            UserDetails user = userDetailsService.loadUserByUsername(userDto.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDto.getUsername(),
                    userDto.getPassword()
            ));
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("User not found");
        }
        return JWT.create().withSubject(userDto.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TIME_TO_EXPIRE_TOKEN))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    public UsernamePasswordAuthenticationToken verify(String token) {
        String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();
        assert username != null : null;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
