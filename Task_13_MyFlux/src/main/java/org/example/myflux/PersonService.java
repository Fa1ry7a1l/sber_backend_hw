package org.example.myflux;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    private List<Person> persons = new ArrayList<>();

    public PersonService() {
        persons.add(new Person(" Андрей", 15, 9,  0x9ae366));
        persons.add(new Person(" Кирилл", 18, 11,  0x632d43));
        persons.add(new Person(" Алена", 10, 1,  0xe36669));
        persons.add(new Person(" Полина", 12, 21,  0x69e366));
        persons.add(new Person(" Алеша", 37, 31,  0xe066e3));
        persons.add(new Person(" Дима", 28, 5,  0x66a7e3));
        persons.add(new Person(" Кира", 16, 2,  0xa7e366));
    }

    public Person[] getPersons() {
        Person[] res = new Person[persons.size()];
        for (int i = 0; i < persons.size(); i++) {
            res[i] = persons.get(i);
        }
        return res;
    }

}
