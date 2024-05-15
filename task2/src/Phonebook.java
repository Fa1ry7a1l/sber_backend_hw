import java.util.ArrayList;
import java.util.HashMap;

public class Phonebook {
    private final HashMap<String, ArrayList<String>> _phonebook;
    public Phonebook()
    {
        _phonebook = new HashMap<>();
    }

    public ArrayList<String> get(String name)
    {
        return (ArrayList<String>)_phonebook.getOrDefault(name,new ArrayList<>()).clone();
    }

    public void add(String name, String number)
    {
        ArrayList<String> allNumbers = _phonebook.getOrDefault(name,new ArrayList<>());
        allNumbers.add(number);
        _phonebook.put(name,allNumbers);
    }
}
