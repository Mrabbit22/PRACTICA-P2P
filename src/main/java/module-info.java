module com.example.practicap2p {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.practicap2p to javafx.fxml;
    exports com.example.practicap2p;
}