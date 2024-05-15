package org.example;

import java.math.BigInteger;

public interface Calculator {
    /**
     * Расчет факториала числа.
     * @param number
     */
    @Cache
    @Metric
    BigInteger calc (int number) throws InterruptedException;
}
