package org.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MetricHandler implements InvocationHandler {
    Object delegate;
    public MetricHandler(Object o)
    {
        delegate = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.isAnnotationPresent(Metric.class)) return invoke(method, args);
        long start = System.currentTimeMillis();
        Object result = invoke(method,args);
        long finish = System.currentTimeMillis();
        System.out.printf("Время работы метода: %d (в наносек)\n",finish-start);
        return result;
    }

    private Object invoke(Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(delegate, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Impossible", e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

}
