package org.example;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random r = new Random();
        Integer[] arr = new Integer[10];
        for(int i = 0;i< 10;i++)
        {
            arr[i] = r.nextInt(0,100);
        }
        var it = new MyIterator<>(arr);
        while (it.hasNext())
        {
            System.out.println(it.next());
        }
    }
}