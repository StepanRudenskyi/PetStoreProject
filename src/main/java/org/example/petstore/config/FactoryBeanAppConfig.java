package org.example.petstore.config;

import org.example.petstore.service.order.OrderProcessingServiceFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryBeanAppConfig {
    @Bean
    public OrderProcessingServiceFactoryBean orderProcessingContextFactoryBean() {
        return new OrderProcessingServiceFactoryBean();
    }
}
