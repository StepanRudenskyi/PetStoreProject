package org.example.petstore.service;

import org.example.petstore.service.OrderProcessingService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class OrderProcessingServiceFactoryBean implements FactoryBean<OrderProcessingService> {
    @Override
    public OrderProcessingService getObject() {
        return new OrderProcessingService();
    }

    @Override
    public Class<?> getObjectType() {
        return OrderProcessingService.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
