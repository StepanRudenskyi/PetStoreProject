package org.example.petstore.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderStatusUpdateRequest {
    private String status;
}
