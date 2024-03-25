/*
    ALBERT ANG
    3/25/2024

 */
package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class MainWinController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_doctor_lgin;

    @FXML
    private Button btn_patient_lgin;

    @FXML
    void btn_doctor_lgin_btn(ActionEvent event) throws ExecutionException, InterruptedException, IOException {
        Scene d = new Scene(new FXMLLoader().load(App.class.getResourceAsStream("/DoctorCreation.fxml")),677,461);
        Stage nwin = new Stage();
        nwin.setTitle("RX MED APP");
        nwin.setScene(d);
        nwin.setResizable(false);
        nwin.show();
    }

    @FXML
    void btn_patient_lgin_clicked(ActionEvent event) {
        System.out.println("LOGGING IN AS PATIENT");
    }


}

