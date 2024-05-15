package chain;

public abstract class Handler {

    private final Handler next;

    public Handler(Handler next) {
        this.next = next;
    }

    protected abstract String check(String password);

    public String handle(String password) {
        String res = check(password);
        if (res.isEmpty()) {
            if (next == null)
                return "OK";
            return next.handle(password);
        } else
            return res;
    }
}
