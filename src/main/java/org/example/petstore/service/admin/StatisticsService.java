package org.example.petstore.service.admin;

import org.example.petstore.dto.StatisticsDto;
import org.example.petstore.mapper.AdminStatisticsMapper;
import org.example.petstore.repository.OrderLineRepository;
import org.example.petstore.repository.OrderRepository;
import org.example.petstore.repository.UserRepository;
import org.example.petstore.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    OrderLineRepository orderLineRepository;

    @Autowired
    AdminStatisticsMapper statisticsMapper;

    @Autowired
    ProductService productService;

    /**
     * Gathers various statistics including total users, total orders, total revenue,
     * average order value, total products sold, and the most popular products.
     *
     * @return a {@link StatisticsDto} containing the gathered statistics.
     */
    public StatisticsDto getStatistics() {
        Long totalUsers = userRepository.count();
        Long totalOrders = orderRepository.count();
        BigDecimal totalRevenue = orderRepository.calculateTotalRevenue();

        // Calculate the average order value.
        BigDecimal averageOrderValue = totalOrders > 0
                ? totalRevenue.divide(BigDecimal.valueOf(totalOrders), RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        Long totalProductsSold = orderLineRepository.countTotalProductsSold();

        // Retrieve the list of most popular products from the repository.
        List<Object[]> mostPopularProductsList = orderLineRepository.findMostPopularProducts();
        // Map the raw data into a list of ProductSalesDto objects.
        List<StatisticsDto.ProductSalesDto> mostPopularProducts = statisticsMapper.mapMostPopularProducts(mostPopularProductsList);

        return statisticsMapper.toDto(totalUsers, totalOrders, totalRevenue, averageOrderValue, totalProductsSold, mostPopularProducts);
    }

}
