module RxMedApp {
    requires javafx.controls;
    requires javafx.fxml;

    opens view to javafx.fxml;
    exports view;

    opens model;
    exports model;

    opens viewmodel;
    exports viewmodel;

}