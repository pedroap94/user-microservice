package br.com.pedro.usermicroservice.services;

import br.com.pedro.usermicroservice.dto.CartDto;
import br.com.pedro.usermicroservice.exception.CartException;
import br.com.pedro.usermicroservice.exception.CartNotFoundException;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    public CartService(CartRepository cartRepository, @Lazy UserService userService, ModelMapper modelMapper, EntityManager entityManager) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
    }

    public void cartAdd(CartDto cartDto) {
        try {
            Cart cart = modelMapper.map(cartDto, Cart.class);
            UserEntity user = userAuthenticated();
            cart.setUsuarioId(user);
            cart.setDate(LocalDate.now());
            cartRepository.save(cart);
        } catch (Exception e){
            throw new CartException(e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void cartDelete(Long idItem) {
        try {
            UserEntity user = userAuthenticated();
            Query query = entityManager.createQuery("delete Cart c where (c.usuarioId = :userId and c.idItem = :idItem)");
            query.setParameter("userId", user);
            query.setParameter("idItem", idItem);
            int rowsAffected = query.executeUpdate();
            if (rowsAffected == 0) {
                throw new CartNotFoundException("");
            }
        } catch (CartNotFoundException e) {
            throw new CartNotFoundException("Cart not found");
        } catch (Exception e) {
            throw new CartException(e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void cartUpdate(CartDto cartDto) {
        try {
            Query query = entityManager
                    .createQuery("update Cart c set c.quantity = :quantity where (c.usuarioId = :user and c.idItem = :idItem)");
            query.setParameter("quantity", cartDto.getQuantity());
            query.setParameter("user", userAuthenticated());
            query.setParameter("idItem", cartDto.getIdItem());
            int rowsAffected = query.executeUpdate();
            if (rowsAffected == 0) {
                throw new CartNotFoundException("");
            }
        } catch (CartNotFoundException e) {
            throw new CartNotFoundException("Item not found");
        } catch (Exception e) {
            throw new CartException(e.getMessage());
        }
    }

    private UserEntity userAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = ((UserSecurity) userDetails).getUser().getUsername();
        return userService.userToCart(username);
    }

}
