package org.example.petstore.controller;

import jakarta.persistence.NoResultException;
import org.example.petstore.dto.ReceiptDto;
import org.example.petstore.model.Order;
import org.example.petstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReceiptController {

    @Autowired
    private OrderService orderService;

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

    @GetMapping("/processOrder")
    public String processOrder(@RequestParam("orderId") int orderId, Model model) {
        try {
            orderService.processOrder(orderId);
            Order order = orderService.getOrderById(orderId);

            model.addAttribute("order", order);
            model.addAttribute("discountApplied", orderService.getOrderProcessingService().isDiscountApplied());
            model.addAttribute("validationMessage", orderService.getOrderProcessingService().getValidationMessage());

            return "processedOrder";
        } catch (NoResultException e) {
            return "404";
        } catch (Exception e) {
            model.addAttribute("message", "An error occurred while processing the order");
            return "error";
        }
    }

}
