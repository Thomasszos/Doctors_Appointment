package view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class PatientLogInController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_patient_create;

    @FXML
    private Button btn_patient_forgot;

    @FXML
    private Button btn_patient_login;

    @FXML
    private TextField inp_patient_email;

    @FXML
    private TextField inp_patient_password;

    @FXML
    void btn_patient_create_clicked(ActionEvent event) {

    }

    @FXML
    void btn_patient_forgot_clicked(ActionEvent event) {

    }

    @FXML
    void btn_patient_login_clicked(ActionEvent event) throws ExecutionException, InterruptedException {
        CollectionReference acc = App.fs.collection("accounts");
        Query logInQ = acc.whereEqualTo("acct_email",inp_patient_email.getText().toString())
                .whereEqualTo("acct_pass",inp_patient_password.getText().toString());
        ApiFuture<QuerySnapshot> results = logInQ.get();
        if(results.get().getDocuments().size() >0) {
            System.out.println("SUCCESS");
        }

    }

    @FXML
    void initialize() {
        assert btn_patient_create != null : "fx:id=\"btn_patient_create\" was not injected: check your FXML file 'PatientLogin.fxml'.";
        assert btn_patient_forgot != null : "fx:id=\"btn_patient_forgot\" was not injected: check your FXML file 'PatientLogin.fxml'.";
        assert btn_patient_login != null : "fx:id=\"btn_patient_login\" was not injected: check your FXML file 'PatientLogin.fxml'.";
        assert inp_patient_email != null : "fx:id=\"inp_patient_email\" was not injected: check your FXML file 'PatientLogin.fxml'.";
        assert inp_patient_password != null : "fx:id=\"inp_patient_password\" was not injected: check your FXML file 'PatientLogin.fxml'.";

    }


}
