package org.example.petstore.controller.api;

import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.OrderStatusUpdateRequest;
import org.example.petstore.dto.account.AdminUserDto;
import org.example.petstore.dto.order.AdminOrderDto;
import org.example.petstore.dto.stats.SummaryStatDto;
import org.example.petstore.service.admin.UserManagementService;
import org.example.petstore.service.order.OrderService;
import org.example.petstore.service.statistics.StatisticsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * AdminController is a RESTful controller that exposes endpoints for managing administrative tasks
 * such as viewing statistics, managing users, and handling orders. It allows the admin to perform
 * operations like viewing users, promoting/demoting admins, and viewing order histories.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final StatisticsService statisticsService;
    private final UserManagementService userManagementService;
    private final OrderService orderService;

    /**
     * Retrieves a paginated list of all users in the system. The list is displayed with
     * pagination to ensure efficient loading of user data.
     *
     * @param pageable the pagination details (page size, sorting, etc.).
     * @return a ResponseEntity containing a Page of AdminUserDto objects representing the users.
     */
    @GetMapping("/users")
    public ResponseEntity<Page<AdminUserDto>> viewUsers(@PageableDefault(size = 5) Pageable pageable) {
        Page<AdminUserDto> users = userManagementService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    /**
     * Promotes a user to an admin role.
     *
     * @param userId the ID of the user to promote.
     * @return a ResponseEntity with a message confirming the promotion of the user.
     */
    @PostMapping("/add-admin/{userId}")
    public ResponseEntity<Map<String, String>> addAdmin(@PathVariable Long userId) {
        userManagementService.addAdmin(userId);
        return ResponseEntity.ok(Map.of("message", "User with ID: " + userId + " has been given admin role"));
    }

    /**
     * Removes the admin role from a user, effectively demoting them.
     *
     * @param userId the ID of the user to demote.
     * @return a ResponseEntity with a message confirming the removal of the admin role.
     */
    @DeleteMapping("/remove-admin/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long userId) {
        userManagementService.removeAdmin(userId);
        return ResponseEntity.ok(Map.of("message", "User with ID: " + userId + " is no longer an admin"));
    }

    /**
     * Retrieves a paginated list of orders for management by the admin. This endpoint is used
     * to manage order histories and track order status.
     *
     * @param pageable the pagination details (page size, sorting, etc.).
     * @return a ResponseEntity containing a Page of AdminOrderDto objects representing orders.
     */
    @GetMapping("/orders")
    public ResponseEntity<Page<AdminOrderDto>> getOrders(@RequestParam(required = false) String status,
                                                         @PageableDefault() Pageable pageable) {
        Page<AdminOrderDto> orderHistories = orderService.getOrdersHistory(status, pageable);
        return ResponseEntity.ok(orderHistories);
    }

    @PatchMapping("/orders/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long orderId,
                                                  @RequestBody OrderStatusUpdateRequest request
    ) {
        orderService.updateOrderStatus(orderId, request.getStatus());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<SummaryStatDto> getQuickStats() {
        return ResponseEntity.ok(statisticsService.buildStatistics());
    }
}
