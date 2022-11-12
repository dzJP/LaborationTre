import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest {

    @Test
    void shouldShowSimpleAssertion() {
        Assertions.assertEquals(1, 1);
    }

    @Test
    @DisplayName("Should check all items in the list")
    void shouldCheckAllItemsInTheList() {
        List<Integer> numbers = List.of(2, 3, 5, 7);

        Assertions.assertAll(() -> assertEquals(2, numbers.get(0)),
                () -> assertEquals(3, numbers.get(1)),
                () -> assertEquals(5, numbers.get(2)),
                () -> assertEquals(7, numbers.get(3))); // Faster test, checks every assertion, even if the first one fails.

        Assertions.assertEquals(2, numbers.get(0));
        Assertions.assertEquals(3, numbers.get(1));
        Assertions.assertEquals(5, numbers.get(2));
        Assertions.assertEquals(7, numbers.get(3));  // Slow, one by one test.
    }

}
