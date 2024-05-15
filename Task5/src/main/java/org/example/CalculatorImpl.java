package org.example;

import java.math.BigInteger;

public class CalculatorImpl implements Calculator {

    @Override
    public BigInteger calc(int number) throws InterruptedException {
        if (number < 0)
            throw new IllegalArgumentException("Попытка взятия факториала от лтрицательного числа");
        BigInteger res = BigInteger.valueOf(1);
        for (int i = 1; i <= number; i++) {
            res = res.multiply(BigInteger.valueOf(i));
        }
        Thread.sleep(2000);
        return res;
    }




}
