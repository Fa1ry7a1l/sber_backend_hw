package chain;

public class PasswordLengthHandler extends Handler {
    public PasswordLengthHandler(Handler next) {
        super(next);
    }

    @Override
    protected String check(String password) {
        if ( password.length() >= 5)
        {
            return "";
        }
        return  "Правило 1 : Ваш пароль должен состоять как минимум из 5 символов";
    }
}
