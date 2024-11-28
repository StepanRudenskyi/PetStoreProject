package org.example.petstore.service.cart;

import org.example.petstore.model.Cart;
import org.example.petstore.model.User;
import org.example.petstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartValidator {

    @Autowired
    private CartRepository cartRepository;

    public boolean isCartEmpty(User user) {
        List<Cart> userCart = cartRepository.findByUser(user);
        return userCart.isEmpty();

    }
}
