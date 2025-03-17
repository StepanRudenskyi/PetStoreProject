package org.example.petstore.mapper;

import org.example.petstore.dto.ReceiptDto;
import org.example.petstore.dto.ReceiptDto.OrderLineDto;
import org.example.petstore.model.Account;
import org.example.petstore.model.Order;
import org.example.petstore.model.OrderLine;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

public class ReceiptMapper {

    public static ReceiptDto toDto(Order order) {
        if (order == null) {
            return null;
        }

        ReceiptDto dto = new ReceiptDto();
        Account account = order.getCustomer();

        dto.setAccountFirstName(account.getFirstName());
        dto.setAccountLastName(account.getLastName());
        dto.setOrderId(order.getOrderId());
        dto.setPaymentMethod(order.getPaymentMethod().name());
        dto.setStatus(order.getStatus().name());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());

        List<ReceiptDto.OrderLineDto> orderLines = order.getOrderLineList().stream()
                .map(ReceiptMapper::toOrderLineDto)
                .collect(Collectors.toList());

        dto.setOrderLines(orderLines);

        return dto;
    }

    @Named("mapToOrderLineDto")
    public static OrderLineDto toOrderLineDto(OrderLine orderLine) {
        if (orderLine == null) {
            return null;
        }

        OrderLineDto dto = new OrderLineDto();

        dto.setProductName(orderLine.getProduct().getName());
        dto.setPrice(orderLine.getProduct().getRetailPrice());
        dto.setQuantity(orderLine.getQuantity());

        return dto;
    }
}
