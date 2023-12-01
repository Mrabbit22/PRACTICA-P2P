package com.example.practicap2p;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private Stage stg;
    private Scene login;
    @Override
    public void start(Stage stage) throws IOException {
        //stg = stage;
        //stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        login = new Scene(fxmlLoader.load(), 320, 320);
        stage.setTitle("LOGIN");
        stage.setScene(login);
        stage.show();
    }
    public void changeScene(FXMLLoader fxmlLoader) throws IOException{
        this.stg = new Stage();
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Object195.fxml"));
        Scene Escena = new Scene(fxmlLoader.load(), 540, 440);
        this.stg.setTitle("Titulo");

        this.stg.setScene(Escena);
        this.stg.show();
    }
    public static void main(String[] args) {
        launch();
    }
}