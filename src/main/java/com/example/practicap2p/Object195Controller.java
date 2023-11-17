package com.example.practicap2p;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Object195Controller {
    @FXML
    private TextField FreindSelect;

    @FXML
    private Tab FriendManager;

    @FXML
     private Button MASTAB;

    @FXML
    private Button MENOSTAB;

    @FXML
    private TabPane TABPANE;

    @FXML
    private Button BENVIAR;


    @FXML
    private TextField TENVIAR;
    @FXML
    private TextArea TEXTOMOSTRARCOSAS;
    /*
    public void initialize(){
        Tab aux = new Tab();
        aux.setText("Amigos");
        this.TABPANE.getTabs().add(aux);
    }
    */
    @FXML
    private void initialize(){

    }
    @FXML
    void addTab(ActionEvent event) {
        FXMLLoader cargador = new FXMLLoader(getClass().getResource("SAMPLETAB.fxml"));
        Tab aux = new Tab();
        try {
            aux.setContent(cargador.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        aux.setText(FreindSelect.getText());
        this.TEXTOMOSTRARCOSAS.setText("Conversación con "+FreindSelect.getText());
        this.TABPANE.getTabs().add(aux);
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
        String texto = this.TENVIAR.getText();
        this.TEXTOMOSTRARCOSAS.setText(this.TEXTOMOSTRARCOSAS.getText()+"\n"+texto);
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
    *
     */
}
