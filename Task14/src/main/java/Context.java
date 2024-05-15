public class Context {

     int completedTaskCount = 0;
     int failedTaskCount = 0;

     int interruptedTaskCount = 0;
     boolean finished = false;

    private ExecutionManager manager;

    public Context(ExecutionManager manager) {
        this.manager = manager;
    }

    int getCompletedTaskCount() {
        return completedTaskCount;
    }

    int getFailedTaskCount() {
        return failedTaskCount;
    }

    int getInterruptedTaskCount() {
        return interruptedTaskCount;
    }

    void interrupt() {
        manager.interrupt();
    }

    boolean isFinished() {
        return finished;
    }
}
