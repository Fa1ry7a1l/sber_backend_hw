import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class PresenterTest {
    @Mock
    Presenter controller;

    @BeforeEach
    void prepareTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("ОТправка подарка")
    void loadPhoto() {
        //given
        Present p = new Present("С новым годом!!");
        Person p2 = new Person("Полина","123");
        when(controller.present(p2,p)).thenReturn(true);

        //when
        boolean result = controller.present(p2,p);

        //then
        Assertions.assertTrue(result);
    }

}
