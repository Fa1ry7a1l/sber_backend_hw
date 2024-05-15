public class Main {
    public static void main(String[] args) {


        System.out.println("Эксперимент с обычным пулом на 5 потоков");
        tryThreadPool(new FixedThreadPool(5));

        System.out.println();
        System.out.println("==========================================");
        System.out.println();
        System.out.println("Эксперимент с расширяемым пулом от 5 до 10 потоков");
        tryThreadPool(new ScalableThreadPool(5, 10));
    }

    public static void tryThreadPool(ThreadPool threadPool) {
        threadPool.start();

        for (int i = 0; i < 5; i++) {
            threadPool.execute(() -> {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        System.out.println("После запуска 5 задач, количество потоков - " + threadPool.getCurrentThreadCount());

        for (int i = 0; i < 12; i++) {
            threadPool.execute(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        System.out.println("После добавления 12 задач, количество потоков - " + threadPool.getCurrentThreadCount());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("через 5 секунд потоков осталось - " + threadPool.getCurrentThreadCount());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("через 3 секунды потоков осталось - " + threadPool.getCurrentThreadCount());

        threadPool.finish();
    }
}

/*Эксперимент с обычным пулом на 5 потоков
После запуска 5 задач, количество потоков - 5
После добавления 12 задач, количество потоков - 5
через 5 секунд потоков осталось - 5
через 3 секунды потоков осталось - 5

==========================================

Эксперимент с расширяемым пулом от 5 до 10 потоков
После запуска 5 задач, количество потоков - 10
После добавления 12 задач, количество потоков - 10
через 5 секунд потоков осталось - 7
через 3 секунды потоков осталось - 5*/