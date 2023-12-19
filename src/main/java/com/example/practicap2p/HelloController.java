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
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
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

    private String username;

    private String password;

    private boolean hayServidor = false;

    private String registryURL = "rmi://localhost:6789/P2P";

    private CallbackServerInterface h;

    public HelloController() throws MalformedURLException, NotBoundException, RemoteException {
    }

    private String getNombre(){return FieldUser.getText().trim();}

    private String getPassword(){return FieldPassword.getText().trim();}
    @FXML
    void onRegisterButtonClick(ActionEvent event) throws java.rmi.RemoteException{
        try{
            //Aquí metes los datos que te pasan en los fields en la base de datos
            username = this.getNombre();
            password = this.getPassword();
            if (!hayServidor){
                this.hayServidor = true;
                this.h = (CallbackServerInterface) Naming.lookup(this.registryURL);
            }
            if (username.isEmpty() || password.isEmpty()){
                System.err.println("No se puede registrar a menos que introduzca un usuario y una contraseña");
            } else if (password.isEmpty()){
                System.err.println("No introdujo una contraseña");
            } else if (username.isEmpty()){
                System.err.println("Se ha olvidado de introducir el nombre del usuario");
            } else {
                h.registrarUsuario(username, password);
            }
        }catch (RemoteException | MalformedURLException | NotBoundException e){
        }
    }
    @FXML
    void onHelloButtonClick(ActionEvent event) throws RemoteException {
        //HelloApplication HA = new HelloApplication();
        username = this.getNombre();
        password = this.getPassword();
        int id;
        try {
            if (!hayServidor){
                this.hayServidor = true;
                this.h = (CallbackServerInterface) Naming.lookup(this.registryURL);
            }
            id = h.login(username,password);
            if (id >= 0 && !h.isUsuarioConectado(username)){
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
                Controlador.setNombre(this.getNombre());
                //AHORA HACEMOS LA CONEXIÓN DEL CLIENTE
                try {
                    ArrayList <String> solicitudes = new ArrayList<>();
                   CallbackClientInterface callbackObj = new CallbackClientImpl(getNombre(), h, id);
                   callbackObj.setFriends();
                   callbackObj.getAmigos();
                    Controlador.setCliente((CallbackClientImpl) callbackObj);
                    Controlador.setServidor(h);
                    Controlador.setId(id);
                    solicitudes = (ArrayList<String>) h.obtenerSolicitudes(id);
                    if (!solicitudes.isEmpty()){
                        for (String solicitud : solicitudes){
                            Controlador.friendRequest(solicitud);
                        }
                    }
                    ((CallbackClientImpl) callbackObj).setControlador(this.Controlador);
                    h.registerForCallback(this.getNombre(),callbackObj);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                //Sacar Object195Controller y enviarle el nombre
            }
        }catch (RemoteException | MalformedURLException | NotBoundException e){
            throw new RuntimeException(e);
        }
    }

}
