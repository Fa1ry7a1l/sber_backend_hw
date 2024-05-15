import java.util.concurrent.Callable;

public class Task<T> {

    private T value = null;
    private State state;
    private Callable<? extends T> callable;

    private Exception exception;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
        state = State.NotStarted;
    }

    public T get() throws Exception {
        if (state != State.NotStarted) {
            if (state == State.Running) {
                synchronized (this) {
                    //просто чтобы подождали
                }
            }

            if (state == State.Finished) {
                return value;
            } else {
                throw new CallerException(exception);
            }
        } else {
            synchronized (this) {
                if (state == State.NotStarted) {
                    state = State.Running;
                    try {
                        value = callable.call();
                        state = State.Finished;
                        return value;
                    } catch (Exception e) {
                        exception =new CallerException(e);
                        state = State.Exception;
                        throw  exception;
                    }
                } else {
                    return get();
                }
            }
        }

    }

    enum State {
        NotStarted,
        Running,
        Finished,
        Exception
    }
}

