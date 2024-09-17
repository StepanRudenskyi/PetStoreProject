package org.example.petstore.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
public class Cart {
    private Map<Product, Integer> productQuantityMap = new HashMap<>();
    private Double totalPrice;

    public void addItem(Product product, int quantity) {

        if (productQuantityMap.containsKey(product)) {
            int currentQuantity = productQuantityMap.get(product);
            productQuantityMap.put(product, currentQuantity + quantity);
        } else {
            productQuantityMap.put(product, quantity);
        }
        calculateTotalPrice();
    }


    public void removeItem(Product product) {
        productQuantityMap.remove(product);
        calculateTotalPrice();
    }

    //TODO: add clear cart

    private void calculateTotalPrice() {
        totalPrice = productQuantityMap.entrySet().stream()
                .mapToDouble(item -> item.getKey().getRetailPrice() * item.getValue())
                .sum();
    }

}
