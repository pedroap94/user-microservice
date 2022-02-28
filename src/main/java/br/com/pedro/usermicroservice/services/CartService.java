package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.exception.CreateCartException;
import br.com.pedro.usermicroservice.model.Cart;
import br.com.pedro.usermicroservice.model.UserEntity;
import br.com.pedro.usermicroservice.repository.CartRepository;
import br.com.pedro.usermicroservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Cart cartAdd(Cart cart) throws Exception {
        UserService userService = new UserService();
        UserEntity user = userService.userToCart();
        cart.setId(user.getId());
        return cartRepository.save(cart);
    }

}
