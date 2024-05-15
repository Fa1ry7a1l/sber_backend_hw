import java.util.HashMap;
import java.util.Map;

public class Main {
    static Map<Integer, String> map = new HashMap<>();

    public static void main(String[] args) {
        Main.help();

        System.out.println(map.get(1000));
    }

    public static void help()
    {
        for (int i = 0; i < 100000; i++) {
            map.put(i, "value" + i);
        }
    }
}
