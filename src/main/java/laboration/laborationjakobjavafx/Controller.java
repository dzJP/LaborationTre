package laboration.laborationjakobjavafx;

import javafx.beans.Observable;
import shapes.Shape;
import shapes.FactoryShapes;
import shapes.ShapeType;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Model;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class Controller {
    final static int CANVAS_WIDTH = 800;
    final static int CANVAS_HEIGHT = 800;
    @FXML
    private final Model model;
    @FXML
    private Canvas canvas;
    @FXML
    private GraphicsContext context;
    @FXML
    private final BooleanProperty selectOption;
    @FXML
    private ColorPicker cp;
    @FXML
    private TextField changeShapeSize;
    @FXML
    private Text status;
    @FXML
    public Button circleButton;
    @FXML
    public Button rectangleButton;
    @FXML
    public Button pointer;
    @FXML
    public Button undoButton;
    @FXML
    public Button clearCanvasButton;
    @FXML
    public CheckBox selectionEditor;
    @FXML
    public FactoryShapes factory;
    @FXML
    public BooleanProperty circle;
    @FXML
    public BooleanProperty rectangle;

    public Controller() {
        this.rectangle = new SimpleBooleanProperty();
        this.circle = new SimpleBooleanProperty();
        this.selectOption = new SimpleBooleanProperty();
        this.changeShapeSize = new TextField();
        this.model = new Model();
        this.factory = new FactoryShapes();
    }

    public void init(Stage stage) {
        context = canvas.getGraphicsContext2D();
        cp.valueProperty().bindBidirectional(model.colorSelectProperty());
        selectionEditor.selectedProperty().bindBidirectional(model.selectOptionProperty());
        changeShapeSize.textProperty().bindBidirectional(model.sizeSelectProperty());
        selectOption.bindBidirectional(model.selectOptionProperty());
        model.getShapeObservableList().addListener((ListChangeListener<Shape>) e -> drawShapes(context));
        model.addChangesToUndoList();
    }

    public void displayPosition(MouseEvent event) {
        status.setText("X = " + event.getSceneX() + ". Y = " + event.getSceneY() + ".");
    }

    public void onCanvasClicked(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        if (model.isSelectOption()) {
            model.collisionCheck(x, y);
        } else {
            selectOrCreateShape(x, y);
        }
    }

    public void clearCanvas() {
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    public void onClearCanvasClick(ActionEvent event) {
        if (event.getSource().equals(clearCanvasButton)) {
            clearCanvas();
        }
    }

    private void drawShapes(GraphicsContext context) {
        clearCanvas();
        for (var shape : model.getShapeObservableList()) {
            shape.draw(context);
        }
    }

    private Shape returnNewShape(ShapeType type, Color colorPick, double x, double y, double size) {
        return FactoryShapes.createShape(type, colorPick, x, y, size);
    }

    public void actionExit(ActionEvent event) {
        Platform.exit();
    }

    public void actionRectangle() {
        model.setRectangle();
    }

    public void onCircleClick() {
        model.setCircle();
    }

    public void onPointSelection() {
        model.setSelectOption(true);
    }

    private void selectOrCreateShape(double x, double y) {
        if (selectOption.get())
            model.collisionCheck(x, y);
        else
            createAndAddNewShape(x, y);
    }

    private void createAndAddNewShape(double x, double y) {
        var newShape = returnNewShape(model.getShapeType(), cp.getValue(), x, y, model.getSizeText());
        model.addToShapes(newShape);
        model.addChangesToUndoList();
    }

    public void undoAction() {
        model.undoLatestChange();
    }

    @FXML
    public void handleClickSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File Application");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );
        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT);
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error");
            }
            System.out.println(file.getPath());
        } else {
            System.out.println("Cancelled");
        }
    }

    public void onChangeSize() {
        model.changeSizeOnSelectedShape();
    }

    public void onSelectorAction() {
        model.clearSelectedShapes();
    }

    public void OnChangeColor() {
        model.changeColorOnShape();
    }


}