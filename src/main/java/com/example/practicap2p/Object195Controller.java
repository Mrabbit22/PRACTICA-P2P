package com.example.practicap2p;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class Object195Controller {
    @FXML
    private TextField FreindSelect;

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
    /*
    public void initialize(){
        Tab aux = new Tab();
        aux.setText("Amigos");
        this.TABPANE.getTabs().add(aux);
    }
    */
    @FXML
    void addFriend(ActionEvent event) {
        this.FriendList.setText(this.FriendList.getText() + "\n" + " " + this.FriendTag.getText() + " ");
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

    @FXML
    void removeTab(ActionEvent event) {
        this.TABPANE.getTabs().remove(this.TABPANE.getSelectionModel().getSelectedIndex());
        if (this.TABPANE.getTabs().isEmpty()){
            System.exit(0);
        }
    }

/*
    @FXML
    void changeText(ActionEvent event) {
        TextArea area; String texto;
        VBox cajaux;
        //Saco la caja
        cajaux = (VBox) this.TABPANE.getTabs().get(this.TABPANE.getSelectionModel().getSelectedIndex()).getContent();
        //Saco el TextArea en el que escribir
        area = (TextArea) cajaux.getChildren().get(1);

        //Actualizo el texto
        area.setText(area.getText()+"\n"+texto);
    }
  */
    @FXML
    void SendText(ActionEvent event) {
        Node tabContent = this.TABPANE.getTabs().get(this.TABPANE.getSelectionModel().getSelectedIndex()).getContent();
        if (tabContent instanceof Parent) {
            TextArea textArea = findTextArea((Parent) tabContent);
            TextField textField = findTextField((Parent) tabContent);
            if (textArea.getText() != null) {
                textArea.setText(textArea.getText()+"\n"+textField.getText());
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
