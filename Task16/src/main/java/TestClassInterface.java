import java.math.BigInteger;
import java.util.ArrayList;

public interface TestClassInterface {

    @Cache
    BigInteger defaultFactorial(int n);

    @Cache(exclude = {1, 2, 3})
    BigInteger manyParametrsSum(int a, int b, int c, int d);

    @Cache( listLimit = 10)
    ArrayList<Integer> ListResult(int a);
}
