package br.com.pedro.usermicroservice.services;

import lombok.AllArgsConstructor;
import br.com.pedro.usermicroservice.model.UserEntity;
import br.com.pedro.usermicroservice.model.UserSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.pedro.usermicroservice.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSecurityService implements UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return new UserSecurity(user.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
