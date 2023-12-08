package com.example.practicap2p;

//import com.gluonhq.charm.glisten.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddFriendPopUpController {

    @FXML
    private Button Aceptar;

    @FXML
    private TextField FRIENDLABEL;

    @FXML
    private Button Rechazar;

    private String NombreAmigo;
    private String NombreMio;
    @FXML
    private Text TEXTO;

    @FXML
    private Text TEXTO2;

    private CallbackServerInterface servidor;

    public void setServidor(CallbackServerInterface servidor){
        this.servidor = servidor;
    }
    public void setNombreAmigo(String nombre){
        this.NombreAmigo = nombre;
    }
    public void setNombreMio(String nombre){
        this.NombreMio = nombre;
    }
    @FXML
    void AceptarAmigo(ActionEvent event) {
        //Tienes al servidor para llamar a la función
        //AQUI LO QUE HARÍAS SERÍA ES DECIRLE AL SERVIDOR QUE OS HAGA AMIGOS A TI MISMO Y AL QUE TE PIDIÓ
    }

    @FXML
    void RechazarAmigo(ActionEvent event) {
        //Esto sería cerrarlo a lo bruto
        Stage stage = (Stage) TEXTO.getScene().getWindow();
        stage.close();
    }

}
