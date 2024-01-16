package com.example.practicap2p;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.concurrent.*;


import java.rmi.RemoteException;

public class solicitarPasswordController {

    @FXML
    private Button introducir;

    @FXML
    private Button cerrar;

    @FXML
    private TextField espacioContrasena;

    @FXML
    private Label contrasenaMala;

    private String contrasena;
    private String NuevaContr;
    private String nombre;
    private CallbackServerInterface servidor;
    private Object195Controller controlador;
    private CallbackClientInterface cliente;


    public void setServidor (CallbackServerInterface servidor){
        this.servidor = servidor;
    }
    public void setNombre (String nombre){
        this.nombre = nombre;
    }
    public void setControlador (Object195Controller controlador){
        this.controlador = controlador;
    }
    public void setNuevaContr (String NuevaContr){
        this.NuevaContr = NuevaContr;
    }
    public void setCliente (CallbackClientInterface cliente){
        this.cliente = cliente;
    }

    @FXML
    void introducirContrasena (ActionEvent event){
        this.contrasena = espacioContrasena.getText();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            if (cliente.comprobarUsuario()) {
                if (servidor.login(nombre,contrasena) >= 0){
                    try{
                        servidor.cambiarContrasena(this.nombre, this.NuevaContr);
                    }catch (RemoteException e){
                        System.err.println("Mensaje de error: " + e.getMessage());
                    }
                    Stage stage = (Stage) contrasenaMala.getScene().getWindow();
                    stage.close();
                } else {
                    contrasenaMala.setText("Lo contraseña introducida es erronea. Vuelva a introducirla");
                    espacioContrasena.clear();
                }
            }
        } catch (RemoteException e){
            System.err.println("Ha habido un error al llamar al seridor: " + e.getMessage());
        }
    }

    @FXML
    void Cerrar (ActionEvent event){
        Stage stage = (Stage) contrasenaMala.getScene().getWindow();
        stage.close();
    }
}
