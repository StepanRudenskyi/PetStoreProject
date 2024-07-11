package org.example.petstore;

import jakarta.persistence.EntityManager;
import org.example.petstore.model.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;

@Component
public class TestDataUtil {


    @Transactional
    public void createTestData(EntityManager em) {

        ProductCategory category = new ProductCategory();
        category.setCategoryName("Dairy");
        category.setStorageCondition("Fridge");
        category.setSaleLimitation("None");

        Product product = new Product();
        product.setName("Milk");
        product.setDescription("a carton of fresh milk");
        product.setRetailPrice(2.99);
        product.setCategory(category);

        Department department = new Department();
        department.setName("Diary department");

        Account acc = new Account();
        acc.setFirstName("John");
        acc.setLastName("Doe");
        acc.setEmail("user@usa.net");

        Order order = new Order();
        order.setOrderDate(new Date());
        order.setTotalAmount(2.99);
        order.setPaymentMethod("Credit card");
        order.setStatus("Completed");
        order.setCustomer(acc);

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantity(1);
        orderLine.setProduct(product);
        orderLine.setOrder(order);

        Inventory inventory = new Inventory();
        inventory.setQuantity(100);
        inventory.setWholeSalePrice(2.50);
        inventory.setProduct(product);
        inventory.setInventoryDate(new Date());
        inventory.setBestBefore(new Date(System.currentTimeMillis() + 7L * 24 * 3600 * 1000)); // one week from now

        // set relationships
        order.setOrderLineList(Arrays.asList(orderLine));
        acc.setOrders(Arrays.asList(order));
        category.setProducts(Arrays.asList(product));
        product.setInventories(Arrays.asList(inventory));
        product.setDepartments(Arrays.asList(department));
        department.setProducts(Arrays.asList(product));


        em.persist(category);
        em.persist(product);
        em.persist(department);
        em.persist(inventory);
        em.persist(acc);
        em.persist(orderLine);
        em.persist(order);
    }
}
