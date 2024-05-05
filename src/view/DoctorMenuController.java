package view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Appointments;
import model.Patients;

import java.io.IOException;
import java.net.URL;
import java.util.*;
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
    private Button btn_doctor_refresh_appointment;

    ArrayList<String> patientsIds = new ArrayList<String>();

    @FXML
    void btn_doctor_refresh_appointment_clicked(ActionEvent event) {
        refreshTable();
    }

    @FXML
    void btn_doctor_add_appointment_clicked(ActionEvent event) throws ExecutionException, InterruptedException, IOException {
        Scene d = new Scene(new FXMLLoader().load(App.class.getResourceAsStream("/AppointmentCreation.fxml")),600,400);
        Stage nwin = new Stage();
        nwin.setTitle("RX MED APP");
        nwin.setScene(d);
        nwin.setResizable(false);
        nwin.show();
    }

    @FXML
    void btn_doctor_add_patients_clicked(ActionEvent event) throws ExecutionException, InterruptedException {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("RXMED");
        dialog.setHeaderText("Message:");
        dialog.setContentText("Please enter the account id of the patient:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            addToPatients(result.get());
        }
    }


    @FXML
    void tbl_current_consultations_pressed(KeyEvent event) {
        if(event.getCode() == KeyCode.D) {

        }
    }

    @FXML
    void tbl_current_patients_pressed(KeyEvent event) {
        if(event.getCode() == KeyCode.D) {
            Patients selectedP = tbl_current_patients.getSelectionModel().getSelectedItem();
        }
    }

    private void addToPatients(String s) throws ExecutionException, InterruptedException {
        int valid = 0;
        ApiFuture<QuerySnapshot> futures =  App.fs.collection("accounts").get();
        List<QueryDocumentSnapshot> documents;
        documents = futures.get().getDocuments();
        for(QueryDocumentSnapshot d: documents) {
            if(d.getId().equals(s)) {
                valid = 1;
                break;
            }
        }


        if(valid == 1) {
            Map<String, Object> data = new HashMap<>();
            data.put("acct_id", App.currentLogIn);
            data.put("patient_name", s );
            // Add a new document with a generated ID
            ApiFuture<DocumentReference> future = App.fs.collection("patients").add(data);
            try {
                // Wait for the result
                DocumentReference documentReference = future.get();
                System.out.println("Document added with ID: " + documentReference.getId());
                refreshTable();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("RXMED");
            alert.setHeaderText("I'M SORRY");
            alert.setContentText("Invalid ID");
            alert.showAndWait();
        }


    }

    public void refreshTable() {
        tbl_current_consultations.getItems().clear();
        tbl_current_consultations.getColumns().clear();
        tbl_current_patients.getItems().clear();
        tbl_current_patients.getColumns().clear();


        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future =  App.fs.collection("patients").whereEqualTo("acct_id",App.currentLogIn).get();
        ApiFuture<QuerySnapshot> futureAppointment =  App.fs.collection("appointments").whereEqualTo("acct_id",App.currentLogIn).get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        List<QueryDocumentSnapshot> documentsAppointment;
        try{
            // patients
            TableColumn<Patients ,String> fcol = new TableColumn<>("PATIENT ID");
            fcol.setCellValueFactory(new PropertyValueFactory<>("patientId"));

            TableColumn<Patients ,String> scol = new TableColumn<>("PATIENT FIRST");
            scol.setCellValueFactory(new PropertyValueFactory<>("patientFirst"));

            TableColumn<Patients ,String> tcol = new TableColumn<>("PATIENT LAST");
            tcol.setCellValueFactory(new PropertyValueFactory<>("patientLast"));

            TableColumn<Patients ,String> lcol = new TableColumn<>("PATIENT EMAIL");
            lcol.setCellValueFactory(new PropertyValueFactory<>("patientEmail"));

//            //appointments
            TableColumn<Appointments,String> afcol = new TableColumn<>("APPOINTMENT ID");
            afcol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));

            TableColumn<Appointments,String> ascol = new TableColumn<>("PATIENT NAME");
            ascol.setCellValueFactory(new PropertyValueFactory<>("appointmentPatient"));

            TableColumn<Appointments,String> atcol = new TableColumn<>("APPOINTMENT DATE");
            atcol.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));

            tbl_current_patients.getColumns().add(fcol);
            tbl_current_patients.getColumns().add(scol);
            tbl_current_patients.getColumns().add(tcol);
            tbl_current_patients.getColumns().add(lcol);

            tbl_current_consultations.getColumns().add(afcol);
            tbl_current_consultations.getColumns().add(ascol);
            tbl_current_consultations.getColumns().add(atcol);

            documents = future.get().getDocuments();
            documentsAppointment = futureAppointment.get().getDocuments();

            if(documents.size() > 0) {
                for (QueryDocumentSnapshot document : documents)
                {

                    String[] pat = getPatientInfo(document.get("patient_name").toString());
                    Patients p = new Patients(pat[0],pat[1],pat[2],pat[3]);
                    //getDoctor(document.get("acct_id").toString());
                    //Appointments appt  = new Appointments(document.getId().toString(),doctor,document.get("appt_date").toString());
                    tbl_current_patients.getItems().add(p);
                    patientsIds.add(document.getId());
                    //tbl_patient_appointment.getItems().add(appt);
                }
            }

            if(documentsAppointment.size() > 0) {
                for (QueryDocumentSnapshot document : documentsAppointment)
                {

                    String[] pat = getPatientInfo(document.get("appt_patient").toString());
                    String name = pat[1] + " " + pat[2];
                    Appointments ap = new Appointments(document.getId(),document.getString("acct_id").toString(),name,document.getString("appt_date").toString());
                    tbl_current_consultations.getItems().add(ap);
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void initialize() {
        assert btn_doctor_add_appointment != null : "fx:id=\"btn_doctor_add_appointment\" was not injected: check your FXML file 'DoctorMenu.fxml'.";
        assert btn_doctor_add_patients != null : "fx:id=\"btn_doctor_add_patients\" was not injected: check your FXML file 'DoctorMenu.fxml'.";
        assert tbl_current_consultations != null : "fx:id=\"tbl_current_consultations\" was not injected: check your FXML file 'DoctorMenu.fxml'.";
        assert tbl_current_patients != null : "fx:id=\"tbl_current_patients\" was not injected: check your FXML file 'DoctorMenu.fxml'.";

        refreshTable();


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
