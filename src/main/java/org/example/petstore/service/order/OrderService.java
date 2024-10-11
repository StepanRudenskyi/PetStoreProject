package org.example.petstore.service.order;

import jakarta.persistence.NoResultException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.petstore.dto.AdminOrderDto;
import org.example.petstore.dto.ReceiptDto;
import org.example.petstore.enums.OrderStatus;
import org.example.petstore.mapper.AdminOrderMapper;
import org.example.petstore.mapper.OrderMapper;
import org.example.petstore.model.Order;
import org.example.petstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;

    @Autowired
    AdminOrderMapper orderMapper;

    @Getter
    private OrderProcessingService orderProcessingService;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderProcessingService orderProcessingService) {
        this.orderRepository = orderRepository;
        this.orderProcessingService = orderProcessingService;
    }

    /**
     * Processes an order by applying any necessary discount logic.
     *
     * @param orderId the ID of the order to be processed
     * @throws NoResultException if the order is not found
     */
    public void processOrder(int orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            orderProcessingService.applyDiscountLogic(order);
        } else {
            throw new NoResultException("Order with ID: " + orderId + " not found");
        }
    }

    /**
     * Retrieves the receipt details for an order and marks it as completed.
     *
     * @param accountId the account ID related to the order
     * @param orderId   the ID of the order
     * @return a {@link ReceiptDto} containing order receipt details
     * @throws NoResultException if the order is not found
     */
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

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId the ID of the order
     * @return the {@link Order} object
     * @throws NoResultException if the order is not found
     */
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

    /**
     * Retrieves a list of order history for administrative purposes.
     *
     * @return a list of {@link AdminOrderDto} representing order history
     */
    public List<AdminOrderDto> getOrdersHistory() {
        List<Order> allOrders = orderRepository.findAll();
        List<AdminOrderDto> resultList = new ArrayList<>();

        for (Order order : allOrders) {
            resultList.add(orderMapper.toDto(order));
        }
        return resultList;
    }

}
