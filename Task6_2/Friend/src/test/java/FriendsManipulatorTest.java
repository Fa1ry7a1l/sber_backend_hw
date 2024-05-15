import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FriendsManipulatorTest {

    static FriendManipulator controller;

    @BeforeAll
    static void prepareTest() {
        controller = Mockito.mock(FriendManipulator.class);
        //поведение добавления друзей
        when(controller.addFriend(any(), any())).then(invocationOnMock ->
        {
            Person person1 = invocationOnMock.<Person>getArgument(0);
            Person person2 = invocationOnMock.<Person>getArgument(1);
            if (person1.friends.contains(person2)) {
                return false;
            }
            person1.friends.add(person2);
            person2.friends.add(person1);
            return true;
        });

        //поведение удаления
        when(controller.removeFriend(any(), any())).then(invocationOnMock ->
        {
            Person person1 = invocationOnMock.<Person>getArgument(0);
            Person person2 = invocationOnMock.<Person>getArgument(1);
            if (!person1.friends.contains(person2)) {
                return false;
            }
            person1.friends.remove(person2);
            person2.friends.remove(person1);
            return true;
        });
    }

    @Test
    @DisplayName("Добавление друга")
    void addFriend() {
        //given
        Person p1 = new Person("Андрей", "123");
        Person p2 = new Person("Полина", "123");

        //when
        boolean result = controller.addFriend(p1, p2);

        //then
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Попытка добавить друга который и так друг")
    void addFriendAlready() {
        //given
        Person p1 = new Person("Андрей", "123");
        Person p2 = new Person("Полина", "123");
        p1.friends.add(p2);
        p2.friends.add(p1);

        //when
        boolean result = controller.addFriend(p1, p2);

        //then
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Удаление друга, которые не является другом")
    void removeFriend() {
        //given
        Person p1 = new Person("Андрей", "123");
        Person p2 = new Person("Полина", "123");

        //when
        boolean result = controller.removeFriend(p1, p2);

        //then
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Удаление друга")
    void removeFriendAlready() {
        //given
        Person p1 = new Person("Андрей", "123");
        Person p2 = new Person("Полина", "123");
        p1.friends.add(p2);
        p2.friends.add(p1);

        //when
        boolean result = controller.removeFriend(p1, p2);

        //then
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Получение списка друзей")
    void getFriends() {
        //given
        Person p1 = new Person("Андрей", "123");
        Person p2 = new Person("Полина", "123");
        Person p3 = new Person("Кирилл", "123");
        p1.friends.add(p2);
        p2.friends.add(p1);
        p2.friends.add(p3);
        p3.friends.add(p2);

        var testCollection =Arrays.asList(p1,p3);

        when(controller.getFriends(p2)).thenReturn(Arrays.asList(p1,p3));

        //when
        List<Person> result = controller.getFriends(p2);

        //then
        Assertions.assertTrue(result.containsAll(testCollection));
        Assertions.assertTrue(testCollection.containsAll(result));
    }
    @Test
    @DisplayName("Получение пустого списка друзей")
    void getFriendsEmpty() {
        //given
        Person p1 = new Person("Андрей", "123");


        when(controller.getFriends(p1)).thenReturn(new ArrayList<>());

        //when
        List<Person> result = controller.getFriends(p1);

        //then
        Assertions.assertTrue(result.isEmpty());
    }

}
