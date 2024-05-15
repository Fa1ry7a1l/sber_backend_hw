package chain;

public class DigitHandler extends Handler{
    public DigitHandler(Handler next) {
        super(next);
    }

    @Override
    protected String check(String password) {
        if ( password.chars().anyMatch(Character::isDigit))
        {
            return "";
        }
        return  "Правило 2 : Ваш пароль должен содержать цифру";
    }
}
