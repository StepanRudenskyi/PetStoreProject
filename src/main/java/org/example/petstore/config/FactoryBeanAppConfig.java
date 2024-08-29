package org.example.petstore.config;

import org.example.petstore.context.OrderProcessingContextFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryBeanAppConfig {
    @Bean
    public OrderProcessingContextFactoryBean orderProcessingContextFactoryBean() {
        return new OrderProcessingContextFactoryBean();
    }
}
