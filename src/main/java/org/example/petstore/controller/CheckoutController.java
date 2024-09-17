package org.example.petstore.controller;

import org.example.petstore.enums.PaymentMethod;
import org.example.petstore.model.Cart;
import org.example.petstore.model.Order;
import org.example.petstore.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    private final Cart cart;

    @Autowired
    public CheckoutController(CheckoutService checkoutService, Cart cart) {
        this.checkoutService = checkoutService;
        this.cart = cart;
    }

    @GetMapping
    public String showCheckoutPage(Model model) {
        if (cart.getProductQuantityMap().isEmpty()) {
            model.addAttribute("errorMessage", "Your cart is empty.");
            return "cart";
        }
        model.addAttribute("cart", cart);
        return "checkout";
    }

    @PostMapping("/process")
    public String processCheckout(@RequestParam("paymentMethod") String paymentMethod, Model model) {
        try {
            PaymentMethod selectedPaymentMethod = PaymentMethod.valueOf(paymentMethod.toUpperCase());
            Order order = checkoutService.processCheckout(cart, selectedPaymentMethod);
            model.addAttribute("receipt", order);
            return "redirect:/receipt?accountId=" + order.getCustomer().getId() + "&orderId=" + order.getOrderId();
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Checkout failed: " + e.getMessage());
            return "checkout";
        }
    }
}
