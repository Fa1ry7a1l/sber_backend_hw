public interface ThreadPool {
    void start();

    void execute(Runnable runnable);
    
    int getCurrentThreadCount();

    void finish();
}
