package org.example.petstore.controller;

import org.example.petstore.dto.CheckoutDto;
import org.example.petstore.enums.PaymentMethod;
import org.example.petstore.model.Order;
import org.example.petstore.model.User;
import org.example.petstore.service.order.CheckoutService;
import org.example.petstore.service.user.UserService;
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
    private final UserService userService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService, UserService userService) {
        this.checkoutService = checkoutService;
        this.userService = userService;
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
            User currentUser = userService.getCurrentUser();
            CheckoutDto checkoutData = checkoutService.prepareCheckoutPage(currentUser);

            // Add cart and total price to the model
            model.addAttribute("cartItems", checkoutData.getCartItems());
            model.addAttribute("totalPrice", checkoutData.getTotalPrice());

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
            Order order = checkoutService.processCheckout(selectedPaymentMethod);
            model.addAttribute("receipt", order);
            return "redirect:/receipt/" + order.getOrderId();
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Checkout failed: " + e.getMessage());
            return "cart/checkout";
        }
    }
}
