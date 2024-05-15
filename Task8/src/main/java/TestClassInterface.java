import java.math.BigInteger;
import java.util.ArrayList;

public interface TestClassInterface {

    @Cache
    BigInteger defaultFactorial(int n);

    @Cache(exclude = {1, 2, 3}, savingType = SavingType.FILE)
    BigInteger manyParametrsSum(int a, int b, int c, int d);

    @Cache(savingType = SavingType.FILE, zip = true, listLimit = 10)
    ArrayList<Integer> ListResultWithZip(int a);
}
