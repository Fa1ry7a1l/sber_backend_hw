package task1;

import task1.exceptions.AccountIsLockedException;
import task1.exceptions.IllegalCharacterException;
import task1.exceptions.PinValidationException;
import task1.exceptions.TooManyDigitsException;

import java.util.Arrays;
import java.util.Calendar;

public class PinValidator {
    private boolean pinValid = false;
    private int counter = 0;
    private  int errorCounter = 0;

    public Calendar calendar = Calendar.getInstance();
    int[] pin = new int[]{-1, -1, -1, -1};

    public PinValidator()
    {
        calendar.add(Calendar.DAY_OF_MONTH,-1);
    }

    private void CheckBlock()
    {
        Calendar calendar1 = Calendar.getInstance();
        if(calendar1.before(calendar))
        {
            double secondsRemain = (calendar.getTimeInMillis()-calendar1.getTimeInMillis())/1000.0;
            throw new AccountIsLockedException(String.format("До разблокировки аккаунта осталось %.2f секунд",secondsRemain));
        }
    }

    public  void addChar(char c) {

        CheckBlock();

        if(counter>=4)
        {
            throw new TooManyDigitsException("Введено слишком много ");
        }
        if(!Character.isDigit(c))
        {
            throw new IllegalCharacterException("В пин-коде могут встречаться только цифры, а вы ввели " + c);
        }
        pin[counter++]=c-'0';

    }

    public void tryPin() {
        counter = 0;
        if(Arrays.equals(pin,new int[]{1,1,1,1}))
        {
            pinValid = true;
        }
        else
        {
            errorCounter++;
            pinValid = false;
            if(errorCounter >=3)
            {
                errorCounter = 0;
                calendar = Calendar.getInstance();
                calendar.add(Calendar.SECOND,10);
                throw new PinValidationException("Слишком много неправильных вводов пин-кода , аккаунт заблокирован на 10 секунд");
            }
        }
    }

    public boolean isPinValid() {
        return pinValid;
    }

    public int getCounter() {
        return counter;
    }

    public int getErrorCounter()
    {
        return errorCounter;
    }
}
