package org.example.petstore.service.order;

import jakarta.persistence.NoResultException;
import org.example.petstore.dto.CheckoutDto;
import org.example.petstore.enums.OrderStatus;
import org.example.petstore.enums.PaymentMethod;
import org.example.petstore.model.*;
import org.example.petstore.repository.AccountRepository;
import org.example.petstore.repository.OrderLineRepository;
import org.example.petstore.service.cart.CartService;
import org.example.petstore.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for processing checkout operations, including order creation,
 * order line creation, cart validation, and payment method handling.
 */
@Service
public class CheckoutService {

    private final OrderService orderService;
    private final OrderLineRepository orderLineRepository;
    private final AccountRepository accountRepository;
    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CheckoutService(OrderService orderService, OrderLineRepository orderLineRepository,
                           AccountRepository accountRepository, CartService cartService, UserService userService) {
        this.orderService = orderService;
        this.orderLineRepository = orderLineRepository;
        this.accountRepository = accountRepository;
        this.cartService = cartService;
        this.userService = userService;
    }

    /**
     * Processes the checkout by creating an order, saving it, creating order lines from the cart,
     * and clearing the cart once the order is complete.
     *
     * @param paymentMethod the payment method to be used for the order
     * @return the saved order
     * @throws RuntimeException if an error occurs during checkout processing
     */
    public Order processCheckout(PaymentMethod paymentMethod) {
        try {
            User currentUser = userService.getCurrentUser();
            List<Cart> cartItems = cartService.getCartItemsByUser(currentUser);

            if (cartItems.isEmpty()) {
                throw new IllegalStateException("Cart is empty. Cannot proceed with checkout.");

            }
            // create order based on cart
            Order order = buildOrder(paymentMethod, currentUser, cartItems);
            Order savedOrder = orderService.saveOrder(order);

            // create order line
            List<OrderLine> orderLines = createOrderLines(cartItems, savedOrder);
            orderLineRepository.saveAll(orderLines);

            cartService.clearCart();
            return savedOrder;
        } catch (Exception e) {
            throw new RuntimeException("Failed to process checkout", e);

        }
    }

    /**
     * Builds an order based on the user's cart and the selected payment method.
     *
     * @param paymentMethod the payment method for the order
     * @param currentUser the current user making the order
     * @param cartItems the items in the user's cart
     * @return the constructed order
     */    private Order buildOrder(PaymentMethod paymentMethod, User currentUser, List<Cart> cartItems) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setPaymentMethod(paymentMethod);
        order.setStatus(OrderStatus.PROCESSING);

        order.setTotalAmount(cartService.calculateTotalPrice(cartItems));

        var customer = accountRepository.findByUser(currentUser)
                .orElseThrow(() -> new NoResultException("Account not found"));

        order.setCustomer(customer);

        return order;
    }

    /**
     * Creates a list of order lines from the items in the cart.
     *
     * @param cartItems the items in the cart
     * @param order the order to associate the order lines with
     * @return a list of created order lines
     */
    private List<OrderLine> createOrderLines(List<Cart> cartItems, Order order) {
        return cartItems.stream()
                .map(item -> buildOrderLine(item.getProduct(), item.getQuantity(), order))
                .collect(Collectors.toList());
    }

    /**
     * Builds an order line for a specific product and quantity.
     *
     * @param product the product being ordered
     * @param quantity the quantity of the product ordered
     * @param order the order to associate this order line with
     * @return the constructed order line
     */
    private OrderLine buildOrderLine(Product product, Integer quantity, Order order) {
        OrderLine orderLine = new OrderLine();
        orderLine.setProduct(product);
        orderLine.setQuantity(quantity);
        orderLine.setOrder(order);

        return orderLine;
    }

    /**
     * Prepares the checkout page by validating the cart, calculating the total price,
     * and returning the information in a CheckoutDto.
     *
     * @param currentUser the current user viewing the checkout page
     * @return the CheckoutDto containing cart items and total price
     */
    public CheckoutDto prepareCheckoutPage(User currentUser) {
        List<Cart> cartItems = cartService.getCartItemsByUser(currentUser);

        // check if cart is empty
        cartService.validateCartForCheckout(currentUser);
        // Calculate total price
        BigDecimal totalPrice = cartService.calculateTotalPrice(cartItems);

        return new CheckoutDto(cartItems, totalPrice);
    }
}
