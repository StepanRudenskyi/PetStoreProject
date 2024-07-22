package org.example.petstore.service;

public class OrderServiceImpl implements OrderService {

    @Override
    public void processOrder(int orderId) {
        System.out.println("Processing order with id: " + orderId + " ...");
    }
}
