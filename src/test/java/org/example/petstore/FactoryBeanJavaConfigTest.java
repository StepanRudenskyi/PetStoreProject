package org.example.petstore;

import org.example.petstore.config.FactoryBeanAppConfig;
import org.example.petstore.context.OrderProcessingContext;
import org.example.petstore.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FactoryBeanAppConfig.class)
public class FactoryBeanJavaConfigTest {

    @Autowired
    private OrderProcessingContext orderProcessingContext;

    @Test
    public void testProcessingContext() {
        Order order = new Order();
        order.setTotalAmount(150.0);

        orderProcessingContext.applyDiscountLogic(order);

        assertTrue(orderProcessingContext.isDiscountApplied());
        assertEquals("Discount applied for orders over $100", orderProcessingContext.getValidationMessage());


        order.setTotalAmount(90.0);

        orderProcessingContext.applyDiscountLogic(order);
        assertFalse(orderProcessingContext.isDiscountApplied());
        assertEquals("No discount applied", orderProcessingContext.getValidationMessage());

    }
}
