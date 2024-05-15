import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ExecutionManager implements ThreadPool {
    public Context execute(Runnable callback, Runnable... tasks) {

        this.callback = callback;
        targetTaskCount += tasks.length;
        context = new Context(this);

        for (Runnable task : tasks) {
            execute(task);
        }
        return context;
    }

    private Runnable callback;

    private int threadsCount;

    private Integer targetTaskCount = 0;

    private Integer finishedTaskCount = 0;

    private Context context;

    private boolean running = true;

    private final List<Thread> allThreads = new ArrayList<>();
    private Queue<Runnable> tasksQueue = new ArrayDeque<>();

    public ExecutionManager(int threadsCount) {
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
            boolean isFinished = false;
            Runnable task;
            synchronized (tasksQueue) {
                if (Thread.currentThread().isInterrupted()) {
                    context.interruptedTaskCount++;
                    return;
                }
                while (tasksQueue.isEmpty() && running) {
                    try {
                        tasksQueue.wait();

                    } catch (InterruptedException e) {
                        context.interruptedTaskCount++;
                        return;
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
                    isFinished = true;
                }
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
                isFinished = false;
            }
            synchronized (tasksQueue) {
                finishedTaskCount++;

                if (isFinished) {
                    context.completedTaskCount++;
                } else {
                    context.failedTaskCount++;
                }
                if (finishedTaskCount >= targetTaskCount) {
                    callback.run();
                    context.finished = true;
                }
            }
        }


    }

    public void interrupt() {
        synchronized (tasksQueue) {
            for (int i = 0; i < threadsCount; i++) {
                allThreads.get(i).interrupt();
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

}
