package org.example;

import java.util.Random;

public class Child extends Parent {

    public static final String THIRD_NAME = "Andrey";
    public String d()
    {
        return "sds";
    }
    protected float e()
    {
        return 0.1f;
    }

    private void f()
    {

    }
    public int getRandom()
    {
        Random random = new Random();
        return random.nextInt();
    }

}
