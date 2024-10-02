package org.example.petstore.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ReceiptDto {
    private String accountFirstName;
    private String accountLastName;
    private int orderId;
    private Date orderDate;
    private String paymentMethod;
    private String status;
    private List<OrderLineDto> orderLines;
    private double totalAmount;

    @Data
    public static class OrderLineDto {
        private String productName;
        private int quantity;
        private BigDecimal price;
    }

}
