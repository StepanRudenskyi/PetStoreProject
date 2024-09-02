package org.example.petstore.service;

import lombok.Getter;
import lombok.Setter;
import org.example.petstore.model.Order;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class OrderProcessingService {
    private boolean isDiscountApplied;
    private String validationMessage;

    public void applyDiscountLogic(Order order) {
        if (order.getTotalAmount() > 100) {
            isDiscountApplied = true;
            validationMessage = "Discount applied for orders over $100";
        } else {
            isDiscountApplied = false;
            validationMessage = "No discount applied";
        }
    }
}
