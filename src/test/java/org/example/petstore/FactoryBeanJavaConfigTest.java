package org.example.petstore;

import org.example.petstore.config.FactoryBeanAppConfig;
import org.example.petstore.service.OrderProcessingService;
import org.example.petstore.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FactoryBeanAppConfig.class)
public class FactoryBeanJavaConfigTest {

    @Autowired
    private OrderProcessingService orderProcessingService;

    @Test
    public void testProcessingContext() {
        Order order = new Order();
        order.setTotalAmount(150.0);

        orderProcessingService.applyDiscountLogic(order);

        assertTrue(orderProcessingService.isDiscountApplied());
        assertEquals("Discount applied for orders over $100", orderProcessingService.getValidationMessage());


        order.setTotalAmount(90.0);

        orderProcessingService.applyDiscountLogic(order);
        assertFalse(orderProcessingService.isDiscountApplied());
        assertEquals("No discount applied", orderProcessingService.getValidationMessage());

    }
}
