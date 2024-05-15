package org.example;

import java.util.Iterator;

public class MyIterator<T> implements Iterator<T> {

    private final T[] arr;
    private int currentPosition = -1;


    MyIterator(T[] arr) {
        this.arr = arr;
    }

    @Override
    public boolean hasNext() {
        return currentPosition < arr.length - 1;
    }

    @Override
    public T next() {
        currentPosition++;
        return arr[currentPosition];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("В массиве не поддерживается операция удаления");
    }

}
