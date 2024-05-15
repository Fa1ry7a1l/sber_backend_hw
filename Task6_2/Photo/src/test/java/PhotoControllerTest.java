import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class PhotoControllerTest {
    @Mock
    PhotoController controller;

    @BeforeEach
    void prepareTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Загрузить фотографию")
    void loadPhoto() {
        //given
        Photo p = new Photo("Самая красивая фотография");
        when(controller.loadPhoto(p)).thenReturn(true);

        //when
        boolean result = controller.loadPhoto(p);

        //then
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Получить фотографию")
    void getPhoto() {
        //given
        Photo p = new Photo("Самая красивая фотография");
        when(controller.getPhoto("путь/до/картинки")).thenReturn(Optional.of(p));

        //when
        Optional<Photo> result = controller.getPhoto("путь/до/картинки");

        //then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Самая красивая фотография", result.get().description);
    }

}
