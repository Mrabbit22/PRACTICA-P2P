package com.example.practicap2p;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

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
        aux.setText(FreindSelect.getText());
        this.TABPANE.getTabs().add(aux);
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
    *
     */
}
