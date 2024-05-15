package org.example;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class MyTests {

    @Test
    public void Task1() throws InterruptedException {
        CalculatorImpl calculator = new CalculatorImpl();
        assertEquals(calculator.calc(0), BigInteger.valueOf(1));
        assertEquals(calculator.calc(1), BigInteger.valueOf(1));
        assertEquals(calculator.calc(2), BigInteger.valueOf(2));
        assertEquals(calculator.calc(3), BigInteger.valueOf(6));
        assertEquals(calculator.calc(4), BigInteger.valueOf(24));
        assertEquals(calculator.calc(5), BigInteger.valueOf(120));
    }

    @Test
    public void Task2() {
        Class c = Child.class;
        while (c != null) {
            var methods = c.getDeclaredMethods();
            for (var m : methods)
                System.out.println(m);
            c = c.getSuperclass();
        }
    }

    @Test
    public void Task3() {
        Class c = Child.class;
        var methods = c.getMethods();
        var getMethods = Arrays.stream(methods).filter(method -> method.getName().startsWith("get")).toList();
        for (var m : getMethods)
            System.out.println(m);
    }

    @Test
    public void Task4() {
        Parent p = new Parent();
        Child c = new Child();
        assertTrue(Utils.allConstantsContainsTheirNamesAsValues(p));
        assertFalse(Utils.allConstantsContainsTheirNamesAsValues(c));//тут отличается константа от своего имени
    }
    @Test
    public void Task5() throws InterruptedException {
        Calculator calc = new CalculatorImpl();
        Calculator newCalc = (Calculator) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),calc.getClass().getInterfaces(),new Cacher(calc));
        System.out.println(newCalc.calc(40));
        System.out.println(newCalc.calc(50));
        System.out.println(newCalc.calc(40));
        System.out.println(newCalc.calc(40));
    }
    @Test
    public void Task6() throws InterruptedException {
        Calculator calc = new CalculatorImpl();
        Calculator newCalc = (Calculator) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),calc.getClass().getInterfaces(),new Cacher(calc));
        Calculator newCalc2 = (Calculator) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),calc.getClass().getInterfaces(),new MetricHandler(newCalc));
        System.out.println(newCalc2.calc(40));
        System.out.println(newCalc2.calc(50));
        System.out.println(newCalc2.calc(40));
        System.out.println(newCalc2.calc(40)); //показывает результат вызова кэшированных за 0 мс
    }
    @Test
    public void Task6_invert() throws InterruptedException {
        Calculator calc = new CalculatorImpl();
        Calculator newCalc = (Calculator) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),calc.getClass().getInterfaces(),new MetricHandler(calc));
        Calculator newCalc2 = (Calculator) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),calc.getClass().getInterfaces(),new Cacher(newCalc));
        System.out.println(newCalc2.calc(40));
        System.out.println(newCalc2.calc(50));
        System.out.println(newCalc2.calc(40));
        System.out.println(newCalc2.calc(40));// не показывает время работы кэшированных рещультатов ( потому что вызова на самом деле не происходит)
    }
    @Test
    public void Task7() throws InvocationTargetException, IllegalAccessException {
        B b = new B(1,0.5,"Привет");
        D d = new D(0,1000,"Пока");
        BeanUtils.assign(b,d);
        assertEquals(b.s,d.s);
        assertEquals(b.a,d.a);
        assertNotEquals(b.b,d.b);
    }


}
