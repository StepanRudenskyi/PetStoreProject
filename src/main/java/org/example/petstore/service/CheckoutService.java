package org.example.petstore.service;

import jakarta.persistence.NoResultException;
import org.example.petstore.enums.OrderStatus;
import org.example.petstore.enums.PaymentMethod;
import org.example.petstore.model.*;
import org.example.petstore.repository.AccountRepository;
import org.example.petstore.repository.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    private final OrderService orderService;
    private final OrderLineRepository orderLineRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public CheckoutService(OrderService orderService, OrderLineRepository orderLineRepository, AccountRepository accountRepository) {
        this.orderService = orderService;
        this.orderLineRepository = orderLineRepository;
        this.accountRepository = accountRepository;
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

        // temp default account
        Optional<Account> customer = accountRepository.findById(1);
        order.setCustomer(customer.orElseThrow(() -> new NoResultException("Default account not found")));

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
