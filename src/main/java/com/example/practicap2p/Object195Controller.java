package com.example.practicap2p;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

public class Object195Controller {
    @FXML
    private TextField FreindSelect;

    private CallbackClientImpl cliente;

    private CallbackServerInterface servidor;

    @FXML
    private TextField FriendTag;

    @FXML
    private Button AddFriend;

    @FXML
    private Tab FriendManager;

    @FXML
    private Button MASTAB;

    @FXML
    private Button MENOSTAB;

    @FXML
    private TabPane TABPANE;

    @FXML
    private TextArea TEXTOMOSTRARCOSAS;

    @FXML
    private TextArea FriendList;
    private String nombre;

    //public void initialize(){
        /*
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("hello-view.fxml"));
        try {
            Parent loginparent = loader.load();
            Scene loginscene = new Scene(loginparent);
            HelloController controlador = loader.getController();
            this.nombre = controlador.getNombre();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    //}
    public void updateFriendLista(){//Claro, ahora esto usa un hashmap -> Hay que remodelar
        this.FriendList.clear();
        for(Map.Entry<String, CallbackClientInterface> token : this.cliente.getLista().entrySet()){//Podrías castearlo aquí
            this.FriendList.setText(this.FriendList.getText() + "\n" + token.getKey());
        }
    }
    @FXML
    void addFriend(ActionEvent event) {
        //this.FriendList.setText(this.FriendList.getText() + "\n" + " " + amigo + " ");
        //Puedo tener una lista de amigos, y cada vez que meto uno actualizarla???
        //Pero entre otras cosas tengo que actualizar el servidor
        //Lo único que debe hacer es cada vez que se conecte alguien, le envie la lista
        //De objetos
    }
    @FXML
    void addTab(ActionEvent event) {
        Tab aux = new Tab();
        VBox caja = new VBox();
        if (this.FriendList.getText().contains(" " + this.FreindSelect.getText() + " ")){
            aux.setText(FreindSelect.getText());
            //Creo la caja en la que haré el display del texto
            TextArea auxi = new TextArea();
            auxi.setLayoutX(14);
            auxi.setLayoutY(14);
            auxi.setMaxHeight(310);
            auxi.setEditable(false);
            //auxi.setMaxWidth(256);
            aux.setContent(caja);
            //Creo un boton para cerrar la pestaña, que llame a MENOSTAB
            Button botaux = new Button();
            botaux.setOnAction(MENOSTAB.getOnAction());
            botaux.setText("X");
            //Ahora a crear un TextField para manejar los mensajes
            TextField Escritura = new TextField();
            Escritura.setPromptText("Escribe aquí");
            Button botesc = new Button();
            botaux.setOnAction(this::SendText);
            botaux.setText("Enviar");
            //Metes las cosas en la caja y la caja en lña pestaña
            caja.getChildren().add(auxi);
            caja.getChildren().add(botaux);
            caja.getChildren().add(Escritura);
            this.TABPANE.getTabs().add(aux);
        }
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setCliente(CallbackClientImpl cliente){
        this.cliente = cliente;
    }
    public void setServidor(CallbackServerInterface servidor){
        this.servidor = servidor;
    }
    @FXML
    void removeTab(ActionEvent event) {
        this.TABPANE.getTabs().remove(this.TABPANE.getSelectionModel().getSelectedIndex());
        if (this.TABPANE.getTabs().isEmpty()){
            System.exit(0);
        }
    }

    @FXML
    void SendText(ActionEvent event) {
        Node tabContent = this.TABPANE.getTabs().get(this.TABPANE.getSelectionModel().getSelectedIndex()).getContent();
        if (tabContent instanceof Parent) {
            TextArea textArea = findTextArea((Parent) tabContent);
            TextField textField = findTextField((Parent) tabContent);
            if (textArea.getText() != null) {
                textArea.setText(textArea.getText()+"\n"+ this.nombre + ": "+textField.getText());
                textField.clear();
            }else{
                textArea.setText(textField.getText());
                textField.clear();
            }
        }
    }
    private TextArea findTextArea(Parent parent) {
        if (parent instanceof TextArea) {
            return (TextArea) parent;
        } else {
            for (Node child : parent.getChildrenUnmodifiable()) {
                if (child instanceof Parent) {
                    TextArea result = findTextArea((Parent) child);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }
    private TextField findTextField(Parent parent) {
        if (parent instanceof TextField) {
            return (TextField) parent;
        } else {
            for (Node child : parent.getChildrenUnmodifiable()) {
                if (child instanceof Parent) {
                    TextField result = findTextField((Parent) child);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }

}
