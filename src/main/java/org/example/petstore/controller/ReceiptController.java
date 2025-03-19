package org.example.petstore.controller;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.ReceiptDto;
import org.example.petstore.model.Order;
import org.example.petstore.service.order.OrderProcessingService;
import org.example.petstore.service.order.OrderService;
import org.example.petstore.service.user.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ReceiptController {

    private final OrderService orderService;
    private final OrderProcessingService orderProcessingService;
    private final UserValidator userValidator;


    @GetMapping("/receipt/{orderId}")
    public String getSecureReceipt(@PathVariable("orderId") Integer orderId, Model model){
        // retrieve the currently authenticated user
        if (!userValidator.canAccessOrder(orderId)) {
            return "error/403";
        }

        try {
            ReceiptDto receipt = orderService.getReceipt(orderId);
            model.addAttribute("receipt", receipt);
            return "common/receipt";
        } catch (NoResultException e) {
            return "error/404";
        }

    }

    @GetMapping("/processOrder/{orderId}")
    public String processOrder(@PathVariable("orderId") Integer orderId, Model model) {
        try {
            orderService.processOrder(orderId);
            Order order = orderService.getOrderById(orderId);

            model.addAttribute("order", order);
            model.addAttribute("discountApplied", orderProcessingService.isDiscountApplied());
            model.addAttribute("validationMessage", orderProcessingService.getValidationMessage());

            return "order/processedOrder";
        } catch (NoResultException e) {
            return "error/404";
        } catch (Exception e) {
            model.addAttribute("message", "An error occurred while processing the order");
            return "error/error";
        }
    }

}
