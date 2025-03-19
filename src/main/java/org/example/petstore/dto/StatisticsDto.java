package org.example.petstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * StatisticsDto is a data transfer object representing various statistics for the admin dashboard.
 */
@Data
public class StatisticsDto {
    private Long totalUsers;
    private Long totalOrders;
    private BigDecimal totalRevenue;
    private BigDecimal averageOrderValue;
    private Long totalProductsSold;
    private List<ProductSalesDto> mostPopularProducts;

    /**
     * ProductSalesDto represents data for a specific product and its sales count.
     */
}
