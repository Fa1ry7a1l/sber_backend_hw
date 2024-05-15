import java.util.Optional;

public interface Authentificator {
    boolean tryAuth(String login, String password);
    Optional<Person> getProfile(String login);
}
