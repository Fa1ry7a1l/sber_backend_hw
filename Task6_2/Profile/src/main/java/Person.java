import java.util.ArrayList;
import java.util.List;

public class Person {
    public String name;

    public String passwordHash;

    //тут должна быть какая нибудь ленивая загрузка
    public List<Person> friends;

    public Person(String name, String passwordHash) {
        this.name = name;
        this.passwordHash = passwordHash;
        friends = new ArrayList<>();
    }
}
