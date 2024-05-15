import java.math.BigInteger;
import java.util.ArrayList;

public class TestClass implements TestClassInterface {

    @Override
    public BigInteger defaultFactorial(int n) {
        BigInteger res = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            res = res.multiply(BigInteger.valueOf(i));
        }
        return res;
    }


    @Override
    public BigInteger manyParametrsSum(int a, int b, int c, int d) {
        return BigInteger.valueOf(a + b + c + d);
    }

    @Override
    public ArrayList<Integer> ListResultWithZip(int a) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < a; i++) {
            res.add(i);
        }
        return res;
    }
}
