import java.util.List;

public interface FriendManipulator {

    boolean addFriend(Person f1, Person f2);
    boolean removeFriend(Person f1, Person f2);

    List<Person> getFriends(Person f);
}
