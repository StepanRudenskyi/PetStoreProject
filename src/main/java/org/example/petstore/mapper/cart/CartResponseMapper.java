package org.example.petstore.mapper.cart;

import org.example.petstore.dto.cart.CartAddResponseDto;
import org.example.petstore.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartResponseMapper {
    @Mapping(source = "cart.id", target = "cartId")
    @Mapping(constant = "Product added to cart successfully!", target = "message")
    @Mapping(source = "cart.product.id", target = "productId")
    @Mapping(source = "cart.product.name", target = "productName")
    @Mapping(source = "cart.user.id", target = "userId")
    @Mapping(source = "cart.quantity", target = "quantity")
    @Mapping(expression = "java(cart.calculateTotalPrice())", target = "totalPrice")
    @Mapping(source = "cart.additionTime", target = "additionTime")
    CartAddResponseDto toDto(Cart cart);
}
