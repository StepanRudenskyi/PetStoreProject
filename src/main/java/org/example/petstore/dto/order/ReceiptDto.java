package org.example.petstore.dto.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ReceiptDto represents the data transfer object for an order receipt.
 */
@Data
public class ReceiptDto {
    private String accountFirstName;
    private String accountLastName;
    private Long orderId;
    private Date orderDate;
    private String paymentMethod;
    private String status;
    private List<OrderLineDto> orderLines;
    private BigDecimal totalAmount;

    /**
     * OrderLineDto is a nested DTO representing individual product details within an order.
     */
    @Data
    public static class OrderLineDto {
        private String productName;
        private int quantity;
        private BigDecimal price;
    }

}
