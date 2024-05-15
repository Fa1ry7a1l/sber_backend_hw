import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PasswordGame passwordGame = new PasswordGame();

        String answer = "";
        String password = "";
        while (!answer.equals("OK")) {
            System.out.println("Введите пароль (без пробелов)");
            password = scanner.next();
            answer = passwordGame.tryPassword(password);
            System.out.println(answer);
        }
    }
}
