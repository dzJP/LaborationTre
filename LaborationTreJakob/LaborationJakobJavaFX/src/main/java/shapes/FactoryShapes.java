package shapes;

import javafx.scene.paint.Color;

public class FactoryShapes  {
    public static Rectangle rectangleOf (Color color, double x, double y, double size){
        return new Rectangle(color,x,y,size,ShapeType.RECTANGLE);
    }
    public static Circle circleOf (Color color, double x, double y, double size){
        return new Circle(color,x,y,size,ShapeType.CIRCLE);
    }

    public static Shape createShape (ShapeType type,Color color, double x, double y, double size) {
        return switch (type) {
            case CIRCLE -> circleOf(color, x, y, size);
            case RECTANGLE -> rectangleOf(color, x, y, size);
        };
    }
}