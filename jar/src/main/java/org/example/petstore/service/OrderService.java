package org.example.petstore.service;

import org.example.petstore.dto.ReceiptDto;

public interface OrderService {
    void processOrder(int orderId);
    ReceiptDto getReceipt(int accountId, int orderId);
}
