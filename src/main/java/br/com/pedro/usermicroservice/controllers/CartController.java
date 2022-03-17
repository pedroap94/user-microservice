package br.com.pedro.usermicroservice.controllers;

import br.com.pedro.usermicroservice.model.Cart;
import br.com.pedro.usermicroservice.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/cart")
@AllArgsConstructor
public class CartController {

    private CartService cartService;

    @PostMapping("add")
    public ResponseEntity<Cart> cartAdd(@RequestBody Cart cart) {
        return new ResponseEntity<>(cartService.cartAdd(cart), HttpStatus.CREATED);
    }
}
