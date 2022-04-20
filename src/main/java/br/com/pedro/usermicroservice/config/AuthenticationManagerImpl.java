package br.com.pedro.usermicroservice.config;

import br.com.pedro.usermicroservice.exception.UserNotVerifyException;
import br.com.pedro.usermicroservice.model.UserEntity;
import br.com.pedro.usermicroservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@AllArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager {
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getPrincipal() instanceof String && authentication.getCredentials() instanceof String) {
            UserEntity user = userRepository.findByUsername((String) authentication.getPrincipal()).orElseThrow();
            String password = passwordRecover(authentication, user);
            if (password.equals(user.getPassword())) {
                return authentication;
            }
        }
        throw new UserNotVerifyException("Could not verify user");
    }

    private String passwordRecover(Authentication authentication, UserEntity user) {
        try {
            String password = authentication.getCredentials().toString() + user.getSalt();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] byteMessage = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder passwordReturn = new StringBuilder();
            for (byte transformByteInMessage : byteMessage) {
                passwordReturn.append(transformByteInMessage);
            }
            return passwordReturn.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new UserNotVerifyException("Fail to recovery login password");
        }
    }
}
