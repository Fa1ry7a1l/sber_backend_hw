package task1;

import task1.exceptions.IllegalAmountException;
import task1.exceptions.IllegalOperationException;

public class ServerImpl implements Server {

    private int amount;

    public ServerImpl(int amount) {
        this.amount = amount;
    }

    @Override
    public int checkMoney() {
        return amount;
    }

    @Override
    public int deposit(int depositSum) {
        if (depositSum % 100 != 0)
            throw new IllegalOperationException(String.format("Введена неверная сумма %d. Сумма для снятия или внесения должна быть кратна 100.", depositSum));
        if (depositSum < 0)
            throw new IllegalAmountException(String.format("Введена неверная сумма %d. Сумма для снятия или внесения должна быть положительна.", depositSum));
        amount += depositSum;
        return amount;
    }

    @Override
    public int withdraw(int withdrawSum) {
        if (withdrawSum % 100 != 0)
            throw new IllegalOperationException(String.format("Введена неверная сумма %d. Сумма для снятия или внесения должна быть кратна 100.", withdrawSum));
        if (amount - withdrawSum < 0)
            throw new IllegalAmountException(String.format("Введена сумма для снятия больше, чем есть сейчас на счете. Попробуйте другую сумму. На счете сейчас %d.", amount));
        if (withdrawSum < 0)
            throw new IllegalAmountException(String.format("Введена неверная сумма %d. Сумма для снятия или внесения должна быть положительна.", withdrawSum));
        amount -= withdrawSum;
        return amount;
    }
}
