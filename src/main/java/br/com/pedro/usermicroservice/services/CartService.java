package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.model.Cart;
import br.com.pedro.usermicroservice.model.UserSecurity;
import br.com.pedro.usermicroservice.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartService {

    private CartRepository cartRepository;
    private UserSecurity userSecurity;

    public Cart cartAdd(Cart cart) {
        cart.setUsername(userSecurity.getUsername());
        return cartRepository.save(cart);
    }
}
