import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class FixedThreadPool implements ThreadPool {

    private int threadsCount;

    private boolean running = true;

    private final List<Thread> allThreads = new ArrayList<>();
    private Queue<Runnable> tasksQueue = new ArrayDeque<>();

    public FixedThreadPool(int threadsCount) {
        if (threadsCount < 1) {
            throw new IllegalArgumentException(" странное количество потоков " + threadsCount);
        }
        this.threadsCount = threadsCount;
    }

    private void prepareThreadPool() {
        for (int i = 0; i < threadsCount; i++) {
            allThreads.add(new Thread(this::threadTask));
            allThreads.get(i).start();
        }
    }

    @Override
    public void start() {
        prepareThreadPool();
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (tasksQueue) {
            tasksQueue.add(runnable);
            tasksQueue.notify();
        }
    }

    @Override
    public int getCurrentThreadCount() {
        return allThreads.size();
    }

    private void threadTask() {
        while (running) {
            Runnable task;
            synchronized (tasksQueue) {
                while (tasksQueue.isEmpty() && running) {
                    try {
                        tasksQueue.wait();

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (!running) {
                    return;
                }
                task = tasksQueue.poll();
            }
            try {
                if (task != null) {
                    task.run();
                }
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void finish() {
        running = false;
        synchronized (tasksQueue) {
            tasksQueue.notifyAll();
        }
        for (int i = 0; i < allThreads.size(); i++) {
            try {
                allThreads.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isRunning() {
        return this.running;
    }

}
