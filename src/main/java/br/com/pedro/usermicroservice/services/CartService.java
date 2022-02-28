package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.exception.CreateCartException;
import br.com.pedro.usermicroservice.model.Cart;
import br.com.pedro.usermicroservice.model.UserEntity;
import br.com.pedro.usermicroservice.repository.CartRepository;
import br.com.pedro.usermicroservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CartService {

    private CartRepository cartRepository;
    private UserRepository userRepository;

    public Cart createCart(UserEntity user) {
        try {
            Cart cart = new Cart();
//            cart.setUserEntity(user);
            Cart save = cartRepository.save(cart);
            return save;
        } catch (Exception e) {
            throw new CreateCartException("Failed to create cart");
        }
    }

    public Cart cartAdd(Cart cart) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String s = authentication.getPrincipal().toString();
        List<String> list = new ArrayList<>();
        Matcher match = Pattern.compile("username=(.*?),").matcher(s);
        if (match.find()) {
            list.add(match.group(1));
        }
        Optional<UserEntity> userEntity = userRepository.findByUsername(list.get(0));
        if (userEntity.isPresent()) {
            cart.setUserEntity(userEntity.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
        return cartRepository.save(cart);
    }

}
