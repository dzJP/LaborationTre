package shapes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Position;

import java.util.Objects;

public abstract class Shape {

    private final SimpleDoubleProperty y =  new SimpleDoubleProperty();
    private final SimpleDoubleProperty x = new SimpleDoubleProperty();
    private final SimpleDoubleProperty size = new SimpleDoubleProperty();
    private final SimpleObjectProperty<Color> color = new SimpleObjectProperty();
    private final SimpleObjectProperty<Color> borderColor = new SimpleObjectProperty<>();
    private final ShapeType shape;

    public ShapeType getShape(){
        return shape;
    }

    public Shape(Color color,double x, double y, double size, ShapeType shape) {
        setY(y);
        setX(x);
        setColor(color);
        setBorderColor(Color.YELLOW);
        setSize(size);
        this.shape = shape;
    }

    public Position centerPoint(){
        var centerX = getX() - getSize() / 2;
        var centerY = getY() - getSize() / 2;
        return new Position(centerX, centerY);
    }

    public void draw(GraphicsContext context){

    }

    public double getY() {
        return y.get();
    }
    public void setY(double y) {
        this.y.set(y);
    }
    public double getX() {
        return x.get();
    }
    public void setX(double x) {
        this.x.set(x);
    }
    public double getSize() {
        return size.get();
    }
    public void setSize(double size) {
        this.size.set(size);
    }

    public Color getColor() {
        return color.get();
    }
    public void setColor(Color color) {
        this.color.set(color);
    }
    public Color getBorderColor() {
        return borderColor.get();
    }
    public void setBorderColor(Color borderColor) {
        this.borderColor.set(borderColor);
    }
    public SimpleDoubleProperty yProperty() {
        return y;
    }
    public SimpleDoubleProperty xProperty() {
        return x;
    }
    public SimpleDoubleProperty sizeProperty() {
        return size;
    }
    public SimpleObjectProperty<Color> colorProperty() {
        return color;
    }

    public abstract Shape copyOf();

    public abstract boolean collision(double mouseX, double mouseY);
    public abstract boolean insideShape(Position position);
    public abstract boolean insideShape(double x, double y);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape s = (Shape) o;
        return Objects.equals(y, s.y) && Objects.equals(x, s.x) && Objects.equals(size, s.size) && Objects.equals(color, s.color) && shape == s.shape;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x, size, color, shape);
    }
}

