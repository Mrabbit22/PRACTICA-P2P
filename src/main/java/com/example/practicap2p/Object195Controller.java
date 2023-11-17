package com.example.practicap2p;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

public class Object195Controller {
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
    /*
    public void initialize(){
        Tab aux = new Tab();
        aux.setText("Amigos");
        this.TABPANE.getTabs().add(aux);
    }
    */
    @FXML
    void addTab(ActionEvent event) {
        Tab aux = new Tab();
        aux.setText("Conexión");
        TextArea auxi = new TextArea();
        auxi.setLayoutX(14);
        auxi.setLayoutY(14);
        auxi.setMaxHeight(310);
        auxi.setMaxWidth(256);
        aux.setContent(auxi);
        //Button botaux = new Button();
        //botaux.setOnAction(MENOSTAB.getOnAction());
        //botaux.setText("X");
        //aux.setContent(botaux);
        this.TABPANE.getTabs().add(aux);
    }

    @FXML
    void removeTab(ActionEvent event) {
        this.TABPANE.getTabs().remove(this.TABPANE.getSelectionModel().getSelectedIndex());
        if (this.TABPANE.getTabs().isEmpty()){
            System.exit(0);
        }
    }

}
