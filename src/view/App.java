package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlL = new FXMLLoader();
        Scene win = new Scene(fxmlL.load(App.class.getResourceAsStream("/test.fxml")),300,400);
        stage.setTitle("RX MED APP");
        stage.setScene(win);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
