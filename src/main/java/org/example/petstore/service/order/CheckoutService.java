package org.example.petstore.service.order;

import jakarta.persistence.NoResultException;
import org.example.petstore.enums.OrderStatus;
import org.example.petstore.enums.PaymentMethod;
import org.example.petstore.model.Cart;
import org.example.petstore.model.Order;
import org.example.petstore.model.OrderLine;
import org.example.petstore.model.Product;
import org.example.petstore.repository.AccountRepository;
import org.example.petstore.repository.OrderLineRepository;
import org.example.petstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    private final OrderService orderService;
    private final OrderLineRepository orderLineRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public CheckoutService(OrderService orderService, OrderLineRepository orderLineRepository, AccountRepository accountRepository, UserRepository userRepository) {
        this.orderService = orderService;
        this.orderLineRepository = orderLineRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Order processCheckout(Cart cart, PaymentMethod paymentMethod) {
        try {
            // create order based on cart
            Order order = buildOrder(cart, paymentMethod);
            Order savedOrder = orderService.saveOrder(order);

            // create order line
            List<OrderLine> orderLines = createOrderLines(cart, savedOrder);
            orderLineRepository.saveAll(orderLines);
            cart.clear();
            return savedOrder;
        } catch (Exception e) {
            throw new RuntimeException("Failed to process checkout", e);

        }
    }

    // create order based on cart
    private Order buildOrder(Cart cart, PaymentMethod paymentMethod) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setPaymentMethod(paymentMethod);
        order.setStatus(OrderStatus.PROCESSING);
        order.setTotalAmount(cart.getTotalPrice());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoResultException("User not found"));
        var customer = accountRepository.findByUser(user)
                .orElseThrow(() -> new NoResultException("Account not found"));

         order.setCustomer(customer);

        return order;
    }

    private List<OrderLine> createOrderLines(Cart cart, Order order) {
        return cart.getProductQuantityMap().entrySet().stream()
                .map(entry -> buildOrderLine(entry.getKey(), entry.getValue(), order))
                .collect(Collectors.toList());
    }

    private OrderLine buildOrderLine(Product product, Integer quantity, Order order) {
        OrderLine orderLine = new OrderLine();
        orderLine.setProduct(product);
        orderLine.setQuantity(quantity);
        orderLine.setOrder(order);

        return orderLine;
    }
}
