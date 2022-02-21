package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.model.Cart;
import br.com.pedro.usermicroservice.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartService {

    private CartRepository cartRepository;

    public Cart cartAdd(Cart cart) {
        return cartRepository.save(cart);
    }
}
