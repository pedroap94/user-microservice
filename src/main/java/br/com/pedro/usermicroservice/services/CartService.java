package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.dto.CartDto;
import br.com.pedro.usermicroservice.model.Cart;
import br.com.pedro.usermicroservice.model.UserEntity;
import br.com.pedro.usermicroservice.model.UserSecurity;
import br.com.pedro.usermicroservice.repository.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private ModelMapper modelMapper;

    public CartService(CartRepository cartRepository, @Lazy UserService userService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    public Cart cartAdd(CartDto cartDto) {
        Cart cart = modelMapper.map(cartDto, Cart.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = ((UserSecurity) userDetails).getUser().getUsername();
        UserEntity user = userService.userToCart(username);
        cart.setUsuarioId(user);
        cart.setDate(LocalDate.now());
        return cartRepository.save(cart);
    }

    public void cartDelete(CartDto cartDto){
    }

    public void cartUpdate(CartDto cartDto){
        
    }

}
