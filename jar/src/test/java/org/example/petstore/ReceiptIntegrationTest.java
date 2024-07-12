package org.example.petstore;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.example.petstore.model.Account;
import org.example.petstore.model.Order;
import org.example.petstore.model.OrderLine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest(classes = {PetstoreTestConfig.class})
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

    ///@BeforeEach
    public void setUp() {
    	/// you should not pass EntityManager anywhere but get it when you need it and where you need it.
    	//EntityManager is context specific and *container* managed. Container knows when to create a new instance and when to use existsing  
        //testDataUtil.createTestData(em);
    	testDataUtil.createTestData();
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
    @Sql(scripts = {"/import-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testGenerateReceipt() {
    	
    	//required if you use @Autowired 
    	//em.clear();
    	
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>  find Account");
        Account account = em.find(Account.class, 1);
        assertNotNull(account);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> getOrders");
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

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> getOrderLines");
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
