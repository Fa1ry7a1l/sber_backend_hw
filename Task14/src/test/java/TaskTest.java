import org.junit.jupiter.api.*;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskTest {
    private static ThreadPool threadPool;
    private static final int threadsCount = 5;

    private static Task<Integer> task;
    private static Task<Integer> task2;

    @BeforeAll
    public static void before() {
        threadPool = new FixedThreadPool(threadsCount);
        threadPool.start();
    }

    /*функция сначала считается 1 потоком, остальные - ожидают
    * Потом используется уже сохраненный результат функции
    * */

    int task1CountCalls = 0;

    @Test
    void Task1Value() {
        task1CountCalls = 0;

        task = new Task<>(() -> {
            Thread.sleep(2000);
            task1CountCalls++;
            return (Integer) (int) (Math.random() * 10);
        });

        Runnable callable = () -> {
            long end = 0, start = 0;
            Integer res = 0;
            try {
                start = System.currentTimeMillis();
                res = task.get();
                end = System.currentTimeMillis();
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
            }
            System.out.println("результат вычислений - " + res);
            System.out.println("время на подсчет - " + (end - start));
        };

        for (int i = 0; i < 5; i++) {
            threadPool.execute(callable);
        }

        try {
            //нужно подождать или выполнение закончится до начала выполнения тасков
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println("после расчетов время ответа - 0");
        for (int i = 0; i < 2; i++) {
            threadPool.execute(callable);
        }
        try {
            //нужно подождать или выполнение закончится до начала выполнения тасков
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        threadPool.finish();

        assertEquals(1,task1CountCalls,"значение рассчитывалось более 1 раза");

    }

    int task2CountCall = 0;

    @DisplayName("проверка проброса ошибок")
    @Test
    void test2Exception() {
        task2CountCall = 0;
        task2 = new Task<>(() -> {
            Thread.sleep(2000);
            task2CountCall++;
            throw new RuntimeException("Моя ошибочка");
        });

        Runnable callable = () -> {
            long end = 0, start = 0;
            Integer res = 0;
            try {
                start = System.currentTimeMillis();
                res = task2.get();
                end = System.currentTimeMillis();
                System.out.println("результат вычислений - " + res);
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
            }
            System.out.println("время на подсчет - " + (end - start));
        };

        for (int i = 0; i < 5; i++) {
            threadPool.execute(callable);
        }

        try {
            //нужно подождать или выполнение закончится до начала выполнения тасков
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println("после расчетов время ответа - 0");
        for (int i = 0; i < 2; i++) {
            threadPool.execute(callable);
        }
        try {
            //нужно подождать или выполнение закончится до начала выполнения тасков
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        threadPool.finish();

        assertEquals(1,task2CountCall,"рассчет вызывался более 1 раза");
    }


}