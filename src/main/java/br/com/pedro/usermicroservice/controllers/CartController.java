package br.com.pedro.usermicroservice.controllers;

import br.com.pedro.usermicroservice.dto.CartDto;
import br.com.pedro.usermicroservice.model.Cart;
import br.com.pedro.usermicroservice.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("api/v1/cart")
@AllArgsConstructor
public class CartController {

    private CartService cartService;

    @PostMapping("add")
    public ResponseEntity<Cart> cartAdd(@RequestBody CartDto cartDto) {
        return new ResponseEntity<>(cartService.cartAdd(cartDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{idItem}")
    public ResponseEntity<Void> cartDelete(@PathVariable Long idItem){
        cartService.cartDelete(idItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
