/*
    ALBERT ANG
    3/25/2024

 */
package view;

import com.google.cloud.firestore.Firestore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.FirebaseConnect;
import viewmodel.ViewModel;

public class App extends Application {
    public static Firestore fs;
    public static Stage mainStage;


    @Override
    public void start(Stage stage) throws Exception {
        // access the firebase connect object
        FirebaseConnect connection = new FirebaseConnect();
        fs = connection.connectFirebase();
        // loads the main window
        FXMLLoader fxmlL = new FXMLLoader();
        Scene win = new Scene(fxmlL.load(App.class.getResourceAsStream("/Main-Win.fxml")),677,535);
        this.mainStage = new Stage();
        this.mainStage.setTitle("RX MED APP");
        this.mainStage.setScene(win);
        this.mainStage.setResizable(false);
        this.mainStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
