import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        var file = Paths.get("numbers.txt");
        Scanner scanner = new Scanner(file);

        int threadCount = 10;
        Semaphore sem = new Semaphore(1);
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            threads.add(new Thread(new ThreadJob(sem,scanner)));
        }

        for (Thread value : threads) {
            value.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("поток " + Thread.currentThread().getName() + " завершился");

    }
}
