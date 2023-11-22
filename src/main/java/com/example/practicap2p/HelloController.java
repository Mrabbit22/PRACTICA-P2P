package com.example.practicap2p;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

//C:\Users\ldiaz\OneDrive\Escritorio\UNI AÑO 3\CODIS\PRACTICA-CALLBACK
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
        File file = new File("C:\\Users\\ldiaz\\OneDrive\\Escritorio\\UNI AÑO 3\\CODIS\\PRACTICA-CALLBACK\\usuarios.txt");
        HelloApplication HA = new HelloApplication();
        String palabras2 = file.toString();
        String[] palabras = file.toString().split(" ");//Dividirlo en líneas
        //No
        System.out.println(palabras2);// --> Imprime el PATH
        try {
            HA.changeScene("Object195.fxml");
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
