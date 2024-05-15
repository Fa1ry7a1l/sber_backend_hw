package chain;

public class SponsorsHandler extends Handler{

    public SponsorsHandler(Handler next) {
        super(next);
    }

    @Override
    protected String check(String password) {
        String tmp = password.toLowerCase();

        if ( tmp.contains("tesla") || tmp.contains("pepsi") || tmp.contains("adidas"))
        {
            return "";
        }
        return  "Правило 5 : Ваш пароль должен содержать одного из спонсоров - tesla pepsi adidas";
    }
}
