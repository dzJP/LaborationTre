import javafx.scene.paint.Color;
import model.Model;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shapes.Circle;
import shapes.Rectangle;
import shapes.Shape;
import shapes.ShapeType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RandomTest {

    @Test
    void shouldShowSimpleAssertion() {
        Shape shape = new Circle(Color.RED, 50, 50, 50, ShapeType.CIRCLE);
        Model model = new Model();
        model.addToShapes(shape);
        assertEquals(1, model.getShapeObservableList().size()); // will work
//      assertEquals(0, model.getShapeObservableList().size()); // will not work
    }

    @Test
    @DisplayName("Should check if collision method works properly")
    void shouldCheckIfCollisionMethodWorksProperly() {
        Shape shape = new Circle(Color.WHITE, 20, 20, 20, ShapeType.CIRCLE); // shape with x,y pos. 20
        Model model = new Model(); // new model
        model.addToShapes(shape); // add shape
        assertTrue(shape.collisionCheck(20, 20)); // this is true = collision
      //assertTrue(shape.collisionCheck(300,300));               // this is not true = no collision
    }

    @Test
    @DisplayName("Should check and verify that the revertLatest() method works correctly")
    void shouldCheckAndVerifyThatTheRevertLatestMethodWorksCorrectly() {
        Model model = new Model(); // new model so we can call model
        Shape shape = new Circle(Color.PALEGREEN, 40, 40, 30, ShapeType.CIRCLE); // creating a circle shape
        model.addToShapes(shape); // adding shape to model list
        model.revertLatest(); // revert latest change
        assertEquals(1, model.getShapeObservableList().size());
        //assertEquals(0, model.getShapeObservableList().size());
    }


    @Test
    @DisplayName("Should call updateReversedList to add existing shapes to a temporary list")
    void shouldCallUpdateReversedListToAddExistingShapesToATemporaryList() {
        Model model = new Model(); // new model so we can call model
        Shape circleShape = new Circle(Color.BLUEVIOLET, 70,70,50,ShapeType.CIRCLE); // creating a circle shape
        Shape rectangleShape = new Rectangle(Color.GRAY, 40,40,40,ShapeType.RECTANGLE);
        model.addToShapes(circleShape);
        model.addToShapes(rectangleShape);
        model.updateReversedList(); // calling method to copy and add to a temporary list
        assertEquals(2,model.getShapeObservableList().size());
        //assertEquals(1,model.getShapeObservableList().size());
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
//}
