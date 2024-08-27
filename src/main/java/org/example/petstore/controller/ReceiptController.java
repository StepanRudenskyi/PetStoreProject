package org.example.petstore.controller;

import jakarta.persistence.NoResultException;
import org.example.petstore.dto.ReceiptDto;
import org.example.petstore.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReceiptController {

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/receipt")
    public String getReceipt(@RequestParam("accountId") int accountId,
                             @RequestParam("orderId") int orderId, Model model) {
        try {
            ReceiptDto receipt = orderService.getReceipt(accountId, orderId);
            model.addAttribute("receipt", receipt);
            return "receipt";
        } catch (NoResultException e) {
            return "404";
        }
    }
}
