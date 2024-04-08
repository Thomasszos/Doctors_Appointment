package view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

public class PatientCreationController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField inp_create_patient_day;

    @FXML
    private TextField inp_create_patient_email;

    @FXML
    private TextField inp_create_patient_fname;

    @FXML
    private TextField inp_create_patient_lname;

    @FXML
    private TextField inp_create_patient_month;

    @FXML
    private PasswordField inp_create_patient_password;

    @FXML
    private Button inp_create_patient_submit;

    @FXML
    private TextField inp_create_patient_year;

    @FXML
    void inp_create_patient_submit_clicked(ActionEvent event) {
        registerAPatient();
    }

    @FXML
    void initialize() {
        assert inp_create_patient_day != null : "fx:id=\"inp_create_patient_day\" was not injected: check your FXML file 'PatientCreation.fxml'.";
        assert inp_create_patient_email != null : "fx:id=\"inp_create_patient_email\" was not injected: check your FXML file 'PatientCreation.fxml'.";
        assert inp_create_patient_fname != null : "fx:id=\"inp_create_patient_fname\" was not injected: check your FXML file 'PatientCreation.fxml'.";
        assert inp_create_patient_lname != null : "fx:id=\"inp_create_patient_lname\" was not injected: check your FXML file 'PatientCreation.fxml'.";
        assert inp_create_patient_month != null : "fx:id=\"inp_create_patient_month\" was not injected: check your FXML file 'PatientCreation.fxml'.";
        assert inp_create_patient_password != null : "fx:id=\"inp_create_patient_password\" was not injected: check your FXML file 'PatientCreation.fxml'.";
        assert inp_create_patient_submit != null : "fx:id=\"inp_create_patient_submit\" was not injected: check your FXML file 'PatientCreation.fxml'.";
        assert inp_create_patient_year != null : "fx:id=\"inp_create_patient_year\" was not injected: check your FXML file 'PatientCreation.fxml'.";

    }

    public void registerAPatient(){
        DocumentReference docRef = App.fs.collection("accounts").document(UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("acct_email", inp_create_patient_email.getText().toString());
        data.put("acct_first", inp_create_patient_fname.getText().toString());
        data.put("acct_last", inp_create_patient_lname.getText().toString());
        data.put("acct_pass",inp_create_patient_password.getText().toString());
        data.put("acct_type","P");
        data.put("acct_user",inp_create_patient_lname.getText().toString().toLowerCase()
                +""+inp_create_patient_fname.getText().toString().toLowerCase().charAt(0));
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("ADDED TO THE DATABASE\nPlease click read to view data");

        alert.showAndWait();
    }
}
