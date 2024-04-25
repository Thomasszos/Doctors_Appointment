package view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointments;
import model.Patients;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class DoctorMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_doctor_add_appointment;

    @FXML
    private Button btn_doctor_add_patients;

    @FXML
    private TableView<Appointments> tbl_current_consultations;

    @FXML
    private TableView<Patients> tbl_current_patients;

    @FXML
    void initialize() {
        assert btn_doctor_add_appointment != null : "fx:id=\"btn_doctor_add_appointment\" was not injected: check your FXML file 'DoctorMenu.fxml'.";
        assert btn_doctor_add_patients != null : "fx:id=\"btn_doctor_add_patients\" was not injected: check your FXML file 'DoctorMenu.fxml'.";
        assert tbl_current_consultations != null : "fx:id=\"tbl_current_consultations\" was not injected: check your FXML file 'DoctorMenu.fxml'.";
        assert tbl_current_patients != null : "fx:id=\"tbl_current_patients\" was not injected: check your FXML file 'DoctorMenu.fxml'.";


        tbl_current_consultations.getItems().clear();
        tbl_current_consultations.getColumns().clear();
        tbl_current_patients.getItems().clear();
        tbl_current_patients.getColumns().clear();


        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future =  App.fs.collection("patients").whereEqualTo("acct_id",App.currentLogIn).get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try{
            // patients
            TableColumn<Patients ,String> fcol = new TableColumn<>("PATIENT ID");
            fcol.setCellValueFactory(new PropertyValueFactory<>("patientId"));

            TableColumn<Patients ,String> scol = new TableColumn<>("PATIENT FIRST");
            scol.setCellValueFactory(new PropertyValueFactory<>("patientFirst"));

            TableColumn<Patients ,String> tcol = new TableColumn<>("PATIENT LAST");
            tcol.setCellValueFactory(new PropertyValueFactory<>("patientLast"));

            TableColumn<Patients ,String> lcol = new TableColumn<>("PATIENT EMAIL");
            tcol.setCellValueFactory(new PropertyValueFactory<>("patientEmail"));

            //appointments
            TableColumn<Appointments ,String> pfcol = new TableColumn<>("APPOINTMENT ID");
            fcol.setCellValueFactory(new PropertyValueFactory<>("patientId"));

            TableColumn<Patients ,String> pscol = new TableColumn<>("PATIENT FIRST");
            scol.setCellValueFactory(new PropertyValueFactory<>("patientFirst"));

            TableColumn<Patients ,String> ptcol = new TableColumn<>("PATIENT LAST");
            tcol.setCellValueFactory(new PropertyValueFactory<>("patientLast"));

            TableColumn<Patients ,String> plcol = new TableColumn<>("PATIENT EMAIL");
            tcol.setCellValueFactory(new PropertyValueFactory<>("patientEmail"));


                tbl_current_patients.getColumns().add(fcol);
                tbl_current_patients.getColumns().add(scol);
                tbl_current_patients.getColumns().add(tcol);
                tbl_current_patients.getColumns().add(lcol);

            documents = future.get().getDocuments();

            if(documents.size() > 0) {
                for (QueryDocumentSnapshot document : documents)
                {

                    String[] pat = getPatientInfo(document.get("patient_name").toString());
                    Patients p = new Patients(pat[0],pat[1],pat[2],pat[3]);
                            //getDoctor(document.get("acct_id").toString());
                    //Appointments appt  = new Appointments(document.getId().toString(),doctor,document.get("appt_date").toString());
                    tbl_current_patients.getItems().add(p);
                    //tbl_patient_appointment.getItems().add(appt);
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

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
