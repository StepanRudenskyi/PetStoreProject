package org.example.petstore.mapper.cart;

import org.example.petstore.dto.cart.CartProductDto;
import org.example.petstore.dto.cart.CartViewDto;
import org.example.petstore.model.Cart;
import org.example.petstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CartViewMapper {
    CartViewDto toDto(User user);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(expression = "java(cart.calculateTotalPrice())", target = "totalPrice")
    CartProductDto toCartProductDto(Cart cart);

    @Mapping(source = "cartItems", target = "cartItems")
    @Mapping(source = "totalPrice", target = "totalPrice")
    CartViewDto toCartViewDto(List<Cart> cartItems, BigDecimal totalPrice);
}
