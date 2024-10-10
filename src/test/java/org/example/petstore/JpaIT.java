package org.example.petstore;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.petstore.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "/reset.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class JpaIT {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TestDataUtil testDataUtil;

    @BeforeEach
    public void setUp() {
        testDataUtil.createTestData();
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

        // retrieve and check orders for the acc
        List<Order> orders = account.getOrders();
        assertNotNull(orders);
        assertFalse(orders.isEmpty());

        Order order = orders.get(0);
        assertNotNull(order);

        assertEquals(BigDecimal.valueOf(25.44), order.getTotalAmount());
        assertEquals("CREDIT_CARD", order.getPaymentMethod().toString());
        assertEquals("COMPLETED", order.getStatus().toString());

        // get orderLines in order
        List<OrderLine> orderLines = order.getOrderLineList();
        assertNotNull(orderLines);
        assertFalse(orderLines.isEmpty());

        // check the first orderLine
        OrderLine orderLine1 = orderLines.get(0);
        assertNotNull(orderLine1);
        assertEquals(1, orderLine1.getQuantity());
        assertEquals("Milk", orderLine1.getProduct().getName());

        Product milkProduct = orderLine1.getProduct();
        assertNotNull(milkProduct);
        assertEquals("Milk", milkProduct.getName());
        assertEquals("a carton of fresh milk", milkProduct.getDescription());
        assertEquals(BigDecimal.valueOf(2.99), milkProduct.getRetailPrice());

        // check inventories for milk
        List<Inventory> inventories = milkProduct.getInventories();
        assertNotNull(inventories);
        assertFalse(inventories.isEmpty());

        Inventory inventory = inventories.get(0);
        assertNotNull(inventory);
        assertEquals(100, inventory.getQuantity());
        assertEquals(BigDecimal.valueOf(2.50), inventory.getWholeSalePrice());

        // check category for milk
        ProductCategory category = milkProduct.getCategory();
        assertNotNull(category);
        assertEquals("Dairy", category.getCategoryName());
        assertEquals("Fridge", category.getStorageCondition());
        assertEquals("None", category.getSaleLimitation());

        // find the "milk" dep
        Department department1 = em.find(Department.class, 1);
        assertEquals("Diary department", department1.getName());

        // check products in the department "diary"
        List<Product> productsInDepartment1 = department1.getProducts();
        assertNotNull(productsInDepartment1);
        assertFalse(productsInDepartment1.isEmpty());

        Product productInDepartment1 = productsInDepartment1.get(0);
        assertNotNull(productInDepartment1);
        assertEquals("Milk", productInDepartment1.getName());

        List<Department> departmentsOfProduct = productInDepartment1.getDepartments();
        assertNotNull(departmentsOfProduct);
        assertFalse(departmentsOfProduct.isEmpty());

        Department departmentOfProduct = departmentsOfProduct.get(0);
        assertNotNull(departmentOfProduct);
        assertEquals("Diary department", departmentOfProduct.getName());

        // find the "tea and coffee" dep
        Department department2 = em.find(Department.class, 2);
        assertEquals("Tea and Coffee", department2.getName());

        // check products in the department "tea and coffee"
        List<Product> productsInDepartment2 = department2.getProducts();
        assertNotNull(productsInDepartment2);
        assertFalse(productsInDepartment2.isEmpty());

        // check for coffee in "tea and coffee" department
        Product coffeeProduct = productsInDepartment2.stream()
                .filter(p -> p.getName().equals("Coffee"))
                .findFirst()
                .orElse(null);

        assertNotNull(coffeeProduct);
        assertEquals("Coffee", coffeeProduct.getName());
        assertEquals("Roasted coffee beans", coffeeProduct.getDescription());
        assertEquals(BigDecimal.valueOf(5.99), coffeeProduct.getRetailPrice());

        // check inventories for Coffee product
        List<Inventory> coffeeInventories = coffeeProduct.getInventories();
        assertNotNull(coffeeInventories);
        assertFalse(coffeeInventories.isEmpty());

        Inventory coffeeInventory = coffeeInventories.get(0);
        assertNotNull(coffeeInventory);
        assertEquals(50, coffeeInventory.getQuantity());
        assertEquals(BigDecimal.valueOf(5.50), coffeeInventory.getWholeSalePrice());

        // check for Tea product in "Tea and Coffee" department
        Product teaProduct = productsInDepartment2.stream()
                .filter(p -> p.getName().equals("Tea"))
                .findFirst()
                .orElse(null);

        assertNotNull(teaProduct);
        assertEquals("Tea", teaProduct.getName());
        assertEquals("High-quality tea leaves", teaProduct.getDescription());
        assertEquals(BigDecimal.valueOf(3.49), teaProduct.getRetailPrice());

        // check inventories for Tea product
        List<Inventory> teaInventories = teaProduct.getInventories();
        assertNotNull(teaInventories);
        assertFalse(teaInventories.isEmpty());

        Inventory teaInventory = teaInventories.get(0);
        assertNotNull(teaInventory);
        assertEquals(100, teaInventory.getQuantity());
        assertEquals(BigDecimal.valueOf(3.00), teaInventory.getWholeSalePrice());
    }

}
