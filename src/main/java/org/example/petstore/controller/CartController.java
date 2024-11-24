package org.example.petstore.controller;

import org.example.petstore.model.Cart;
import org.example.petstore.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    Cart cart;
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") int productId,
                            @RequestParam("quantity") int quantity,
                            @RequestParam("categoryId") int categoryId,
                            RedirectAttributes redirectAttributes) {
        cartService.addProductToCart(cart, productId, quantity);

        redirectAttributes.addFlashAttribute("successMessage", "Product added to cart successfully!");
        redirectAttributes.addFlashAttribute("addedProductId", productId);

        // return same page
        return "redirect:/products/categories/" + categoryId;
    }


    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable("productId") Integer productId, Model model) {
        cartService.removeProductFromCart(cart, productId);

        model.addAttribute("categories", cartService.getAllCategoriesWithProducts());
        model.addAttribute("successMessage", "Product removed form cart successfully");
        model.addAttribute("cart", cart);

        return "cart/cart";
    }

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cart", cart);
        return "cart/cart";
    }
}
