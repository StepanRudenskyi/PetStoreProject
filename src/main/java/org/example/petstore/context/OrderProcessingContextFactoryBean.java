package org.example.petstore.context;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class OrderProcessingContextFactoryBean implements FactoryBean<OrderProcessingContext> {
    @Override
    public OrderProcessingContext getObject() {
        return new OrderProcessingContext();
    }

    @Override
    public Class<?> getObjectType() {
        return OrderProcessingContext.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
