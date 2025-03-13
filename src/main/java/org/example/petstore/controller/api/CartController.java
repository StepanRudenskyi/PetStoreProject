package org.example.petstore.controller.api;

import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.CartAddResponseDto;
import org.example.petstore.dto.cart.CartViewDto;
import org.example.petstore.service.cart.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartAddResponseDto> addToCart(@RequestParam("productId") Long productId,
                                                        @RequestParam("quantity") int quantity) {
        CartAddResponseDto cartResponseDto = cartService.addProductToCart(productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponseDto);
    }


    @DeleteMapping("/remove/{productId}")
    public void removeFromCart(@PathVariable("productId") Long productId) {
        cartService.removeProductFromCart(productId);
    }

    @GetMapping
    public ResponseEntity<CartViewDto> viewCart() {
        CartViewDto cartView = cartService.getCartView();
        return ResponseEntity.ok(cartView);
    }
}
