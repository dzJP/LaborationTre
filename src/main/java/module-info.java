module laboration.laborationjakobjavafx {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens laboration.laborationjakobjavafx to javafx.fxml;
    exports laboration.laborationjakobjavafx;
    exports model;
    opens model to javafx.fxml;
}