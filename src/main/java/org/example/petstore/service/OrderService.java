package org.example.petstore.service;

import org.example.petstore.dto.ReceiptDto;
import org.example.petstore.model.Order;

public interface OrderService {
    void processOrder(int orderId);
    ReceiptDto getReceipt(int accountId, int orderId);
    Order getOrderById(int orderId);
}
