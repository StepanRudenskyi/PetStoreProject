package org.example.petstore.controller;

import org.example.petstore.model.Cart;
import org.example.petstore.model.User;
import org.example.petstore.service.cart.CartService;
import org.example.petstore.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity,
                            @RequestParam("categoryId") int categoryId,
                            RedirectAttributes redirectAttributes) {

        cartService.addProductToCart(productId, quantity);

        redirectAttributes.addFlashAttribute("successMessage", "Product added to cart successfully!");
        redirectAttributes.addFlashAttribute("addedProductId", productId);

        // return same page
        return "redirect:/products/categories/" + categoryId;
    }


    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable("productId") Long productId, Model model) {

        cartService.removeProductFromCart(productId);
        User currentUser = userService.getCurrentUser();
        List<Cart> cartItems = cartService.getCartItemsByUser(currentUser);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("successMessage", "Product removed form cart successfully");

        return "cart/cart";
    }

    @GetMapping
    public String viewCart(Model model) {
        User currentUser = userService.getCurrentUser();
        List<Cart> cartItems = cartService.getCartItemsByUser(currentUser);
        BigDecimal totalPrice = cartService.calculateTotalPrice(cartItems);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);

        return "cart/cart";
    }
}
