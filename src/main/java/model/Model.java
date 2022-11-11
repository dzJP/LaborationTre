package model;

import Shapes.FactoryShapes;
import Shapes.Shape;
import Shapes.ShapeType;
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
    private Point point;
    private final ObservableList<Shape> shapeObservableList;
    private ObservableList<Shape> selectedShapes;
    public final List<List<Shape>> undoLists = new ArrayList<>();


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
        this.point = new Point(mousePointX, mousePointY);
    }

    public void changeSizeOnSelectedShape (){
        addChangesToUndoList();
        for(var shape : selectedShapes) {
            shape.setSize(getSizeText());
        }
    }


    public void createShapeToList(ShapeType type) {
        Shape shape = FactoryShapes.createShape(type, selectColorPicker.get(),point.getPosX(),point.getPosY(),getSizeText());
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

    public void undoLatestChange(){
        if(undoLists.isEmpty())
            return;
        undoListChange();
    }

    public void undoListChange (){
        shapeObservableList.clear();
        for (var shape : undoLists.get(undoLists.size() - 1))
            shapeObservableList.add(shape.copyOf());
        if(undoLists.size() > 1){
            undoLists.remove(undoLists.size() - 1);
        }
    }
    public void addChangesToUndoList() {
        List<Shape> tempList = new ArrayList<>();
        undoLists.add(tempList);
        copyToTempList(tempList);

    }

    private void copyToTempList(List<Shape> tempList) {
        shapeObservableList.forEach(shape -> tempList.add(shape.copyOf()));
    }

    public void addToShapes(Shape shape){
        if(!(shape == null))
            this.shapeObservableList.add(shape);
    }

    public void setCircle(){
        shapeType = ShapeType.CIRCLE;
        setSelectOption(false);
    }

    public void setRectangle(){
        shapeType = ShapeType.RECTANGLE;
        setSelectOption(false);
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

    public void clearSelectedShapes(){
        for(var shape : shapeObservableList){
            shape.setBorderColor(Color.YELLOW);
        }
        selectedShapes.clear();
    }

    public Color getBorderColor() {
        return borderColor.get();
    }

    public ObjectProperty<Color> borderColorProperty() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor.set(borderColor);
    }

    public ObjectProperty<Color> colorSelectProperty() {
        return selectColorPicker;
    }


    public ObservableList<Shape> getShapeObservableList() {
        return shapeObservableList;
    }

    public void setCircle(boolean circle) {
        this.Circle.set(circle);
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

    public void checkIfInsideShape(double x, double y) {
        for (var shape : shapeObservableList) {
            if (shape.isInsideShape(x,y))
                System.out.println("inside");
            selectedShapesContains(shape);
        }
    }
    public void selectedShapesContains(Shape selectedShape) {
        if (selectedShapes.contains(selectedShape)) {
            selectedShape.setBorderColor(Color.GREEN);
            selectedShapes.remove(selectedShapes);
        } else {
            selectedShape.setBorderColor(Color.RED);
            selectedShapes.add(selectedShape);
        }
    }
    public void changeColorOnShape(){
        addChangesToUndoList();

        for(var shape : selectedShapes){
            shape.setColor(getSelectColorPicker());
        }
    }

}

