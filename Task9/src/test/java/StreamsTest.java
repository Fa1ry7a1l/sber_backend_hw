import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class StreamsTest {

    @Test
    @DisplayName("тест из задания")
    public void mainTest() {
        List<Person> someCollection = new ArrayList<>();
        someCollection.add(new Person("Андрей", 21));
        someCollection.add(new Person("Женя", 18));
        someCollection.add(new Person("Кирилл", 28));

        var testPerson =new Person("У девочки нет имени", 58);
        Map m = Streams.of(someCollection)
                .filter(p -> p.getAge() > 20)
                .transform(p -> new Person(p.getAge() + 30))
                .toMap(p -> p.getName(), p -> p);


        Assertions.assertEquals(1,m.size());
        Assertions.assertEquals(testPerson, m.get("У девочки нет имени"));
        // могло бы быть и больше, но в transform вызывается
        // создание нового человека без указания имени, приходится подставлять дефолтное.
        // А оно используется в качестве ключа, так что персоны перезаписываются
    }

    @Test
    @DisplayName("тест фильтра")
    public void filterTest() {
        List<Person> someCollection = new ArrayList<>();
        someCollection.add(new Person("Андрей", 21));
        someCollection.add(new Person("Женя", 18));
        someCollection.add(new Person("Кирилл", 28));
        someCollection.add(new Person("Аня", 31));
        someCollection.add(new Person("Валя", 8));
        someCollection.add(new Person("Леша", 15));
        someCollection.add(new Person("Кира", 24));

        List<Person> testList = new ArrayList<>();

        testList.add(new Person("Кирилл", 28));
        testList.add(new Person("Аня", 31));
        testList.add(new Person("Кира", 24));

        List m = Streams.of(someCollection)
                .filter(p -> p.getAge() > 21)
                        .toList();


        Assertions.assertEquals(testList,m);
    }


    @Test
    @DisplayName("тест трансформации")
    public void transformTest() {
        List<Person> someCollection = new ArrayList<>();
        someCollection.add(new Person("Андрей", 21));
        someCollection.add(new Person("Женя", 18));
        someCollection.add(new Person("Кирилл", 28));
        someCollection.add(new Person("Аня", 31));
        someCollection.add(new Person("Валя", 8));
        someCollection.add(new Person("Леша", 15));
        someCollection.add(new Person("Кира", 24));

        List<Integer> testList = new ArrayList<>();
        testList.add(6);
        testList.add(4);
        testList.add(6);
        testList.add(3);
        testList.add(4);
        testList.add(4);
        testList.add(4);

        List m = Streams.of(someCollection)
                .transform(p -> p.getName().length())
                .toList();


        Assertions.assertEquals(testList,m);
    }

    @Test
    @DisplayName("тесттрансформации")
    public void toMapTest() {
        List<Person> someCollection = new ArrayList<>();
        someCollection.add(new Person("Андрей", 21));
        someCollection.add(new Person("Женя", 18));
        someCollection.add(new Person("Кирилл", 28));
        someCollection.add(new Person("Аня", 31));
        someCollection.add(new Person("Валя", 8));
        someCollection.add(new Person("Леша", 15));
        someCollection.add(new Person("Кира", 24));

        Map<String,Integer> testMap = new HashMap<>();
        testMap.put("Андрей", 21);
        testMap.put("Женя", 18);
        testMap.put("Кирилл", 28);
        testMap.put("Аня", 31);
        testMap.put("Валя", 8);
        testMap.put("Леша", 15);
        testMap.put("Кира", 24);


        Map<String,Integer> m = Streams.of(someCollection)
                .toMap(Person::getName, Person::getAge);


        Assertions.assertEquals(testMap,m);
    }



}