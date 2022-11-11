package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Point;

public class Circle extends Shape{

    public Circle(Color color, double x, double y, double size, ShapeType type) {
        super(color,x,y,size,type);

    }
    @Override
    public String svgFormat() {
        return "<circle cx=\"" + centerPoint().getPosX() + "\" cy=\"" + centerPoint().getPosY() + "\" r=\"" + (getSize() / 2) +
                "\" fill=\"#" + getColor().toString().substring(2,10) + "\" />";
    }

    @Override
    public void draw (GraphicsContext context){
        context.setFill(getBorderColor());
        context.setFill(getColor());
        context.fillOval(centerPoint().getPosX(), centerPoint().getPosY(), getSize(), getSize());
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
    public boolean isInsideShape(double mouseX, double mouseY) {
        double distX = mouseX - getX();
        double distY = mouseY - getY();
        double distance = Math.sqrt((distX * distX) + (distY * distY));

        return distance <= getSize();
    }

    @Override
    public boolean pointInsideShape(Point point) {
        boolean xInside = point.getPosX() >= centerPoint().getPosX() && point.getPosX() <= centerPoint().getPosX() + getSize();
        boolean yInside = point.getPosY() >= centerPoint().getPosY() && point.getPosY() <= centerPoint().getPosY() + getSize();
        return xInside && yInside;
    }
}
