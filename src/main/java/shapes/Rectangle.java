package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Position;

public class Rectangle extends Shape{

    public Rectangle(Color color, double x, double y, double size, ShapeType type){
        super(color, x,y,size,type);

    }

    public String svgFormat() {
        return "<rectangle cx=\"" + centerPoint().getPositionX() + "\" cy=\"" + centerPoint().getPositionY() + "\" r=\"" + (getSize() / 2) +
                "\" fill=\"#" + getColor().toString().substring(2) + "\" />";
    }

    @Override
    public void draw (GraphicsContext context){
        context.setFill(getBorderColor());
        context.setFill(getColor());
        context.fillRect(centerPoint().getPositionX(), centerPoint().getPositionY(), getSize(), getSize() /2 * 1.75);
    }

    @Override
    public Shape copyOf (){
        return new Rectangle(getColor(), getX(),getY(),getSize(),getShape());
    }

    @Override
    public String toString() {
        return getColor() + super.getClass().toString();
    }

    @Override
    public boolean insideShape(double x, double y){
        double coordinate = Math.sqrt(coordinateX(x,y) + coordinateY(x,y));
        return coordinate <= getSize();
    }

    private double coordinateY(double x, double y) {
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
