import java.math.BigInteger;
import java.util.ArrayList;

public class TestClass implements TestClassInterface {

    @Override
    public BigInteger defaultFactorial(int n) {
        BigInteger res = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            res = res.multiply(BigInteger.valueOf(i));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }


    @Override
    public BigInteger manyParametrsSum(int a, int b, int c, int d) {
        return BigInteger.valueOf(a + b + c + d);
    }

    @Override
    public ArrayList<Integer> ListResult(int a) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < a; i++) {
            res.add(i);
        }
        return res;
    }
}
