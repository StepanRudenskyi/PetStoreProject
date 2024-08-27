package org.example.petstore.service;

import jakarta.persistence.NoResultException;
import lombok.NoArgsConstructor;
import org.example.petstore.dto.ReceiptDto;
import org.example.petstore.mapper.OrderMapper;
import org.example.petstore.model.Order;
import org.example.petstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void processOrder(int orderId) {
        System.out.println("Processing order with id: " + orderId + " ...");
    }

    @Override
    public ReceiptDto getReceipt(int accountId, int orderId) {
        Optional<Order> orderOptional = orderRepository.findReceipt(accountId, orderId);

        if (orderOptional.isPresent()) {
            Order existingOrder = orderOptional.get();
            return OrderMapper.toDto(existingOrder);
        } else {
            throw new NoResultException("Order with ID: " + orderId + " not found");
        }
    }
}
