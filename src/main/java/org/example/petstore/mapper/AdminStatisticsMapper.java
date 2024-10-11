package org.example.petstore.mapper;

import org.example.petstore.dto.StatisticsDto;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminStatisticsMapper {
    StatisticsDto toDto(Long totalUsers, Long totalOrders, BigDecimal totalRevenue,
                        BigDecimal averageOrderValue, Long totalProductsSold,
                        List<StatisticsDto.ProductSalesDto> mostPopularProducts);

    default List<StatisticsDto.ProductSalesDto> mapMostPopularProducts(List<Object[]> mostPopularProductsList) {
        List<StatisticsDto.ProductSalesDto> productSalesDtos = new ArrayList<>();
        for (Object[] result : mostPopularProductsList) {
            String productName = (String) result[0];
            Long salesCount = (Long) result[1];
            productSalesDtos.add(new StatisticsDto.ProductSalesDto(productName, salesCount));
        }
        return productSalesDtos;
    }
}
