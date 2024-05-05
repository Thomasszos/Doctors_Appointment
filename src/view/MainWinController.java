/*
    ALBERT ANG
    3/25/2024

 */
package view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
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
        App.mainStage.hide();
        Scene d = new Scene(new FXMLLoader().load(App.class.getResourceAsStream("/DoctorLogin.fxml")),600,400);
        Stage nwin = new Stage();
        nwin.setTitle("RX MED APP");
        nwin.setScene(d);
        nwin.setResizable(false);
        nwin.show();
    }

    @FXML
    void btn_patient_lgin_clicked(ActionEvent event) throws ExecutionException, InterruptedException, IOException {
        App.mainStage.hide();
        Scene d = new Scene(new FXMLLoader().load(App.class.getResourceAsStream("/PatientLogin.fxml")),600,400);
        Stage nwin = new Stage();
        nwin.setTitle("RX MED APP");
        nwin.setScene(d);
        nwin.setResizable(false);
        nwin.show();
    }

    public void getAllAppointmentsOf(String documentID) throws ExecutionException, InterruptedException {
        CollectionReference acc = App.fs.collection("accounts");
        Query d = acc.whereEqualTo("acct_id",documentID);
        ApiFuture<QuerySnapshot> querySnapshot = d.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            System.out.println(document.getId());
        }
    }



}

