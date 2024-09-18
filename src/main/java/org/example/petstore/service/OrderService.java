package org.example.petstore.service;

import jakarta.persistence.NoResultException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.petstore.dto.ReceiptDto;
import org.example.petstore.enums.OrderStatus;
import org.example.petstore.mapper.OrderMapper;
import org.example.petstore.model.Order;
import org.example.petstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    @Getter
    private OrderProcessingService orderProcessingService;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderProcessingService orderProcessingService) {
        this.orderRepository = orderRepository;
        this.orderProcessingService = orderProcessingService;
    }

    public void processOrder(int orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            orderProcessingService.applyDiscountLogic(order);
        } else {
            throw new NoResultException("Order with ID: " + orderId + " not found");
        }
    }

    public ReceiptDto getReceipt(int accountId, int orderId) {
        Optional<Order> orderOptional = orderRepository.findReceipt(accountId, orderId);

        if (orderOptional.isPresent()) {
            Order existingOrder = orderOptional.get();

            // update status to completed
            updateOrderStatus(orderId, OrderStatus.COMPLETED);

            return OrderMapper.toDto(existingOrder);
        } else {
            throw new NoResultException("Order with ID: " + orderId + " not found");
        }
    }

    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoResultException("Order with ID: " + orderId + " not found"));
    }

    public void updateOrderStatus(int orderId, OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        orderRepository.save(order);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

}
