package org.example.petstore.controller;

import org.example.petstore.enums.PaymentMethod;
import org.example.petstore.model.Cart;
import org.example.petstore.model.Order;
import org.example.petstore.service.cart.CartService;
import org.example.petstore.service.order.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * CheckoutController handles the checkout process, including validation of the cart,
 * selection of payment methods, and order processing.
 * It ensures proper flow from cart validation to the creation of an order receipt.
 */
@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final CartService cartService;
    private final Cart cart;

    @Autowired
    public CheckoutController(CheckoutService checkoutService, CartService cartService, Cart cart) {
        this.checkoutService = checkoutService;
        this.cartService = cartService;
        this.cart = cart;
    }

    /**
     * Displays the checkout page, validating the cart before proceeding.
     * If validation fails, redirects the user back to the cart page with an error message.
     *
     * @param model the Model object to pass attributes to the view
     * @param redirectAttributes the RedirectAttributes used for passing error messages during redirection
     * @return the checkout page view name if the cart is valid, or a redirect to the cart page if validation fails
     */
    @GetMapping
    public String showCheckoutPage(Model model, RedirectAttributes redirectAttributes) {
        try {
            cartService.validateCartForCheckout(cart);
            model.addAttribute("cart", cart);
            return "cart/checkout";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/cart";
        }
    }

    /**
     * Processes the checkout based on the selected payment method, creating an order.
     * If the checkout is successful, the user is redirected to the receipt page.
     * If the checkout fails, an error message is displayed on the checkout page.
     *
     * @param paymentMethod the selected payment method as a string
     * @param model the Model object used to pass attributes to the view
     * @return the redirect URL to the receipt page if successful, or the checkout page with an error message if failed
     */
    @PostMapping("/process")
    public String processCheckout(@RequestParam("paymentMethod") String paymentMethod, Model model) {
        try {
            PaymentMethod selectedPaymentMethod = PaymentMethod.valueOf(paymentMethod.toUpperCase());
            Order order = checkoutService.processCheckout(cart, selectedPaymentMethod);
            model.addAttribute("receipt", order);
            return "redirect:/receipt?accountId=" + order.getCustomer().getId() + "&orderId=" + order.getOrderId();
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Checkout failed: " + e.getMessage());
            return "cart/checkout";
        }
    }
}
