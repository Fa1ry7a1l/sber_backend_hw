

import java.util.Optional;

public interface Recomender {

    Optional<Person> getFriend();
    Optional<Photo> getPhoto();
    Optional<Present> getPresent();
}
