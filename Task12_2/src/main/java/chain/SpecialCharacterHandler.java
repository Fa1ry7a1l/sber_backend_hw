package chain;

public class SpecialCharacterHandler extends Handler{
    public SpecialCharacterHandler(Handler next) {
        super(next);
    }

    @Override
    protected String check(String password) {
        if ( password.chars().anyMatch(value -> !Character.isDigit(value)
                && !Character.isLetter(value)
                && !Character.isWhitespace(value)))
        {
            return "";
        }
        return  "Правило 4 : Ваш пароль должен содержать один из символов /*!@#$%^&*()\"{}_[]|\\?<>,.";
    }
}
