package task1;

import java.awt.event.KeyAdapter;

public interface Server {
    int checkMoney();

    int deposit(int amount);

    int withdraw(int amount);
}
