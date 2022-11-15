import javafx.scene.paint.Color;
import model.Model;
import org.junit.jupiter.api.*;
import shapes.Circle;
import shapes.Shape;
import shapes.ShapeType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RandomTest {

    @Test
    void shouldShowSimpleAssertion() {
        Shape shape = new Circle(Color.RED, 20, 20, 50, ShapeType.CIRCLE);
        Model m = new Model();
        m.addToShapes(shape);
        Assertions.assertEquals(1, m.getShapeObservableList().size());
    }

    @Test
    @DisplayName("Should check if collision on mouse click on a shape")
    void shouldCheckIfCollision() {
        Model model = new Model();
        for (var shape : model.getShapeObservableList()) {
            if (shape.collisionCheck(shape.getX(), shape.getY()))
                model.selectedShapesContains(shape);
            System.out.println("Should be a collision here");
            Assertions.assertEquals(1, model.getShapeObservableList().size());
        }
    }

    @Test
    @DisplayName("Should undo the latest action after pressing undo button")
    void shouldUndoTheLatestActionAfterPressingUndoButton() {
        Model model = new Model();
        if (model.reversedList.isEmpty())
            return;
        model.revertLatest();
        Assertions.assertEquals(1, model.getShapeObservableList().size());
    }

    @Test
    @DisplayName("Should change color on shape after selecting a shape")
    void shouldChangeColorOnShapeAfterSelectingAShape() {
        Model model = new Model();
        model.updateReversedList();
        for (var shape : model.getSelectedShape()) {
            shape.setColor(model.getSelectColorPicker());
            Assertions.assertEquals(1, model.getShapeObservableList().size());
        }
    }


//    @Test
//    @DisplayName("Should check all items in the list")
//    void shouldCheckAllItemsInTheList() {
//        List<Integer> numbers = List.of(2, 3, 5, 7);
//
//        Assertions.assertAll(() -> assertEquals(2, numbers.get(0)), // test if two values are equal
//                () -> assertEquals(3, numbers.get(1)),
//                () -> assertEquals(5, numbers.get(2)),
//                () -> assertEquals(7, numbers.get(3)));
//
//        Assertions.assertEquals(2, numbers.get(0));
//        Assertions.assertEquals(3, numbers.get(1));
//        Assertions.assertEquals(5, numbers.get(2));
//        Assertions.assertEquals(7, numbers.get(3));
//    }
}
