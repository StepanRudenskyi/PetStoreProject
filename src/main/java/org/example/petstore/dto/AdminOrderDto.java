package org.example.petstore.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * AdminOrderDto represents the data transfer object for an order to be used in the admin view.
 */
@Data
public class AdminOrderDto {
    private Long id;
    private String customerName;
    private String status;
    private BigDecimal totalAmount;
    private List<ReceiptDto.OrderLineDto> orderLines;
}
