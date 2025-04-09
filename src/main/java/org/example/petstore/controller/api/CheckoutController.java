package org.example.petstore.controller.api;

import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.order.CheckoutDto;
import org.example.petstore.dto.order.OrderDto;
import org.example.petstore.enums.PaymentMethod;
import org.example.petstore.service.order.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * CheckoutController handles the checkout process, including validation of the cart,
 * selection of payment methods, and order processing.
 * It ensures proper flow from cart validation to the creation of an order receipt.
 */
@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    /**
     * Displays the checkout page, validating the cart before proceeding.
     * If validation fails, redirects the user back to the cart page with an error message.
     *
     * @return the CheckoutDto json if the cart is valid, or a BAD_REQUEST if validation fails
     */
    @GetMapping
    public ResponseEntity<?> showCheckoutPage() {
        CheckoutDto checkoutData = checkoutService.prepareCheckoutPage();
        return ResponseEntity.ok(checkoutData);

    }

    /**
     * Processes the checkout based on the selected payment method, creating an order.
     * If the checkout fails, an error message is displayed on the checkout page.
     *
     * @param paymentMethod the selected payment method as a string
     * @return OrderDTO json if successful, or an error message if failed
     */
    @PostMapping("/process")
    public ResponseEntity<?> processCheckout(@RequestParam("paymentMethod") String paymentMethod) {
        PaymentMethod selectedPaymentMethod = PaymentMethod.valueOf(paymentMethod.toUpperCase());
        OrderDto orderDto = checkoutService.processCheckout(selectedPaymentMethod);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }
}
