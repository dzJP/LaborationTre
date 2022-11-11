package model;

import shapes.FactoryShapes;
import shapes.Shape;
import shapes.ShapeType;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private final BooleanProperty Circle;
    private final BooleanProperty Rectangle;
    private Position position;
    private final ObservableList<Shape> shapeObservableList;
    private final ObservableList<Shape> selectedShapes;
    public final List<List<Shape>> undoList = new ArrayList<>();

    private final ObjectProperty<Color> selectColorPicker;
    private final ObjectProperty<Color> borderColor;
    private final StringProperty sizeSelect;
    private final List<Integer> changeList;

    private final BooleanProperty selectOption;
    private ShapeType shapeType = ShapeType.CIRCLE;



    public ShapeType getShapeType() {
        return shapeType;
    }

    public void setPoint(double mousePointX, double mousePointY) {
        this.position = new Position(mousePointX, mousePointY);
    }

    public void changeSizeOnSelectedShape (){
        addChangesToUndoList();
        for(var shape : selectedShapes) {
            shape.setSize(getSizeText());
        }
    }

    public void createShapeToList(ShapeType type) {
        Shape shape = FactoryShapes.createShape(type, selectColorPicker.get(), position.getPositionX(), position.getPositionY(),getSizeText());
        shapeObservableList.add(shape);
    }

    public Model() {
        this.selectColorPicker = new SimpleObjectProperty<>(Color.BLACK);
        this.borderColor = new SimpleObjectProperty<>();
        this.borderColor.set(Color.TEAL);
        this.Circle = new SimpleBooleanProperty(false);
        this.selectedShapes = FXCollections.observableArrayList();
        this.changeList = new ArrayList<>();
        this.selectOption = new SimpleBooleanProperty();
        this.Rectangle = new SimpleBooleanProperty(false);
        this.shapeObservableList = FXCollections.observableArrayList(shape -> new Observable[]{
                shape.colorProperty(),
                shape.xProperty(),
                shape.yProperty(),
                shape.sizeProperty()
        });
        this.sizeSelect = new SimpleStringProperty("50");

    }
    public void clearSelectedShapes(){
        for(var shape : shapeObservableList){
            shape.setBorderColor(Color.YELLOW);
        }
        selectedShapes.clear();
    }

    public void collisionCheck(double x, double y) {
        for (var shape : shapeObservableList) {
            if (shape.collision(x,y))
                System.out.println("Collision!");
            selectedShapesContains(shape);
        }
    }
    public void selectedShapesContains(Shape selectedShape) {
        if (selectedShapes.contains(selectedShape)) {
            selectedShape.setBorderColor(Color.GREEN);
            selectedShapes.clear();
        } else {
            selectedShape.setBorderColor(Color.RED);
            selectedShapes.add(selectedShape);
        }
    }
    public void undoLatestChange(){
        if(undoList.isEmpty())
            return;
        undoListChange();
    }

    public void undoListChange (){
        shapeObservableList.clear();
        for (var shape : undoList.get(undoList.size() - 1))
            shapeObservableList.add(shape.copyOf());
        if(undoList.size() > 1){
            undoList.remove(undoList.size() - 1);
        }
    }
    public void addChangesToUndoList() {
        List<Shape> tempList = new ArrayList<>();
        undoList.add(tempList);
        copyToTempList(tempList);
    }
    private void copyToTempList(List<Shape> tempList) {
        shapeObservableList.forEach(shape -> tempList.add(shape.copyOf()));
    }
    public void addToShapes(Shape shape){
        if(!(shape == null))
            this.shapeObservableList.add(shape);
    }
    public void changeColorOnShape(){
        addChangesToUndoList();

        for(var shape : selectedShapes){
            shape.setColor(getSelectColorPicker());
        }
    }

    public void setCircle(){
        shapeType = ShapeType.CIRCLE;
        setSelectOption(false);
    }

    public void setRectangle(){
        shapeType = ShapeType.RECTANGLE;
        setSelectOption(false);
    }
    public void setCircle(boolean circle) {
        this.Circle.set(circle);
    }
    public ObjectProperty<Color> colorSelectProperty() {
        return selectColorPicker;
    }
    public ObservableList<Shape> getShapeObservableList() {
        return shapeObservableList;
    }
    public StringProperty sizeSelectProperty() {
        return sizeSelect;
    }
    public double getSizeText(){
        return Double.parseDouble(sizeSelect.getValue());
    }

    public BooleanProperty selectOptionProperty() {
        return selectOption;
    }
    public boolean isSelectOption() {
        return selectOption.get();
    }

    public void setSelectOption(boolean selectOption) {
        this.selectOption.set(selectOption);
    }

    public Color getSelectColorPicker() {
        return selectColorPicker.get();
    }

    public ObjectProperty<Color> selectColorPickerProperty() {
        return selectColorPicker;
    }
    public void setSelectColorPicker(Color selectColorPicker) {
        this.selectColorPicker.set(selectColorPicker);
    }

    public boolean isCircle() {
        return Circle.get();
    }

    public BooleanProperty circleProperty() {
        return Circle;
    }

    public boolean isRectangle() {
        return Rectangle.get();
    }

    public BooleanProperty rectangleProperty() {
        return Rectangle;
    }

    public ObservableList<Shape> getSelectedShape() {
        return selectedShapes;
    }
}

