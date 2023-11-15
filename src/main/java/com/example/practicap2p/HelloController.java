package com.example.practicap2p;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class HelloController {

    @FXML
    private Button ButtonLogIn;

    @FXML
    private PasswordField FieldPassword;

    @FXML
    private TextField FieldUser;

    @FXML
    private Text PASSWORD;

    @FXML
    private Text USER;

    @FXML
    private Label welcomeText;

    @FXML
    void onHelloButtonClick(ActionEvent event) {
        //System.out.println("Usuario : " + FieldUser.getText());
        //System.out.println("Contraseña : " + FieldPassword.getText());
        HelloApplication HA = new HelloApplication();
        try {
            HA.changeScene("Object195.fxml");
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
