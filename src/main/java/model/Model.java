package model;

import laboration.laborationjakobjavafx.Controller;
import shapes.Base;
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

    private final ObservableList<Shape> shapeObservableList;
    private final ObservableList<Shape> selectedShapes;
    public final List<List<Shape>> reversedList = new ArrayList<>();
    private final ObjectProperty<Color> selectColorPicker;
    private final ObjectProperty<Color> borderColor;
    private ShapeType shapeType = ShapeType.CIRCLE;
    private final StringProperty sizeSelect;
    private final List<Integer> changeList;
    private final BooleanProperty selectionChoice;
    private final BooleanProperty Circle;
    private final BooleanProperty Rectangle;
    private Position position;

    public Model() {
        this.selectColorPicker = new SimpleObjectProperty<>(Color.BLACK);
        this.borderColor = new SimpleObjectProperty<>();
        this.borderColor.set(Color.TEAL);
        this.Circle = new SimpleBooleanProperty(false);
        this.selectedShapes = FXCollections.observableArrayList();
        this.changeList = new ArrayList<>();
        this.selectionChoice = new SimpleBooleanProperty();
        this.Rectangle = new SimpleBooleanProperty(false);
        this.shapeObservableList = FXCollections.observableArrayList(shape -> new Observable[]{
                shape.colorProperty(),
                shape.xProperty(),
                shape.yProperty(),
                shape.sizeProperty()
        });
        this.sizeSelect = new SimpleStringProperty("30");

    }

    public void collisionCheck(double x, double y) {
        for (var shape : shapeObservableList) {
            if (shape.collisionCheck(x,y))
                System.out.println("Collision check");
                selectedShapesContains(shape);
        }
    }

    public void changeSizeSelected(){
        updateReversedList();
        for(var shape : selectedShapes) {
            shape.setSize(getSizeText());
        }
    }


    public void revertLatest(){
        if(reversedList.isEmpty())
            return;
        revertListChange();

    }

    public void revertListChange(){
        shapeObservableList.clear();
        for (var shape : reversedList.get(reversedList.size() - 1))
            shapeObservableList.add(shape.copyList());
        if(reversedList.size() > 1){
            reversedList.remove(reversedList.size() - 1);

        }
    }
    public void updateReversedList() {
        List<Shape> tempList = new ArrayList<>();
        reversedList.add(tempList);
        addToTemporaryList(tempList);
    }
    private void addToTemporaryList(List<Shape> tempList) {
        shapeObservableList.forEach(shape -> tempList.add(shape.copyList()));
    }
    public void addToShapes(Shape shape){
        if(!(shape == null))
            this.shapeObservableList.add(shape);
            Controller.objectCount++;
    }

    public void clearSelected(){
        for(var shape : shapeObservableList){
            shape.setBorderColor(Color.RED);
        }
        selectedShapes.clear();
    }
    public void selectedShapesContains(Shape selectedShape) {
        if (selectedShapes.contains(selectedShape)) {
            selectedShape.setBorderColor(Color.BLUE);
            selectedShapes.clear();
        } else {
            selectedShape.setBorderColor(Color.GREEN);
            selectedShapes.add(selectedShape);
        }
    }

    public void changeColorOnShape(){
        updateReversedList();
        for(var shape : selectedShapes){
            shape.setColor(getSelectColorPicker());
        }
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public void setCircle(){
        shapeType = ShapeType.CIRCLE;
        setSelectionChoice(false);
    }

    public void setRectangle(){
        shapeType = ShapeType.RECTANGLE;
        setSelectionChoice(false);
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

    public BooleanProperty selectionChoiceProperty() {
        return selectionChoice;
    }
    public boolean getSelectionChoice() {
        return selectionChoice.get();
    }

    public void setSelectionChoice(boolean selectionChoice) {
        this.selectionChoice.set(selectionChoice);
    }

    public Color getSelectColorPicker() {
        return selectColorPicker.get();
    }
    public void setPoint(double mousePointX, double mousePointY) {
        this.position = new Position(mousePointX, mousePointY);
    }

    public void createShapeToList(ShapeType type) {
        Shape shape = Base.createShape(type, selectColorPicker.get(), position.getPositionX(), position.getPositionY(),getSizeText());
        shapeObservableList.add(shape);
    }

    public void setCircle(boolean circle) {
        this.Circle.set(circle);
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

