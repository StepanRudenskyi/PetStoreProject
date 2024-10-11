package org.example.petstore.mapper;

import org.example.petstore.dto.AdminOrderDto;
import org.example.petstore.dto.ReceiptDto;
import org.example.petstore.enums.OrderStatus;
import org.example.petstore.model.Account;
import org.example.petstore.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdminOrderMapper {

    public AdminOrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }

        AdminOrderDto adminOrderDto = new AdminOrderDto();

        if (order.getOrderId() != null) {
            adminOrderDto.setId(order.getOrderId().longValue());
        }
        adminOrderDto.setCustomerName(orderCustomerFirstName(order));
        if (order.getStatus() != null) {
            adminOrderDto.setStatus(order.getStatus().name());
        }
        adminOrderDto.setTotalAmount(order.getTotalAmount());

        List<ReceiptDto.OrderLineDto> orderLines = order.getOrderLineList().stream()
                .map(OrderMapper::toOrderLineDto)
                .collect(Collectors.toList());
        adminOrderDto.setOrderLines(orderLines);

        return adminOrderDto;
    }

    public Order toEntity(AdminOrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }

        Order order = new Order();

        order.setTotalAmount(orderDto.getTotalAmount());
        if (orderDto.getStatus() != null) {
            order.setStatus(Enum.valueOf(OrderStatus.class, orderDto.getStatus()));
        }

        return order;
    }

    private String orderCustomerFirstName(Order order) {
        if (order == null) {
            return null;
        }
        Account customer = order.getCustomer();
        if (customer == null) {
            return null;
        }
        String firstName = customer.getFirstName();
        if (firstName == null) {
            return null;
        }
        return firstName;
    }
}
