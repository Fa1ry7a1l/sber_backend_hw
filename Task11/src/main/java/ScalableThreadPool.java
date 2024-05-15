import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ScalableThreadPool implements ThreadPool {
    private int min;
    private int max;
    private Integer currentTaskCounter = 0;

    private boolean running = true;

    private List<Thread> allThreads = new ArrayList<>();
    private Queue<Runnable> tasksQueue = new ArrayDeque<>();

    public ScalableThreadPool(int min, int max) {
        if (min < 1) {
            throw new IllegalArgumentException(" странное количество потоков " + min);
        }

        if (min > max) {
            throw new IllegalArgumentException("минимальное число потоков больше максимального ");

        }

        this.min = min;
        this.max = max;
    }

    private void prepareThreadPool() {
        for (int i = 0; i < min; i++) {
            allThreads.add(new Thread(this::threadTask));
            allThreads.get(i).start();
        }
    }

    @Override
    public void start() {
        prepareThreadPool();
    }

    public int getCurrentThreadCount() {
        return allThreads.size();
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (tasksQueue) {
            tasksQueue.add(runnable);

            //это нужно чтобы мы просто так не создавали пустые потоки, если есть незанятые.
            synchronized (currentTaskCounter) {
                if (max > allThreads.size() && allThreads.size() < currentTaskCounter) {
                    allThreads.add(new Thread(this::threadTask));
                    allThreads.getLast().start();
                }
            }
            tasksQueue.notify();
        }
    }

    private void threadTask() {
        while (running) {
            Runnable task;
            synchronized (tasksQueue) {
                if (tasksQueue.isEmpty() && allThreads.size() > min) {
                    threadSelfDeletion();
                    return;
                }

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

            synchronized (currentTaskCounter) {
                currentTaskCounter++;
            }
            try {
                if (task != null) {
                    task.run();
                }
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }

            synchronized (currentTaskCounter) {
                currentTaskCounter--;
            }
        }
        if (allThreads.size() > min) {
            threadSelfDeletion();
            return;
        }
    }

    private void threadSelfDeletion() {

        allThreads.remove(Thread.currentThread());
    }


    public void finish() {
        running = false;
        synchronized (tasksQueue) {
            tasksQueue.notifyAll();
            for (int i = 0; i < allThreads.size(); i++) {
                try {
                    allThreads.get(i).join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public boolean isRunning() {
        return this.running;
    }


}
