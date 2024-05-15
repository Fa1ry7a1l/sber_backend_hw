package chain;

public class BigLetterHandler extends Handler{
    public BigLetterHandler(Handler next) {
        super(next);
    }

    @Override
    protected String check(String password) {
        if ( password.chars().anyMatch(Character::isUpperCase))
        {
            return "";
        }
        return  "Правило 3 : Ваш пароль должен содержать заглавную латинскую букву";
    }
}
