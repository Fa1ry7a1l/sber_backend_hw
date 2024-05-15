public class PasswordGame {
    public String tryPassword(String password) {
        if (!checkPasswordLength(password)) {
            return "Правило 1 : Ваш пароль должен состоять как минимум из 5 символов";
        }
        if (!checkContainsDigit(password)) {
            return "Правило 2 : Ваш пароль должен содержать цифру";
        }
        if (!checkContainsBigLetter(password)) {
            return "Правило 3 : Ваш пароль должен содержать заглавную латинскую букву";
        }
        if (!checkSpecialCharacter(password)) {
            return "Правило 4 : Ваш пароль должен содержать один из символов /*!@#$%^&*()\"{}_[]|\\?<>,.";
        }
        if (!checkSponsors(password)) {
            return "Правило 5 : Ваш пароль должен содержать одного из спонсоров - tesla pepsi adidas";
        }

        return "OK";
    }

    private boolean checkPasswordLength(String password) {
        return password.length() >= 5;
    }

    private boolean checkContainsDigit(String password) {
        return password.chars().anyMatch(Character::isDigit);
    }

    private boolean checkContainsBigLetter(String password) {
        return password.chars().anyMatch(Character::isUpperCase);
    }

    private boolean checkSpecialCharacter(String password) {
        return password.chars().anyMatch(value -> !Character.isDigit(value)
                && !Character.isLetter(value)
                && !Character.isWhitespace(value));
    }
    private boolean checkSponsors(String password) {
        String tmp = password.toLowerCase();
        return tmp.contains("tesla") || tmp.contains("pepsi") || tmp.contains("adidas");
    }
}
