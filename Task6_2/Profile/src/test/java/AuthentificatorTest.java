import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class AuthentificatorTest {
    @Mock
    Authentificator authentificator;

    @BeforeEach
    void prepareTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("аутентификация")
    void tryAuth() {
        //given
        Person p = new Person("Андрей","123");
        when(authentificator.tryAuth("Андрей","123")).thenReturn(true);

        //when
        boolean result = authentificator.tryAuth("Андрей","123");

        //then
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("получение пользователя по логину")
    void getProfile() {
        //given
        Person p = new Person("Андрей","123");
        when(authentificator.getProfile("Андрей")).thenReturn(Optional.of(p));

        //when
        Optional<Person> result = authentificator.getProfile("Андрей");

        //then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Андрей",result.get().name);
    }
}
