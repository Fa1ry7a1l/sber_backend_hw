import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class RecomenderTest {

    @Mock
    Recomender recomender;

    @BeforeEach
    void prepareTest()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Получить рекомендованного друга")
    void getFriend()
    {
        //given
        Person p = new Person("Андрей", "9999");
        when(recomender.getFriend()).thenReturn(Optional.of(p));

        //when
        Optional<Person> result = recomender.getFriend();

        //then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Андрей",result.get().name);
    }

    @Test
    @DisplayName("Получить рекомендованный подарок")
    void getPresent()
    {
        //given
        Present p = new Present("С днем рождения");
        when(recomender.getPresent()).thenReturn(Optional.of(p));

        //when
        Optional<Present> result = recomender.getPresent();

        //then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("С днем рождения",result.get().message);
    }
    @Test
    @DisplayName("Получить рекомендованный подарок")
    void getPhoto()
    {
        //given
        Photo p = new Photo("Самая красивая картина на свете");
        when(recomender.getPhoto()).thenReturn(Optional.of(p));

        //when
        Optional<Photo> result = recomender.getPhoto();

        //then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Самая красивая картина на свете",result.get().description);
    }
}
