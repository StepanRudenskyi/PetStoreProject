package org.example.petstore.mapper;

import org.example.petstore.dto.stats.ProductSalesDto;
import org.example.petstore.dto.stats.StatisticsDto;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminStatisticsMapper {
    StatisticsDto toDto(Long totalUsers, Long totalOrders, BigDecimal totalRevenue,
                        BigDecimal averageOrderValue, Long totalProductsSold,
                        List<ProductSalesDto> mostPopularProducts);

}
