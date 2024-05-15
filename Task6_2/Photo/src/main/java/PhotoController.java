import java.util.Optional;

public interface PhotoController {
    boolean loadPhoto(Photo p);
    Optional<Photo> getPhoto(String path);
}
