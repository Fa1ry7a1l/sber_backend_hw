import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FixedThreadPoolTest {
    private static ThreadPool threadPool;
    private static final int threadsCount = 5;
    @BeforeAll
    public static void before()
    {
        threadPool = new FixedThreadPool(threadsCount);
        threadPool.start();
    }

    @DisplayName("полное заполнение ThreadPool")
    @Order(1)
    @Test
    void givenThreadPool_whenFill_thenThreadCountStaysSame() {

        for (int i = 0; i < 5; i++) {
            threadPool.execute(() -> {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Assertions.assertEquals(threadsCount,threadPool.getCurrentThreadCount());
    }

    @DisplayName("добавление в полный ThreadPool")
    @Order(2)
    @Test
    void givenThreadPool_whenAddsMoreThenThreadCount_thenThreadCountStaysSame() {

        for (int i = 0; i < 12; i++) {
            threadPool.execute(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        Assertions.assertEquals(threadsCount,threadPool.getCurrentThreadCount());

    }
    @DisplayName("завершение ThreadPool")
    @Order(3)
    @Test
    void givenThreadPool_whenAddsTasksFinish_thenThreadCountStaysSame() {
        threadPool.finish();
        Assertions.assertEquals(threadsCount,threadPool.getCurrentThreadCount());

    }

}