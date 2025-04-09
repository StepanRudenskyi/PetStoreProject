package org.example.petstore.service.order;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.order.AdminOrderDto;
import org.example.petstore.dto.order.ReceiptDto;
import org.example.petstore.enums.OrderStatus;
import org.example.petstore.exception.OrderAccessDeniedException;
import org.example.petstore.mapper.AdminOrderMapper;
import org.example.petstore.mapper.ReceiptMapper;
import org.example.petstore.model.Order;
import org.example.petstore.repository.OrderRepository;
import org.example.petstore.service.user.UserValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AdminOrderMapper orderMapper;
    private final OrderProcessingService orderProcessingService;
    private final UserValidator userValidator;

    /**
     * Processes an order by applying any necessary discount logic.
     *
     * @param orderId the ID of the order to be processed
     * @throws NoResultException if the order is not found
     */
    public void processOrder(Long orderId) {
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
     * @param orderId the ID of the order
     * @return a {@link ReceiptDto} containing order receipt details
     * @throws NoResultException if the order is not found
     */
    public ReceiptDto getReceipt(Long orderId) {
        Order order = orderRepository.findReceipt(orderId)
                .orElseThrow(() -> new NoResultException("Order with ID: " + orderId + " not found"));

        // update status to completed
        updateOrderStatus(orderId, OrderStatus.COMPLETED);

        if (!userValidator.canAccessOrder(orderId)) {
            throw new OrderAccessDeniedException("You can access your receipts only");
        }

        return ReceiptMapper.toDto(order);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId the ID of the order
     * @return the {@link Order} object
     * @throws NoResultException if the order is not found
     */
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoResultException("Order with ID: " + orderId + " not found"));
    }

    public void updateOrderStatus(Long orderId, OrderStatus status) {
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
    public Page<AdminOrderDto> getOrdersHistory(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return orderPage.map(orderMapper::toDto);
    }

}
