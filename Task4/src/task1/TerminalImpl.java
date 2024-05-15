package task1;

import task1.exceptions.PinException;
import task1.exceptions.ServerException;

import java.util.Scanner;

public class TerminalImpl implements Terminal {

    final private Server server;
    final private TerminalFront terminalFront;
    private final PinValidator pinValidator;
    private final Scanner scanner;

    public TerminalImpl(TerminalFront terminalFront, PinValidator pinValidator, Server server) {
        this.server = server;
        this.pinValidator = pinValidator;
        this.terminalFront = terminalFront;
        this.scanner = new Scanner(System.in);
    }

    public void doWork() {
        connect();
        String message = """
                Выберите
                1 - проверить счет
                2 - внести деньги
                3 - снять деньги
                4 - выход
                                
                """;

        int code = -1;
        while (code != 4) {
            terminalFront.ShowMessage(message);
            code = scanner.nextInt();
            switch (code) {
                case 1 -> checkMoney();
                case 2 -> depositMoney();
                case 3 -> withdrawMoney();
                case 4 -> {
                    break;
                }
                default -> terminalFront.ShowMessage("Вы ввели неправильную операцию");
            }

        }
        terminalFront.ShowMessage("Пока пока");
    }

    @Override
    public void connect() {
        terminalFront.ShowMessage("Введите ъ для завершения ввода пин-кода");
        char c;
        while (!pinValidator.isPinValid()) {
            c = scanner.next().toLowerCase().charAt(0);
            try {
                terminalFront.ShowMessage("Введен символ " + c);

                if (c == 'ъ') {
                    pinValidator.tryPin();
                    terminalFront.ShowMessage(pinValidator.isPinValid() ? "Вы вошли" : String.format("Неправильный пин-код, попробуйте еще раз, осталось %d попыток", 3 - pinValidator.getErrorCounter()));
                    if (pinValidator.isPinValid())
                        return;
                } else {
                    pinValidator.addChar(c);
                }
            } catch (PinException e) {
                terminalFront.ShowMessage(e.getMessage());
            }
            terminalFront.ShowMessage(String.format("осталось %d символов пин-кода", 4 - pinValidator.getCounter()));

        }
    }


    @Override
    public void checkMoney() {
        terminalFront.ShowMessage(String.format("У вас на счету %d", server.checkMoney()));
    }

    @Override
    public void withdrawMoney() {
        int amount = 0;
        terminalFront.ShowMessage("Введите сумму для снятия. Она должна быть кратна 100 и не превышать текущий баланс");
        amount = scanner.nextInt();
        try {
            int res = server.withdraw(amount);
            terminalFront.ShowMessage(String.format("Новый баланс - %d", res));
        } catch (ServerException e) {
            terminalFront.ShowMessage(e.getMessage());
        }
    }

    @Override
    public void depositMoney() {
        int amount = 0;
        terminalFront.ShowMessage("Введите сумму для снятия. Она должна быть кратна 100 и не превышать текущий баланс");
        amount = scanner.nextInt();
        try {
            int res = server.deposit(amount);
            terminalFront.ShowMessage(String.format("Новый баланс - %d", res));

        } catch (ServerException e) {
            terminalFront.ShowMessage(e.getMessage());
        }
    }
}
