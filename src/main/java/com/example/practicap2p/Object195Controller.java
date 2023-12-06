package com.example.practicap2p;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.rmi.RemoteException;
import java.util.HashMap;
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

    private String[] amigos;

    private HashMap<String,String> chatLog = new HashMap<>();

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
    public void setAmigos(String[] amigos){
        this.amigos = amigos;
    }
    public void updateFriendLista(){//Claro, ahora esto usa un hashmap -> Hay que remodelar
        this.FriendList.clear();
        //Para que salgan todos, si tal hago una lista de String, y que eso sean mis amigos totales
        //Y que el hashmap sea los conectados, así que me los tienen que quitar al desconectarse
        for(String token : this.amigos){//Podrías castearlo aquí
            this.FriendList.setText(this.FriendList.getText() + "\n" + " " + token + " ");
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
    void addTab(ActionEvent event) {//Este si es solo para los conectados
        //Así que para abrir la conexión tiene que mirar si está en el hashmap
        Tab aux = new Tab();
        VBox caja = new VBox();
        if (this.cliente.getLista().containsKey(this.FreindSelect.getText())){
            aux.setText(FreindSelect.getText());
            //Creo la caja en la que haré el display del texto
            TextArea auxi = new TextArea();
            auxi.setLayoutX(14);
            auxi.setLayoutY(14);
            auxi.setMaxHeight(310);
            auxi.setEditable(false);
            //Necesito una función a traves del cliente, que me permita sacar la conversación
            for(Map.Entry<String, CallbackClientInterface> token : this.cliente.getLista().entrySet()){
                if(token.getKey().equals(this.FreindSelect.getText())){
                    try {
                        if(this.chatLog.containsKey(FreindSelect.getText()) && token.getValue().getMyLog(this.nombre) == null){//El mio existe solo
                            auxi.setText(this.chatLog.get(FreindSelect.getText()));
                            break;
                        } else if (!this.chatLog.containsKey(FreindSelect.getText()) && token.getValue().getMyLog(this.nombre) != null) {//El suyo existe solo
                            auxi.setText(token.getValue().getMyLog(this.nombre));
                            break;
                        } else if (!this.chatLog.containsKey(FreindSelect.getText()) && token.getValue().getMyLog(this.nombre) == null) {
                            break;
                        } else{//Ambos existen (El más grande)
                            //Si son iguales, o el mio es mayor
                            if (this.chatLog.get(FreindSelect.getText()).toCharArray().length >= token.getValue().getMyLog(this.nombre).toCharArray().length){
                                auxi.setText(this.chatLog.get(FreindSelect.getText()));
                                break;
                            }else{
                                auxi.setText(this.cliente.getMyLog(this.nombre));
                                break;
                            }
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            System.out.println(auxi.getText());
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
    public HashMap<String, String> getLog(){
        return this.chatLog;
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
            //El servidor se quita su cliente y el de los otros, pero del mio me ocupo yo
            //PELELE, TIENES QUE INDICAR QUE TE HAS CERRADO DEL TODO
            try {
                this.servidor.unregisterForCallback(this.nombre);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);
        }
    }
    void removeTab2(String tab) {//Si se desconecta el otro te chapa la conexión, pero desconexión del todo
        this.TABPANE.getTabs().removeIf(token -> token.getText().equals(tab));
        //this.TABPANE.getTabs().remove(this.TABPANE.getSelectionModel().getSelectedIndex());
        if (this.TABPANE.getTabs().isEmpty()){
            System.exit(0);
        }
    }

    public void sentText(String Texto, String User){//Mirar que lo añada al Tab al que pertenece
        //Node tabContent = this.TABPANE.getTabs().get(this.TABPANE.getSelectionModel().getSelectedIndex()).getContent();
        Node tabContent = null;
        Boolean exit = false;
        for(Tab token : this.TABPANE.getTabs()){
            if(token.getText().equals(User)){
                tabContent = token.getContent();
                exit = true;
            }
        }
        if(exit){
            if (tabContent instanceof Parent) {
                TextArea textArea = findTextArea((Parent) tabContent);
                TextField textField = findTextField((Parent) tabContent);
                if (textArea.getText() != null) {
                    textArea.setText(textArea.getText() + "\n" + User + ": " + Texto);
                    chatLog.put(User,textArea.getText() + "\n" + User + ": " + Texto);
                    textField.clear();
                } else {
                    textArea.setText(textField.getText());
                    textField.clear();
                }
            }
        }
    }
    @FXML
    void SendText(ActionEvent event) {
        Node tabContent = this.TABPANE.getTabs().get(this.TABPANE.getSelectionModel().getSelectedIndex()).getContent();
        if(this.cliente.getLista().containsKey(this.TABPANE.getTabs().get(this.TABPANE.getSelectionModel().getSelectedIndex()).getText())){
            if (tabContent instanceof Parent) {
                TextArea textArea = findTextArea((Parent) tabContent);
                TextField textField = findTextField((Parent) tabContent);
                if (textArea.getText() != null) {
                    textArea.setText(textArea.getText() + "\n" + this.nombre + ": " + textField.getText());
                    chatLog.put(this.nombre,textArea.getText() + "\n" + this.nombre + ": " + textField.getText());
                    //Itero por la lista de amigos -> Busco el del chat
                    //Uso su objeto cliente para enviar el mensaje
                    for (Map.Entry<String, CallbackClientInterface> token : this.cliente.getLista().entrySet()) {
                        //Como saco el nombre del tab en el que estoy
                        if (token.getKey().equals(this.TABPANE.getSelectionModel().getSelectedItem().getText())) {
                            try {
                                token.getValue().sentText(this.nombre, textField.getText());
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    textField.clear();
                } else {
                    textArea.setText(textField.getText());
                    textField.clear();
                }
            }else{
                //this.TABPANE.getTabs().remove(this.TABPANE.getSelectionModel().getSelectedIndex());
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
