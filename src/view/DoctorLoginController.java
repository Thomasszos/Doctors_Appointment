package view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class DoctorLoginController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_doctor_login;

    @FXML
    private TextField inp_doctor_login_email;

    @FXML
    private TextField inp_doctor_login_password;

    @FXML
    void btn_doctor_login_clicked(ActionEvent event) throws ExecutionException, InterruptedException, IOException {
        CollectionReference acc = App.fs.collection("accounts");
        Query logInQ = acc.whereEqualTo("acct_email",inp_doctor_login_email.getText().toString())
                .whereEqualTo("acct_pass",inp_doctor_login_password.getText().toString()).whereEqualTo("acct_type","D");
        ApiFuture<QuerySnapshot> results = logInQ.get();
        if(results.get().getDocuments().size() >0) {
            App.currentLogIn = results.get().getDocuments().get(0).getId().toString();
            App.mainStage.hide();
            Scene d = new Scene(new FXMLLoader().load(App.class.getResourceAsStream("/DoctorMenu.fxml")),887,636);
            Stage nwin = new Stage();
            nwin.setTitle("RX MED APP");
            nwin.setScene(d);
            nwin.setResizable(false);
            nwin.show();
        }else{
            System.out.println("INVALID INPUTS");
        }
    }

    @FXML
    void initialize() {
        assert btn_doctor_login != null : "fx:id=\"btn_doctor_login\" was not injected: check your FXML file 'DoctorLogin.fxml'.";
        assert inp_doctor_login_email != null : "fx:id=\"inp_doctor_login_email\" was not injected: check your FXML file 'DoctorLogin.fxml'.";
        assert inp_doctor_login_password != null : "fx:id=\"inp_doctor_login_password\" was not injected: check your FXML file 'DoctorLogin.fxml'.";

    }
}
