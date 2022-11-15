package laboration.laborationjakobjavafx;

import javafx.scene.control.*;
import shapes.Shape;
import shapes.Base;
import shapes.ShapeType;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
//import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    final static int CANVAS_HEIGHT = 600;
    public static int objectCount = 0;
    @FXML
    private Text count;
    @FXML
    private final Model model;
    @FXML
    public MenuItem exitOption;
    @FXML
    public MenuItem SaveOption;
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
    public Button selectMode;
    @FXML
    public Button undoButton;
    @FXML
    private Button clearCanvasButton;
    @FXML
    public CheckBox selectionEditor;
    @FXML
    public Base base;
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
        this.base = new Base();
    }

    public void init(Stage stage) {
        context = canvas.getGraphicsContext2D();
        cp.valueProperty().bindBidirectional(model.colorSelectProperty());
        selectionEditor.selectedProperty().bindBidirectional(model.selectionChoiceProperty());
        changeShapeSize.textProperty().bindBidirectional(model.sizeSelectProperty());
        selectOption.bindBidirectional(model.selectionChoiceProperty());
        model.getShapeObservableList().addListener((ListChangeListener<Shape>) e -> drawShapesOnClick(context));
        model.updateReversedList();

        clearCanvasButton.setOnAction(
                (event) -> {
                clearCanvas(canvas);
                objectCount = 0;
                count.setText(String.valueOf(objectCount));
                }
        );
    }

//    @FXML
//    public void handleClickSave() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Save File Application");
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("png files (*.png)", "*.png"),
//                new FileChooser.ExtensionFilter("All files", "*.*")
//        );
//        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
//        if (file != null) {
//            try {
//                WritableImage writableImage = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT);
//                canvas.snapshot(null, writableImage);
//                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
//                ImageIO.write(renderedImage, "png", file);
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.out.println("Error");
//            }
//            System.out.println(file.getPath());
//        } else {
//            System.out.println("Cancelled");
//        }
//    }

    public void displayPosition(MouseEvent event) {
        status.setText("X = " + event.getSceneX() + ". Y = " + event.getSceneY() + ".");
    }

    public void onCanvasClicked(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        if (model.getSelectionChoice()) {
            model.collisionCheck(x, y);
        } else {
            selectOrCreateShape(x, y);
        }
    }

    public void clearCanvas(Canvas canvas) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.WHITE);
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    }
    public void onClearCanvasClick(ActionEvent event) {
        if (event.getSource().equals(clearCanvasButton)) {
            clearCanvas(canvas);
            System.out.println("Clicked on Clear Canvas button");
        }
    }

    private void drawShapesOnClick(GraphicsContext context) {
            clearCanvas(canvas);
        for (var shape : model.getShapeObservableList()) {
            shape.draw(context);
        }
    }

    private Shape returnNewShape(ShapeType type, Color colorPick, double x, double y, double size) {
        return Base.createShape(type, colorPick, x, y, size);
    }

    public void actionExit(ActionEvent event) {
        if (event.getSource().equals(exitOption)) {
            Platform.exit();
            System.out.println("Exiting program..");
        }
    }

    public void actionRectangle() {
        model.setRectangle();
    }

    public void onCircleClick() {
        model.setCircle();
    }

    public void onSelectClick() {
        model.setSelectionChoice(true);
        System.out.println("Entered Select Mode");
    }

    private void selectOrCreateShape(double x, double y) {
        if (selectOption.get())
            model.collisionCheck(x, y);
        else
            addNewCreatedShape(x, y);
    }

    private void addNewCreatedShape(double x, double y) {
        var newShape = returnNewShape(model.getShapeType(), cp.getValue(), x, y, model.getSizeText());
        model.addToShapes(newShape);
        model.updateReversedList();
        count.setText(String.valueOf(objectCount));
    }

    public void revertEvent() {
        model.revertLatest();
        System.out.println("Clicked on Undo button");
    }
    public void onChangeSize() {
        model.changeSizeSelected();
    }

    public void onSelectMode() {
        model.clearSelected();
    }

    public void onChangeColor() {
        model.changeColorOnShape();
    }

}