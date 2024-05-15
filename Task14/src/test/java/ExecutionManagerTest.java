import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionManagerTest {

    public int callbackTest1Count = 0;

    @Test
    public void testNormalWork() {
        ExecutionManager executionManager = new ExecutionManager(5);
        executionManager.start();
        callbackTest1Count = 0;
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
            callbackTest1Count++;
        };

        var context = executionManager.execute(callback, r1, r2);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executionManager.finish();


        Assertions.assertTrue(context.isFinished());
        Assertions.assertEquals(2, context.getCompletedTaskCount());
        Assertions.assertEquals(1, callbackTest1Count,"Callback вызвылся более 1 раза");
    }

    public int callbackTest2Count = 0;

    @Test
    public void testWithExceptionsWork() {

        callbackTest2Count = 0;


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
        Runnable r3 = () -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException("Нужная какая то ошибка");
        };
        Runnable callback = () -> {
            System.out.println("callback");
            callbackTest2Count++;
        };

        var context = executionManager.execute(callback, r1, r3, r1, r1, r1, r1);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executionManager.finish();


        Assertions.assertTrue(context.isFinished());
        Assertions.assertEquals(5, context.getCompletedTaskCount());
        Assertions.assertEquals(1, context.getFailedTaskCount());
        Assertions.assertEquals(1, callbackTest2Count, "Callback вызвался более 1 раза");
    }

}