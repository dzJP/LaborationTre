package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Position;

public class Circle extends Shape{

    public Circle(Color color, double x, double y, double size, ShapeType type) {
        super(color,x,y,size,type);

    }

    @Override
    public void draw (GraphicsContext context){
        context.setFill(getBorderColor());
        context.setFill(getColor());
        context.fillOval(centerPoint().getPositionX(), centerPoint().getPositionY(), getSize(), getSize());
    }

    @Override
    public Shape copyOf (){
        return new Circle(getColor(), getX(),getY(), getSize(),getShape());
    }


    @Override
    public String toString() {
        return getColor() + super.getClass().toString();
    }


    @Override
    public boolean insideShape(double x, double y){
        double distance = Math.sqrt(coordinateX(x,y) + coordinateY(x,y));
        return distance <= getSize();
    }

    private double coordinateY(double y, double x) {
        double coordinateY = getX() - getY();
        return coordinateY;
    }

    private double coordinateX(double x, double y) {
        double coordinateX = getX() - getX();
        return coordinateX;
    }

    @Override
    public boolean collision(double mouseX, double mouseY) {
        double distX = mouseX - getX();
        double distY = mouseY - getY();
        double distance = Math.sqrt((distX * distX) + (distY * distY));

        return distance <= getSize();
    }

    @Override
    public boolean pointInsideShape(Position position) {
        boolean xInside = position.getPositionX() >= centerPoint().getPositionX() && position.getPositionX() <= centerPoint().getPositionX() + getSize();
        boolean yInside = position.getPositionY() >= centerPoint().getPositionY() && position.getPositionY() <= centerPoint().getPositionY() + getSize();
        return xInside && yInside;
    }
}
