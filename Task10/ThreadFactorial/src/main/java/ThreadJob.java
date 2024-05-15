import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ThreadJob implements Runnable {


    private Semaphore semaphore;
    private Scanner scanner;

    public ThreadJob(Semaphore sem, Scanner scanner) {
        this.semaphore = sem;
        this.scanner = scanner;
    }

    private BigInteger factorial(int n) {
        var res = BigInteger.ONE;
        for (int i = 1; i < n; i++) {
            res = res.multiply(BigInteger.valueOf(i));
        }
        return res;
    }

    @Override
    public void run() {
        while (true) {
            int value;
            try {
                semaphore.acquire();
                if (!scanner.hasNextInt()) {
                    //признак того, что мы досчитали до конца файла
                    break;
                }
                value = scanner.nextInt();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finally {
                semaphore.release();
            }
            var factVal = factorial(value);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.printf("f(%d) = %s\n",value,factVal.toString());
        }
        System.out.println("поток " + Thread.currentThread().getName() + " завершился");

    }
}
