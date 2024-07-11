package org.example.petstore;

import jakarta.persistence.EntityManager;
import org.example.petstore.model.Account;
import org.example.petstore.model.Order;
import org.example.petstore.model.OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {PetstoreTestConfig.class})
public class ReceiptIntegrationTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private TestDataUtil testDataUtil;

    @BeforeEach
    public void setUp() {
        testDataUtil.createTestData(em);
    }

    @Test
    @Transactional
    public void testGenerateReceipt() {
        Account account = em.find(Account.class, 1);
        assertNotNull(account);

        List<Order> orders = account.getOrders();
        assertNotNull(orders);
        assertFalse(orders.isEmpty());

        StringBuilder receipt = new StringBuilder();
        receipt.append("Receipt for ").append(account.getFirstName()).append(" ").append(account.getLastName()).append(":\n");

        for (Order order : orders) {
            receipt.append("Order ID: ").append(order.getOrderId()).append("\n");
            receipt.append("Order Date: ").append(order.getOrderDate()).append("\n");
            receipt.append("Payment method: ").append(order.getPaymentMethod()).append("\n");
            receipt.append("Status: ").append(order.getStatus()).append("\n");

            List<OrderLine> orderLines = order.getOrderLineList();
            assertNotNull(orderLines);
            assertFalse(orderLines.isEmpty());

            receipt.append("Items: \n");

            for (OrderLine orderLine : orderLines) {
                receipt.append(" - ").append(orderLine.getProduct().getName()).append(": ");
                receipt.append(orderLine.getQuantity()).append(" x ");
                receipt.append("$").append(orderLine.getProduct().getRetailPrice()).append("\n");
            }
            receipt.append("Total amount: $").append(order.getTotalAmount()).append("\n");

            receipt.append("\n");
        }

        System.out.println(receipt);

        assertTrue(receipt.toString().contains("John Doe"));
        assertTrue(receipt.toString().contains("Milk"));
        assertTrue(receipt.toString().contains("Total amount: $25.44"));

    }
}
