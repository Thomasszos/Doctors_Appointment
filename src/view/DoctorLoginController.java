package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

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
    void btn_doctor_login_clicked(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btn_doctor_login != null : "fx:id=\"btn_doctor_login\" was not injected: check your FXML file 'DoctorLogin.fxml'.";
        assert inp_doctor_login_email != null : "fx:id=\"inp_doctor_login_email\" was not injected: check your FXML file 'DoctorLogin.fxml'.";
        assert inp_doctor_login_password != null : "fx:id=\"inp_doctor_login_password\" was not injected: check your FXML file 'DoctorLogin.fxml'.";

    }
}
