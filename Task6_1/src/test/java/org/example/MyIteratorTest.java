package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyIteratorTest {

    @Test
    @DisplayName("hasNext - false в пустом массиве")
    void hasNextVoid() {
        //given
        MyIterator<Integer> m = new MyIterator<>(new Integer[0]);
        //when
        boolean v = m.hasNext();
        //then
        Assertions.assertFalse(v);
    }

    @Test
    @DisplayName("hasNext - true в начале непустого массива")
    void hasNextNotVoidAtStart() {
        //given
        MyIterator<Integer> m = new MyIterator<>(new Integer[]{10, 15});
        //when
        boolean v = m.hasNext();
        //then
        Assertions.assertTrue(v);

    }

    @Test
    @DisplayName("hasNext - false в конце непустого массива")
    void hasNextNotVoidAtEnd() {
        //given
        MyIterator<Integer> m = new MyIterator<>(new Integer[]{10, 15});
        //when
        m.next();
        m.next();
        boolean v = m.hasNext();
        //then
        Assertions.assertFalse(v);
    }

    @Test
    @DisplayName("next - ArrayIndexOutOfBoundsException в пустом массиве")
    void nextInVoid() {
        //given
        MyIterator<Integer> m = new MyIterator<>(new Integer[0]);
        //when then
        Assertions.assertThrowsExactly(ArrayIndexOutOfBoundsException.class, m::next);
    }

    @Test
    @DisplayName("next - элементы в правильном порядке в непустом массиве")
    void nextInNotVoid() {
        //given
        MyIterator<Integer> m = new MyIterator<>(new Integer[]{10, -15, 31});
        //when
        var res1 = m.next();
        //then
        Assertions.assertEquals(10, res1);

        //when
        var res2 = m.next();
        //then
        Assertions.assertEquals(-15, res2);

        //when
        var res3 = m.next();
        Assertions.assertEquals(31, res3);
    }

    @Test
    @DisplayName("remove - UnsupportedOperationException при удалении, когда итератор ЕЩЕ НЕ указывает на элемент")
    void removeBeforeMove() {
        //given
        MyIterator<Integer> m = new MyIterator<>(new Integer[]{10, -15, 31});
        //when then
        Assertions.assertThrowsExactly(UnsupportedOperationException.class, m::remove);
    }

    @Test
    @DisplayName("remove - UnsupportedOperationException при удалении, когда итератор УКАЗЫВАЕТ на элемент")
    void removeInMove() {
        //given
        MyIterator<Integer> m = new MyIterator<>(new Integer[]{10, -15, 31});
        m.next();
        m.next();

        //when then
        Assertions.assertThrowsExactly(UnsupportedOperationException.class, m::remove);
    }
    @Test
    @DisplayName("remove - UnsupportedOperationException при удалении, когда итератор УЖЕ НЕ указывает на элемент")
    void removeAfterMove() {
        //given
        MyIterator<Integer> m = new MyIterator<>(new Integer[]{10, -15, 31});
        m.next();
        m.next();
        m.next();
        //when //then
        Assertions.assertThrowsExactly(UnsupportedOperationException.class, m::remove);
    }

}