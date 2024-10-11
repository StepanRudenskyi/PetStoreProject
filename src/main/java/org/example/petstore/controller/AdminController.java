package org.example.petstore.controller;

import org.example.petstore.dto.AdminOrderDto;
import org.example.petstore.dto.StatisticsDto;
import org.example.petstore.dto.AdminUserDto;
import org.example.petstore.model.Product;
import org.example.petstore.service.admin.StatisticsService;
import org.example.petstore.service.admin.UserManagementService;
import org.example.petstore.service.order.OrderService;
import org.example.petstore.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller for handling admin-related operations.
 * This controller handles requests related to user management, statistics, products, and orders for admins.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    UserManagementService userManagementService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    /**
     * Displays statistics for admin users.
     *
     * @param model the model to hold statistics data
     * @return the admin statistics page
     */
    @GetMapping("/statistics")
    public String showStatistics(Model model) {
        StatisticsDto statistics = statisticsService.getStatistics();
        model.addAttribute("stats", statistics);
        return "admin/statistics";
    }

    /**
     * Displays a list of all users for admin management.
     *
     * @param model the model to hold the list of users
     * @return the admin users page
     */
    @GetMapping("/users")
    public String viewUsers(Model model) {
        List<AdminUserDto> users = userManagementService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/adminUsers";
    }

    /**
     * Promotes a user to admin by their user ID.
     *
     * @param userId the ID of the user to promote
     * @param attributes used to add flash messages
     * @return a redirect to the admin users page
     */
    @PostMapping("/add-admin/{userId}")
    public String addAdmin(@PathVariable Long userId, RedirectAttributes attributes) {
        userManagementService.addAdmin(userId);
        attributes.addFlashAttribute("successMessage", "admin added successfully");
        return "redirect:/admin/users";
    }

    /**
     * Removes admin rights from a user by their user ID.
     *
     * @param userId the ID of the user to remove admin rights from
     * @param redirectAttributes used to add flash messages
     * @return a redirect to the admin users page
     */
    @PostMapping("/remove-admin/{userId}")
    public String deleteUser(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        userManagementService.removeAdmin(userId);
        redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
        return "redirect:/admin/users";
    }

    /**
     * Adds a new product to the store.
     *
     * @param product the product to add
     * @param redirectAttributes used to add flash messages
     * @return a redirect to the add product page
     */
    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        productService.addProduct(product);
        redirectAttributes.addFlashAttribute("successMessage", "Product added successfully.");
        return "redirect:/admin/add-product";
    }

    /**
     * Displays the order management page for admins.
     *
     * @param model the model to hold the order history
     * @return the admin order history page
     */
    @GetMapping("/orders")
    public String showManageOrdersPage(Model model) {
        List<AdminOrderDto> orderHistories = orderService.getOrdersHistory();
        model.addAttribute("orderHistory", orderHistories);
        return "admin/orderHistory";
    }
}
