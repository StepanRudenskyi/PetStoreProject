package org.example.petstore.service.order;

import lombok.Getter;
import lombok.Setter;
import org.example.petstore.model.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Getter
@Setter
public class OrderProcessingService {
    private boolean isDiscountApplied;
    private String validationMessage;

    public void applyDiscountLogic(Order order) {
        if (order.getTotalAmount().compareTo(BigDecimal.valueOf(100)) > 0) {
            isDiscountApplied = true;
            validationMessage = "Discount applied for orders over $100";
        } else {
            isDiscountApplied = false;
            validationMessage = "No discount applied";
        }
    }
}