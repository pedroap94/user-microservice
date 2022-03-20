package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.exception.CreateCartException;
import br.com.pedro.usermicroservice.model.Cart;
import br.com.pedro.usermicroservice.model.UserEntity;
import br.com.pedro.usermicroservice.model.UserSecurity;
import br.com.pedro.usermicroservice.repository.CartRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;

    public CartService(CartRepository cartRepository, @Lazy UserService userService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    public Cart createCart(UserEntity user) {
        try {
            Cart cart = new Cart();
            return cartRepository.save(cart);
        } catch (Exception e) {
            throw new CreateCartException("Failed to create cart");
        }
    }

    public Cart cartAdd(Cart cart) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = ((UserSecurity) userDetails).getUser().getUsername();
        UserEntity user = userService.userToCart(username);
        cart.setId(user.getId());
        return cartRepository.save(cart);
    }

}
