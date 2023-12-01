package com.example.practicap2p;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

//C:\Users\ldiaz\OneDrive\Escritorio\UNI AÑO 3\CODIS\PRACTICA-CALLBACK
public class HelloController {

    private Stage stg;
    @FXML
    private Button ButtonLogIn;

    private Object195Controller Controlador;

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
    private Button Register;
    @FXML
    void onRegisterButtonClick(ActionEvent event) {
        //Aquí metes los datos que te pasan en los fields en la base de datos
    }
    public String getNombre(){return FieldUser.getText();}
    @FXML
    void onHelloButtonClick(ActionEvent event) {
        //HelloApplication HA = new HelloApplication();
        try {
            //HA.changeScene("Object195.fxml");--> Esto ahora lo voy a hacer yo
            this.stg = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Object195.fxml"));
            Scene Escena = new Scene(fxmlLoader.load(), 540, 440);
            this.stg.setTitle("Titulo");

            this.stg.setScene(Escena);
            this.stg.show();
            //System.out.println(this.getNombre());
            this.Controlador = fxmlLoader.getController();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Controlador.setNombre(this.getNombre());
        //Sacar Object195Controller y enviarle el nombre
    }

}
