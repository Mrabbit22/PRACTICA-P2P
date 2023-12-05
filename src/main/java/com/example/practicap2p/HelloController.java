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

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
    public String getContrasena(){return FieldPassword.getText();}
    @FXML
    void onHelloButtonClick(ActionEvent event) throws RemoteException {
        //HelloApplication HA = new HelloApplication();
        String registryURL = "rmi://localhost:6789/P2P";
        CallbackServerInterface h;
        try {
            h = (CallbackServerInterface) Naming.lookup(registryURL);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            throw new RuntimeException(e);
        }
        String[] listaamigos;
        try {
            listaamigos = h.login(this.getNombre(),this.getContrasena());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if (listaamigos!=null) {
            try {
                //HA.changeScene("Object195.fxml");--> Esto ahora lo voy a hacer yo
                this.stg = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Object195.fxml"));
                Parent root = fxmlLoader.load();
                Scene Escena = new Scene(root, 540, 440);
                this.stg.setTitle("Chat de " + this.getNombre());

                this.stg.setScene(Escena);
                this.stg.show();
                //System.out.println(this.getNombre());
                this.Controlador = fxmlLoader.getController();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            //Al hacer la conexión, modificar para que se pasen solo los amigos
            //Un arraylist, que mire los nombres en ambos clientes
            //O un hashmap con clave usuarios y contenido amigos

            Controlador.setNombre(this.getNombre());

            //AHORA HACEMOS LA CONEXIÓN DEL CLIENTE
            try {
                CallbackClientInterface callbackObj = new CallbackClientImpl();
                callbackObj.setNombre(this.getNombre());
                //CallbackServerInterface h = (CallbackServerInterface) Naming.lookup(registryURL);
                callbackObj.setControlador(Controlador);
                h.registerForCallback(this.getNombre(), callbackObj, listaamigos);
                Controlador.setCliente((CallbackClientImpl) callbackObj);
                Controlador.setServidor(h);
                Controlador.updateFriendLista();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            //Sacar Object195Controller y enviarle el nombre
        }
    }
}
