package view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class AppointmentCreation {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comb_appointment_creation_patient;

    @FXML
    private Button btn_appointment_creation_confirm;

    @FXML
    private DatePicker inp_appointment_creation_date;

    @FXML
    private TextField inp_appointment_creation_desc;


    public ArrayList<String> choicesNames = new ArrayList<String>();
    public ArrayList<String> choicesIds = new ArrayList<String>();

    public String selectedId;
    @FXML
    void comb_appointment_creation_patient_clicked(ActionEvent event) {
        selectedId = choicesIds.get(choicesNames.indexOf(comb_appointment_creation_patient.getValue().toString()));

    }

    @FXML
    void initialize() throws ExecutionException, InterruptedException {
        assert comb_appointment_creation_patient != null : "fx:id=\"comb_appointment_creation_patient\" was not injected: check your FXML file 'AppointmentCreation.fxml'.";
        assert inp_appointment_creation_date != null : "fx:id=\"inp_appointment_creation_date\" was not injected: check your FXML file 'AppointmentCreation.fxml'.";
        assert inp_appointment_creation_desc != null : "fx:id=\"inp_appointment_creation_desc\" was not injected: check your FXML file 'AppointmentCreation.fxml'.";

        ApiFuture<QuerySnapshot> future =  App.fs.collection("patients").whereEqualTo("acct_id",App.currentLogIn).get();
        List<QueryDocumentSnapshot> docs = future.get().getDocuments();

        for(QueryDocumentSnapshot ds: docs) {
            String[] pat = getPatientInfo(ds.get("patient_name").toString());
            choicesNames.add(pat[1] + " " + pat[2]);
            choicesIds.add(ds.get("patient_name").toString());
        }

        for(int i = 0; i < choicesNames.size(); i++) {
            comb_appointment_creation_patient.getItems().add(choicesNames.get(i));
        }

    }

    @FXML
    void btn_appointment_creation_confirm_clicked(ActionEvent event) {
        Map<String, Object> data = new HashMap<>();
        data.put("acct_id", App.currentLogIn);
        data.put("appt_date", inp_appointment_creation_date.getValue().toString());
        data.put("appt_desc",inp_appointment_creation_desc.getText().toString());
        data.put("appt_patient",selectedId);
        data.put("appt_time","1500");
        ApiFuture<DocumentReference> future = App.fs.collection("appointments").add(data);
    }

    public String[] getPatientInfo(String pid) throws ExecutionException, InterruptedException {
        String[] result = {"","","",""};
        ApiFuture<QuerySnapshot> futurePatient =  App.fs.collection("accounts").get();
        List<QueryDocumentSnapshot> documentPatient = futurePatient.get().getDocuments();
        for (QueryDocumentSnapshot d: documentPatient) {
            if(d.getId().toString().equals(pid)) {
                result[0] = d.getId().toString();
                result[1] = d.get("acct_first").toString();
                result[2] = d.get("acct_last").toString();
                result[3] = d.get("acct_email").toString();
                break;
            };
        };
        return  result;
    }

}
