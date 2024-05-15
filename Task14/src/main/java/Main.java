public class Main {
    public static void main(String[] args) {


       /* ThreadPool threadPool = new FixedThreadPool(5);
        threadPool.start();

        Task<Integer> task = new Task<>(() -> {
            Thread.sleep(2000);
            return (Integer) (int) (Math.random() * 10);
        });
        threadPool.execute(() -> {
            System.out.println("started");
            long end = 0, start = 0;
            Integer res2=0;
            try {
                start = System.currentTimeMillis();
                res2 =task.get();
                end = System.currentTimeMillis();
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
            }

            System.err.println(res2);
            System.out.println("время на подсчет - " + (end - start));
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        threadPool.finish();*/

        ExecutionManager executionManager = new ExecutionManager(5);
        executionManager.start();

        Runnable r1 = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("r1 finished");
        };
        Runnable r2 = () -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("r2 finished");
        };
        Runnable callback = () -> {
            System.out.println("callback");
        };

        var context = executionManager.execute(callback, r1, r2);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executionManager.finish();

        System.out.println(context.finished);

    }
}
