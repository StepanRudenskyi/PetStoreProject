package org.example.petstore;

import org.example.petstore.service.OrderService;
import org.example.petstore.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = {PetstoreTestConfig.class})
@Sql(scripts = {"/import-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/reset.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class TimingDynamicInvocationHandlerTest {

    private OrderService orderServiceProxy;

    @BeforeEach
    public void setUp() {
        OrderService orderServiceImpl = new OrderServiceImpl();
        orderServiceProxy = (OrderService) Proxy.newProxyInstance(
                OrderService.class.getClassLoader(),
                new Class[]{OrderService.class},
                new TimingDynamicInvocationHandler(orderServiceImpl));
    }
    @Test
    public void testOrderProcessing() {
        assertDoesNotThrow(() -> orderServiceProxy.processOrder(1));
    }
}
