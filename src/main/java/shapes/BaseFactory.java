package shapes;

import javafx.scene.paint.Color;


public class BaseFactory {
    public static Rectangle rect(Color color, double x, double y, double size){
        return new Rectangle(color,x,y,size,ShapeType.RECTANGLE);
    }
    public static Circle circ(Color color, double x, double y, double size){
        return new Circle(color,x,y,size,ShapeType.CIRCLE);
    }

    public static Shape createShape (ShapeType type,Color color, double x, double y, double size) {
        return switch (type) {
            case CIRCLE -> circ(color, x, y, size);
            case RECTANGLE -> rect(color, x, y, size);
        };
    }
}