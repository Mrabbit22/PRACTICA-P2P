package com.example.practicap2p;

//import com.gluonhq.charm.glisten.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class AddFriendPopUpController {

    @FXML
    private Button Aceptar;

    @FXML
    private TextField FRIENDLABEL;

    @FXML
    private Button Rechazar;

    private String NombreAmigo;
    private int idMio;
    private String nombreMio;
    @FXML
    private Text TEXTO;

    @FXML
    private Text TEXTO2;

    private CallbackServerInterface servidor;

    private CallbackClientInterface cliente;

    private Object195Controller controler;
    public void setServidor(CallbackServerInterface servidor){
        this.servidor = servidor;
    }
    public void setNombreAmigo(String nombre){
        this.NombreAmigo = nombre;
    }
    public void setIdMio(int id){
        this.idMio = id;
    }

    public void setNombreMio(String yo){
        this.nombreMio = yo;
    }

    public void setCliente (CallbackClientInterface yo){
        this.cliente = yo;
    }

    public void setControler ( Object195Controller controler){
        this.controler = controler;
    }
    void initialize(){
        FRIENDLABEL.setText(this.NombreAmigo);
    }
    @FXML
    void AceptarAmigo(ActionEvent event) {
        //Tienes al servidor para llamar a la función
        //AQUI LO QUE HARÍAS SERÍA ES DECIRLE AL SERVIDOR QUE OS HAGA AMIGOS A TI MISMO Y AL QUE TE PIDIÓ
        try{
            int idAmigo = servidor.existeUsuario(this.NombreAmigo);
            if(idAmigo >= 0){
                servidor.nuevoAmigo(this.idMio,idAmigo);
                ArrayList <String> amigos = new ArrayList<>();
                amigos.add(nombreMio);
                amigos.add(NombreAmigo);
                cliente.ServeraddNewFriend(amigos);
            }
            System.out.println(nombreMio);
            System.out.println(NombreAmigo);
            controler.quitarDeLista(nombreMio);
            Stage stage = (Stage) TEXTO.getScene().getWindow();
            stage.close();
        } catch (RemoteException e){
            System.err.println("Mensaje de error: " + e.getMessage());
        }

    }

    @FXML
    void RechazarAmigo(ActionEvent event) {
        //Esto sería cerrarlo a lo bruto
        controler.quitarDeLista(NombreAmigo);
        Stage stage = (Stage) TEXTO.getScene().getWindow();
        stage.close();
    }

}
