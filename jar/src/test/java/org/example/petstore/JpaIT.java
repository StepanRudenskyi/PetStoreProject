package org.example.petstore;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.petstore.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {PetstoreTestConfig.class})
public class JpaIT {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TestDataUtil testDataUtil;

    @BeforeEach
    public void setUp() {
        testDataUtil.createTestData(em);
    }

    @Test
    public void testSchema() {
        //no-op

    }

    @Test
    @Transactional
    public void testDataCreationAndRead() {

        Account account = em.find(Account.class, 1);

        assertNotNull(account);
        assertEquals("John", account.getFirstName());
        assertEquals("Doe", account.getLastName());
        assertEquals("user@usa.net", account.getEmail());

        List<Order> orders = account.getOrders();
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        Order order = orders.get(0);

        assertNotNull(order);
        assertEquals(2.99, order.getTotalAmount());
        assertEquals("Credit card", order.getPaymentMethod());
        assertEquals("Completed", order.getStatus());

        List<OrderLine> orderLines = order.getOrderLineList();
        assertNotNull(orderLines);
        assertFalse(orderLines.isEmpty());
        OrderLine orderLine = orderLines.get(0);

        assertNotNull(orderLine);
        assertEquals(1, orderLine.getQuantity());
        assertEquals("Milk", orderLine.getProduct().getName());

        Product product = orderLine.getProduct();
        assertNotNull(product);
        assertEquals("Milk", product.getName());
        assertEquals("a carton of fresh milk", product.getDescription());
        assertEquals(2.99, product.getRetailPrice());

        List<Inventory> inventories = product.getInventories();
        assertNotNull(inventories);
        assertFalse(inventories.isEmpty());
        Inventory inventory = inventories.get(0);

        assertNotNull(inventory);
        assertEquals(100, inventory.getQuantity());
        assertEquals(2.50, inventory.getWholeSalePrice());

        ProductCategory category = product.getCategory();
        assertNotNull(category);
        assertEquals("Dairy", category.getCategoryName());
        assertEquals("Fridge", category.getStorageCondition());
        assertEquals("None", category.getSaleLimitation());

        Department department = em.find(Department.class, 1);
        assertEquals("Diary department", department.getName());

        List<Product> productsInDepartment = department.getProducts();
        assertNotNull(productsInDepartment);
        assertFalse(productsInDepartment.isEmpty());
        Product productInDepartment = productsInDepartment.get(0);

        assertNotNull(productInDepartment);
        assertEquals("Milk", productInDepartment.getName());

        List<Department> departmentsOfProduct = productInDepartment.getDepartments();
        assertNotNull(departmentsOfProduct);
        assertFalse(departmentsOfProduct.isEmpty());
        Department departmentOfProduct = departmentsOfProduct.get(0);

        assertNotNull(departmentOfProduct);
        assertEquals("Diary department", departmentOfProduct.getName());

    }

}
