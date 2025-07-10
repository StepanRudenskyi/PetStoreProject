package org.example.petstore.service.order;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.order.AdminOrderDto;
import org.example.petstore.dto.order.ReceiptDto;
import org.example.petstore.dto.stats.StatDto;
import org.example.petstore.enums.OrderStatus;
import org.example.petstore.mapper.AdminOrderMapper;
import org.example.petstore.model.Order;
import org.example.petstore.repository.OrderRepository;
import org.example.petstore.utils.StatUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AdminOrderMapper orderMapper;
    private final OrderProcessingService orderProcessingService;
    private final StatUtils statUtils;

    /**
     * Processes an order by applying any necessary discount logic.
     *
     * @param orderId the ID of the order to be processed
     * @throws NoResultException if the order is not found
     */
    public void processOrder(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            orderProcessingService.applyDiscountLogic(order);
        } else {
            throw new NoResultException("Order with ID: " + orderId + " not found");
        }
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId the ID of the order
     * @return the {@link Order} object
     * @throws NoResultException if the order is not found
     */
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoResultException("Order with ID: " + orderId + " not found"));
    }

    public void updateOrderStatus(Long orderId, String newStatus) {
        Optional<OrderStatus> optionalStatus = OrderStatus.fromString(newStatus);
        if (optionalStatus.isPresent()) {
            Order order = getOrderById(orderId);
            order.setStatus(optionalStatus.get());
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Invalid order status: " + newStatus);
        }
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Retrieves a list of order history for administrative purposes.
     *
     * @return a list of {@link AdminOrderDto} representing order history
     */
    public Page<AdminOrderDto> getOrdersHistory(String status, Pageable pageable) {
        Page<Order> orderPage;

        if (status != null) {
            Optional<OrderStatus> optionalStatus = OrderStatus.fromString(status);
            if (optionalStatus.isPresent()) {
                orderPage = orderRepository.findByStatus(optionalStatus.get(), pageable);
            } else {
                throw new IllegalArgumentException("Invalid order status: " + status);
            }
        } else {
            orderPage = orderRepository.findAll(pageable);
        }
        return orderPage.map(orderMapper::toDto);
    }

    public StatDto getOrderStats() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        StatUtils.DateRange todayRange = statUtils.getDayRange(today);
        StatUtils.DateRange yesterdayRange = statUtils.getDayRange(yesterday);

        long todayCount = orderRepository.countByOrderDateBetween(todayRange.start(), todayRange.end());
        long yesterdayCount = orderRepository.countByOrderDateBetween(yesterdayRange.start(), yesterdayRange.end());

        int changePercent = statUtils.calculatePercentageChange(todayCount, yesterdayCount);

        return new StatDto("Orders Today", (int) todayCount, changePercent, "percent", null);
    }


    public StatDto getPendingOrdersStats() {
        LocalDate today = LocalDate.now();

        LocalDateTime todayStart = statUtils.getStartOfDay(today);
        LocalDateTime todayEnd = statUtils.getEndOfDayExclusive(today);

        long pendingToday = orderRepository.countByStatusAndOrderDateBefore(OrderStatus.PROCESSING, todayEnd);
        long pendingYesterday = orderRepository.countByStatusAndOrderDateBefore(OrderStatus.PROCESSING, todayStart);

        int changePercent = statUtils.calculatePercentageChange(pendingToday, pendingYesterday);

        return new StatDto("Pending Orders", (int) pendingToday, changePercent, "percent", null);
    }


    /**
     * represents weekly revenue
     * */
    public StatDto getRevenueStats() {
        // calc revenue this week: monday -> today
        // calc revenue last week: monday -> same day of week
        // compare + calc persent change

        LocalDate today = LocalDate.now();
        StatUtils.ComparisonRange range = statUtils.getSameWeekdayRevenueRange(today);

        BigDecimal revenueThisWeek = orderRepository.sumRevenueByStatusAndDateRange(
                OrderStatus.COMPLETED, range.thisWeek().start(), range.thisWeek().end()
        );

        BigDecimal revenueLastWeek = orderRepository.sumRevenueByStatusAndDateRange(
                OrderStatus.COMPLETED, range.lastWeek().start(), range.lastWeek().end()
        );

        int changePercent = statUtils.calculatePercentageChange(revenueThisWeek, revenueLastWeek);

        return new StatDto("Revenue", revenueThisWeek.intValue(), changePercent, "percent", "USD");
    }
}
