import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ScalableThreadPoolTest {
    private static ThreadPool threadPool;
    private static final int threadsCountMin = 5;
    private static final int threadsCountMax = 15;

    @BeforeAll
    public static void before() {
        threadPool = new ScalableThreadPool(threadsCountMin, threadsCountMax);
        threadPool.start();
    }

    @DisplayName("заполнение ThreadPool до минимума")
    @Order(1)
    @Test
    void givenThreadPool_whenFillTillMin_thenThreadCountStaysMin() {

        for (int i = 0; i < 5; i++) {
            threadPool.execute(() -> {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Assertions.assertEquals(threadsCountMin, threadPool.getCurrentThreadCount());
    }

    @DisplayName("заполнение ThreadPool больше максимума")
    @Order(2)

    @Test
    void givenThreadPool_whenAddsMoreThenThreadCountMax_thenThreadCountBecameMax() {

        for (int i = 0; i < 12; i++) {
            threadPool.execute(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        Assertions.assertEquals(threadsCountMin, threadPool.getCurrentThreadCount());

    }

    @DisplayName("завершение ThreadPool")
    @Order(3)

    @Test
    void givenThreadPool_whenAddsTasksFinish_thenThreadCountReturnsSomethingBetweenMinMax() {
        threadPool.finish();
        Assertions.assertTrue(threadsCountMin >= threadPool.getCurrentThreadCount());
        Assertions.assertTrue(threadsCountMax >= threadPool.getCurrentThreadCount());

    }

}