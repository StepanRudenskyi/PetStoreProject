package org.example.petstore;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.example.petstore.enums.OrderStatus;
import org.example.petstore.enums.PaymentMethod;
import org.example.petstore.model.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;

@Component
public class TestDataUtil {


	@PersistenceContext
	EntityManager em;
	
    @Transactional
    //public void createTestData(EntityManager em) {
    public void createTestData() {

        // create categories
        ProductCategory dairy = new ProductCategory();
        dairy.setCategoryName("Dairy");
        dairy.setStorageCondition("Fridge");
        dairy.setSaleLimitation("None");

        ProductCategory beverages = new ProductCategory();
        beverages.setCategoryName("Beverages");
        beverages.setStorageCondition("Dry");
        beverages.setSaleLimitation("None");

        // create products
        Product product1 = new Product();
        product1.setName("Milk");
        product1.setDescription("a carton of fresh milk");
        product1.setRetailPrice(2.99);
        product1.setCategory(dairy);

        Product product2 = new Product();
        product2.setName("Coffee");
        product2.setDescription("Roasted coffee beans");
        product2.setRetailPrice(5.99);
        product2.setCategory(beverages);

        Product product3 = new Product();
        product3.setName("Tea");
        product3.setDescription("High-quality tea leaves");
        product3.setRetailPrice(3.49);
        product3.setCategory(beverages);

        // create departments
        Department department1 = new Department();
        department1.setName("Diary department");

        Department department2 = new Department();
        department2.setName("Tea and Coffee");

        // create account
        Account acc = new Account();
        acc.setFirstName("John");
        acc.setLastName("Doe");
        acc.setEmail("user@usa.net");

        // create orderLines
        OrderLine orderLine1 = new OrderLine();
        orderLine1.setQuantity(1);
        orderLine1.setProduct(product1);

        OrderLine orderLine2 = new OrderLine();
        orderLine2.setQuantity(2);
        orderLine2.setProduct(product2);

        OrderLine orderLine3 = new OrderLine();
        orderLine3.setQuantity(3);
        orderLine3.setProduct(product3);

        // calculate total price
        Double totalAmount = (orderLine1.getQuantity() * orderLine1.getProduct().getRetailPrice()) +
                (orderLine2.getQuantity() * orderLine2.getProduct().getRetailPrice()) +
                (orderLine3.getQuantity() * orderLine3.getProduct().getRetailPrice());

        // create order
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setTotalAmount(totalAmount);
        order.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        order.setStatus(OrderStatus.COMPLETED);
        order.setCustomer(acc);

        orderLine1.setOrder(order);
        orderLine2.setOrder(order);
        orderLine3.setOrder(order);


        // create inventories
        Inventory milkInventory = new Inventory();
        milkInventory.setQuantity(100);
        milkInventory.setWholeSalePrice(2.50);
        milkInventory.setProduct(product1);
        milkInventory.setInventoryDate(new Date());
        milkInventory.setBestBefore(new Date(System.currentTimeMillis() + 7L * 24 * 3600 * 1000)); // one week from now

        Inventory teaInventory = new Inventory();
        teaInventory.setQuantity(100);
        teaInventory.setWholeSalePrice(3.00);
        teaInventory.setProduct(product2);
        teaInventory.setInventoryDate(new Date());
        teaInventory.setBestBefore(new Date(System.currentTimeMillis() + 365L * 24 * 3600 * 1000));

        Inventory coffeeInventory = new Inventory();
        coffeeInventory.setQuantity(50);
        coffeeInventory.setWholeSalePrice(5.50);
        coffeeInventory.setProduct(product3);
        coffeeInventory.setInventoryDate(new Date());
        coffeeInventory.setBestBefore(new Date(System.currentTimeMillis() + 182L * 24 * 3600 * 1000));

        // set relationships
        order.setOrderLineList(Arrays.asList(orderLine1, orderLine2, orderLine3));
        acc.setOrders(Arrays.asList(order));
        dairy.setProducts(Arrays.asList(product1));
        beverages.setProducts(Arrays.asList(product2, product3));
        product1.setInventories(Arrays.asList(milkInventory));
        product1.setDepartments(Arrays.asList(department1));
        department1.setProducts(Arrays.asList(product1));
        department2.setProducts(Arrays.asList(product2, product3));
        product2.setInventories(Arrays.asList(coffeeInventory));
        product2.setDepartments(Arrays.asList(department2));
        product3.setInventories(Arrays.asList(teaInventory));
        product3.setDepartments(Arrays.asList(department2));

        em.persist(dairy);
        em.persist(beverages);
        em.persist(product1);
        em.persist(product2);
        em.persist(product3);
        em.persist(department1);
        em.persist(department2);
        em.persist(acc);
        em.persist(order);
        em.persist(orderLine1);
        em.persist(orderLine2);
        em.persist(orderLine3);
        em.persist(milkInventory);
        em.persist(teaInventory);
        em.persist(coffeeInventory);
    }
}
