package view;

import com.google.cloud.firestore.Firestore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.FirebaseConnect;

public class App extends Application {
    public static Firestore fs;


    @Override
    public void start(Stage stage) throws Exception {
        // access the firebase connect object
        FirebaseConnect connection = new FirebaseConnect();
        fs = connection.connectFirebase();
        // loads the main window
        FXMLLoader fxmlL = new FXMLLoader();
        Scene win = new Scene(fxmlL.load(App.class.getResourceAsStream("/Main-Win.fxml")),677,535);
        stage.setTitle("RX MED APP");
        stage.setScene(win);
        stage.setResizable(false);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}
