module com.example.practicap2p {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;


    opens com.example.practicap2p to javafx.fxml;
    exports com.example.practicap2p;
}