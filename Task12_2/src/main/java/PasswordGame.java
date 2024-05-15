import chain.*;

public class PasswordGame {

    private final Handler handler;

    public PasswordGame() {
        handler = new PasswordLengthHandler(
                new DigitHandler(
                        new BigLetterHandler(
                                new SpecialCharacterHandler(
                                        new SponsorsHandler(null)))));
    }

    public String tryPassword(String password) {
        return handler.handle(password);
    }

}
