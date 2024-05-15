import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("пункт 1");
        ArrayList<String> allLines = new ArrayList<>(Arrays.asList("привет", "слон", "конфета", "привет", "конфета", "ящик", "клюква", "слон", "конфета", "привет", "привет", "слон", "ящик", "ящик", "ящик"));
        HashMap<String, Integer> map = new HashMap<>();
        for (var s : allLines) {
            map.put(s, map.getOrDefault(s, 0) + 1);

        }

        for (var k : map.keySet()) {
            System.out.printf("%s %d \n",k,map.get(k));
        }

        System.out.println("\n\n\n\n");

        System.out.println("пункт 2");

        Phonebook phonebook = new Phonebook();
        phonebook.add("Андрей","123456");
        phonebook.add("Ваня","312");
        phonebook.add("Андрей","111");
        phonebook.add("Кирилл","123");
        phonebook.add("Антон","3333");

        System.out.println("Номера Андрея: " + Arrays.toString(phonebook.get("Андрей").toArray()));
        System.out.println("Номера Ваня: " + Arrays.toString(phonebook.get("Ваня").toArray()));
        System.out.println("Номера Кирилл: " + Arrays.toString(phonebook.get("Кирилл").toArray()));
        System.out.println("Номера Антон: " + Arrays.toString(phonebook.get("Антон").toArray()));

    }
}