package org.example.petstore.controller.api;

import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.ReceiptDto;
import org.example.petstore.service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReceiptController {

    private final OrderService orderService;

    @GetMapping("/receipt/{orderId}")
    public ResponseEntity<ReceiptDto> getSecureReceipt(@PathVariable("orderId") Long orderId){
        ReceiptDto receipt = orderService.getReceipt(orderId);
        return ResponseEntity.ok(receipt);
    }

}
