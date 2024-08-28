package org.example.petstore.context;

import lombok.Getter;
import lombok.Setter;
import org.example.petstore.model.Order;

@Getter
@Setter
public class OrderProcessingContext {
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
