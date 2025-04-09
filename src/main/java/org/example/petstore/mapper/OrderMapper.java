package org.example.petstore.mapper;

import org.example.petstore.dto.order.OrderDto;
import org.example.petstore.dto.order.ReceiptDto;
import org.example.petstore.model.Order;
import org.example.petstore.model.OrderLine;
import org.example.petstore.service.order.OrderProcessingService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customer.user.username", target = "accountUsername")
    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "orderDate", target = "orderDate")
    @Mapping(source = "paymentMethod", target = "paymentMethod")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "isDiscountApplied", expression = "java(orderProcessingService.isDiscountApplied())")
    @Mapping(target = "validationMessage", expression = "java(orderProcessingService.getValidationMessage())")
    @Mapping(source = "totalAmount", target = "totalPrice")
    @Mapping(target = "orderLines", expression = "java(mapOrderLineList(order.getOrderLineList()))")
    OrderDto toDto(Order order, @Context OrderProcessingService orderProcessingService);

    default List<ReceiptDto.OrderLineDto> mapOrderLineList(List<OrderLine> orderLines) {
        if (orderLines == null) {
            return null;
        }
        return orderLines.stream()
                .map(ReceiptMapper::toOrderLineDto)
                .collect(Collectors.toList());
    }

}