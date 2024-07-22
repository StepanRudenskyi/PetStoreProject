package org.example.petstore;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.petstore.model.Account;
import org.example.petstore.model.Order;
import org.example.petstore.model.OrderLine;
import org.example.petstore.service.OrderService;
import org.example.petstore.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {PetstoreTestConfig.class})
@Sql(scripts = {"/import-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/reset.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class ReceiptIntegrationTest {

    //@Autowired - DO NOT USE @Autowired for PersistenceContext
    //you may need specify different properties in different context, for different components you may need persistence contexts of a different type with different properties
    //also you will get the same instance of EntityManager for each and every test which makes your tests unstable.
    //you're not the first :) see https://stackoverflow.com/questions/31335211/autowired-vs-persistencecontext-for-entitymanager-bean
    @PersistenceContext
    private EntityManager em;

    //in general it's not a best practice to populate test data "in code" (unless it mocks)
    //for real data import there are more several better options
    //hibernate executes "import.sql" by default  it it is available (useful when you run your app first time)
    //or tests spring has better options
    //see also: https://jpa-buddy.com/guides/writing-integration-tests-in-spring-boot-app-with-jpa/
    ///@Autowired  
    private TestDataUtil testDataUtil;
    private OrderService orderServiceProxy;

    @BeforeEach
    public void setUp() {
    	/// you should not pass EntityManager anywhere but get it when you need it and where you need it.
    	//EntityManager is context specific and *container* managed. Container knows when to create a new instance and when to use existsing  
        //testDataUtil.createTestData(em);
        OrderService orderServiceImpl = new OrderServiceImpl();
        orderServiceProxy = (OrderService) Proxy.newProxyInstance(
                OrderService.class.getClassLoader(),
                new Class[]{OrderService.class},
                new TimingDynamicInvocationHandler(orderServiceImpl));

//    	testDataUtil.createTestData();
    }

    @Test
    public void testOrderProcessing() {
        orderServiceProxy.processOrder(1);
    }
    
    @Test
    public void testNoOp() {
    	
    }

    /**
     *  relations *ToOne - by default are EAGER
     *  relations *ToMany - by default are LAZY
     *  
     *  see difference if you change FetchType 
     * 
     */

    
    @Test
    @Transactional
    public void testGenerateOrderHistory() {
        generateOrderHistory(2);
    }

    @Test
    @Transactional
    public void testGenerateReceiptHQL() {
        generateReceiptHQL(1, 1);
    }

    @Test
    @Transactional
    public void testGenerateOrderHistoryWithinDateRange() {
        LocalDateTime startDate = LocalDateTime.of(2024, 7, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 7, 31, 23, 59);
        generateOrderHistoryWithinDateRange(startDate, endDate);
    }

    @Transactional
    public void generateOrderHistory(int accountId) {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>  find Account");
        Account account = em.find(Account.class, accountId);
        assertNotNull(account);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> getOrders");
        List<Order> orders = account.getOrders();
        assertNotNull(orders);
        assertFalse(orders.isEmpty());


        StringBuilder receipt = new StringBuilder();
        receipt.append("----- Receipts History -----").append("\n\n");

        for (Order order : orders) {
            receipt.append(generateReceipt(order)).append("\n");
        }
        System.out.println(receipt);

        /*assertTrue(receipt.toString().contains("John Doe"));
        assertTrue(receipt.toString().contains("Milk"));
        assertTrue(receipt.toString().contains("Total amount: $25.44"));*/
    }

    @Transactional
    public void generateReceiptHQL(int accountId, int orderId) {
        TypedQuery<Order> query = em.createQuery(
                "SELECT o FROM Order o " +
                        "JOIN FETCH o.customer a " +
                        "JOIN FETCH o.orderLineList ol " +
                        "JOIN FETCH ol.product " +
                        "WHERE o.customer.id = :accountId AND o.id = :orderId", Order.class
        );
        query.setParameter("accountId", accountId);
        query.setParameter("orderId", orderId);

        Order order = query.getSingleResult();
        assertNotNull(order);

        String receipt = generateReceipt(order);
        System.out.println(receipt);
    }


    @Transactional
    public void generateOrderHistoryWithinDateRange(LocalDateTime startDate, LocalDateTime endDate) {

        TypedQuery<Order> query = em.createQuery(
                "SELECT o FROM Order o " +
                        "JOIN FETCH o.customer a " +
                        "JOIN FETCH o.orderLineList oll " +
                        "JOIN FETCH oll.product p " +
                        "WHERE o.orderDate " +
                        "BETWEEN :startDate AND :endDate", Order.class
        );

        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        List<Order> orders = query.getResultList();


        assertNotNull(orders);
        assertFalse(orders.isEmpty());

        StringBuilder receipt = new StringBuilder();
        receipt.append("\n----- Receipts History for Date Range -----").append("\n\n");

        for (Order order : orders) {
            receipt.append(generateReceipt(order)).append("\n");
        }

        System.out.println(receipt);
    }


    private String generateReceipt(Order order) {
        Account account = order.getCustomer();
        StringBuilder receipt = new StringBuilder();

        receipt.append("Receipt for ").append(account.getFirstName()).append(" ").append(account.getLastName()).append(":\n");
        receipt.append("Order ID: ").append(order.getOrderId()).append("\n");
        receipt.append("Order Date: ").append(order.getOrderDate()).append("\n");
        receipt.append("Payment method: ").append(order.getPaymentMethod()).append("\n");
        receipt.append("Status: ").append(order.getStatus()).append("\n");

        List<OrderLine> orderLines = order.getOrderLineList();
        assertNotNull(orderLines);
        assertFalse(orderLines.isEmpty());

        for (OrderLine orderLine : orderLines) {
            receipt.append(" - ").append(orderLine.getProduct().getName()).append(": ");
            receipt.append(orderLine.getQuantity()).append(" x ");
            receipt.append("$").append(orderLine.getProduct().getRetailPrice()).append("\n");
        }
        receipt.append("Total amount: $").append(order.getTotalAmount()).append("\n");

        return receipt.toString();
    }
}
