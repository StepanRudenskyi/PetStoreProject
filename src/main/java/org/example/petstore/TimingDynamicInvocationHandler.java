package org.example.petstore;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class TimingDynamicInvocationHandler implements InvocationHandler {

    private static Logger LOGGER = Logger.getLogger(TimingDynamicInvocationHandler.class.getName());
    private final Object target;

    public TimingDynamicInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.nanoTime();
       try {
           return method.invoke(target, args);
       } finally {
           long elapsed = System.nanoTime() - start;
           LOGGER.info("Executing " + method.getName() + " finished in " + elapsed);
       }
    }
}
