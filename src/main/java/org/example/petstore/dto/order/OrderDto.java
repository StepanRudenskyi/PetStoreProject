package org.example.petstore.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDto {
    private String accountUsername;
    private Long orderId;
    private Date orderDate;
    private String paymentMethod;
    private String status;
    private boolean isDiscountApplied;
    private String validationMessage;
    private BigDecimal totalPrice;
    private List<ReceiptDto.OrderLineDto> orderLines;
}
