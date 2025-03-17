package org.example.petstore.mapper;

import org.example.petstore.dto.OrderDto;
import org.example.petstore.dto.ReceiptDto;
import org.example.petstore.model.Order;
import org.example.petstore.model.OrderLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customer.user.username", target = "accountUsername")
    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "orderDate", target = "orderDate")
    @Mapping(source = "paymentMethod", target = "paymentMethod")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "totalAmount", target = "totalPrice")
    @Mapping(target = "orderLines", expression = "java(mapOrderLineList(order.getOrderLineList()))")
    OrderDto toDto(Order order);

    default List<ReceiptDto.OrderLineDto> mapOrderLineList(List<OrderLine> orderLines) {
        if (orderLines == null) {
            return null;
        }
        return orderLines.stream()
                .map(ReceiptMapper::toOrderLineDto)
                .collect(Collectors.toList());
    }

}