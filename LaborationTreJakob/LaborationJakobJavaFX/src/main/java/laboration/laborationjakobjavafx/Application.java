package laboration.laborationjakobjavafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("canvas-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Laboration 3 - Jakob");
        stage.setScene(scene);
        Controller controller = fxmlLoader.getController();
        controller.init(stage);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}