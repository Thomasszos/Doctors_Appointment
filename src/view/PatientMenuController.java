package view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointments;
import viewmodel.AppointmentViewModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class PatientMenuController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_patient_refresh;

    @FXML
    private TableView<Appointments> tbl_patient_appointment;

    @FXML
    void btn_patient_refresh_clicked(ActionEvent event) {
        tbl_patient_appointment.getItems().clear();
        tbl_patient_appointment.getColumns().clear();
        tbl_patient_appointment.refresh();
    }

    @FXML
    void initialize() {
        assert btn_patient_refresh != null : "fx:id=\"btn_patient_refresh\" was not injected: check your FXML file 'PatientMenu.fxml'.";
        assert tbl_patient_appointment != null : "fx:id=\"tbl_patient_appointment\" was not injected: check your FXML file 'PatientMenu.fxml'.";

        // clear everything inside the table
        tbl_patient_appointment.getItems().clear();
        tbl_patient_appointment.getColumns().clear();
        tbl_patient_appointment.refresh();

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future =  App.fs.collection("appointments").whereEqualTo("appt_patient",App.currentLogIn).get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try{
            TableColumn<Appointments,String> fcol = new TableColumn<>("APPOINTMENT ID");
            fcol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));

            TableColumn<Appointments,String> scol = new TableColumn<>("DOCTOR NAME");
            scol.setCellValueFactory(new PropertyValueFactory<>("appointmentDoctor"));

            TableColumn<Appointments,String> tcol = new TableColumn<>("APPOINTMENT DATE");
            tcol.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));


            tbl_patient_appointment.getColumns().add(fcol);
            tbl_patient_appointment.getColumns().add(scol);
            tbl_patient_appointment.getColumns().add(tcol);

            documents = future.get().getDocuments();

            if(documents.size() > 0) {
                for (QueryDocumentSnapshot document : documents)
                {
                    String doctor = getDoctor(document.get("acct_id").toString());
                    Appointments appt  = new Appointments(document.getId().toString(),doctor,document.get("appt_date").toString());
                    tbl_patient_appointment.getItems().add(appt);
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }



    }

    public String getDoctor(String did) throws ExecutionException, InterruptedException {
        String result = "";
        ApiFuture<QuerySnapshot> futureDoctor =  App.fs.collection("accounts").get();
        List<QueryDocumentSnapshot> documentDoctor = futureDoctor.get().getDocuments();
        for (QueryDocumentSnapshot d: documentDoctor) {
            if(d.getId().toString().equals(did)) {
                result = d.get("acct_first").toString();
                break;
            };
        };
      return result;
    };
}
