package org.example.petstore;

import org.example.petstore.config.FactoryBeanAppConfig;
import org.example.petstore.service.order.OrderProcessingService;
import org.example.petstore.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FactoryBeanAppConfig.class)
public class FactoryBeanJavaConfigTest {

    @Autowired
    private OrderProcessingService orderProcessingService;

    @Test
    public void testProcessingContext() {
        Order order = new Order();
        order.setTotalAmount(BigDecimal.valueOf(150.0));

        orderProcessingService.applyDiscountLogic(order);

        assertTrue(orderProcessingService.isDiscountApplied());
        assertEquals("10% discount applied for orders over $100", orderProcessingService.getValidationMessage());


        order.setTotalAmount(BigDecimal.valueOf(90.0));

        orderProcessingService.applyDiscountLogic(order);
        assertFalse(orderProcessingService.isDiscountApplied());
        assertEquals("No discount applied", orderProcessingService.getValidationMessage());

    }
}
