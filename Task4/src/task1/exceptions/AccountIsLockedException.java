package task1.exceptions;

public class AccountIsLockedException extends PinException {
    public AccountIsLockedException(String message) {
        super(message);
    }
}
