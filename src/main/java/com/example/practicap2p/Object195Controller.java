package com.example.practicap2p;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Object195Controller {
    private Stage stg;
    private AddFriendPopUpController Controlador;

    private solicitarPasswordController Controlador2;

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


    @FXML
    private Button BotonPendientes;

    private int id;

    @FXML
    private Button BotonQuitar;

    private String[] amigos;

    private ArrayList <String> listaPendientes = new ArrayList<>();

    private HashMap<String,String> chatLog = new HashMap<>();

    @FXML
    private Button BotonCambiarContr;

    @FXML
    private TextField NuevaContr;

    private String password;

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

    public int getId (){
        return this.id;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setCliente(CallbackClientImpl cliente){
        this.cliente = cliente;
    }
    public void setServidor(CallbackServerInterface servidor){
        this.servidor = servidor;
    }

    public void setPassword (String password) {this.password = password;}

    public ArrayList <String> getListaPendientes (){
        return this.listaPendientes;
    }

    @FXML
    void CambiarContrasena(ActionEvent event) {
        try {
            this.stg = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SolicitarPassword.fxml"));
            Parent root = fxmlLoader.load();
            Scene Escena = new Scene(root, 600, 400);
            this.stg.setTitle("Solicitud de la contraseña antigüa ");

            this.stg.setScene(Escena);
            this.stg.show();
            this.Controlador2 = fxmlLoader.getController();
            this.Controlador2.setServidor(this.servidor);
            this.Controlador2.setNombre(this.nombre);
            this.Controlador2.setControlador(this);
            this.Controlador2.setNuevaContr(NuevaContr.getText());
            this.Controlador2.setCliente(cliente);
            NuevaContr.clear();
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAmigos(String[] amigos){
        this.amigos = amigos;
    }
    @FXML
    void AcutalizarPendientes(ActionEvent event) {
        for (String nombre : listaPendientes){
            aux(nombre);
        }
    }

    @FXML
    void QuitarAmigo(ActionEvent event) {
        //Aquí haces lo que tengas que hacer
        //El que quitas es this.FriendTag
        try{
            if (cliente.comprobarUsuario()){
                ArrayList <String> amigos = new ArrayList<>();
                amigos.add(this.nombre);
                amigos.add(this.FriendTag.getText());
                int idAmigo = servidor.existeUsuario(this.FriendTag.getText());
                servidor.eliminarAmistad(this.id,idAmigo, amigos);
            }
        }catch (RemoteException e){
            System.err.println("Mensaje de error: " + e.getMessage());
        }
    }

    public void quitarDeLista(String nombre){
        listaPendientes.remove(nombre);
    }
    public void updateFriendLista(){
        this.FriendList.clear();
        try{
            ArrayList <String> listaAmigosConectados = cliente.getOnlineFriends();
            for (String amigoConectado : listaAmigosConectados) {
                this.FriendList.setText(this.FriendList.getText() + "\n" + " " + amigoConectado + " ");
            }
        } catch(RemoteException e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    void sendRequest(ActionEvent event) {
        //this.FriendList.setText(this.FriendList.getText() + "\n" + " " + amigo + " ");
        //Puedo tener una lista de amigos, y cada vez que meto uno actualizarla???
        //Pero entre otras cosas tengo que actualizar el servidor
        //Lo único que debe hacer es cada vez que se conecte alguien, le envie la lista
        //De objetos
        int idAmigo;
        try {
            String username = this.FriendTag.getText();
            if (!username.equals(this.nombre) && cliente.comprobarUsuario()){
                idAmigo = servidor.existeUsuario(username);
                if ( !servidor.existeSolicitud(this.id, idAmigo) && idAmigo >= 0 && !servidor.existeAmistad(this.id,idAmigo)){
                    if (servidor.isUsuarioConectado(username)){
                        servidor.sendRequest(this.nombre,username);
                    }else{
                        servidor.anadirSolicitud(cliente.getId(), idAmigo);
                    }
                }
            }
        }catch (RemoteException e){
            System.err.println("Hay un error: " + e.getMessage());
        }
    }

    public void friendRequest(String Nombre){
        //POR AHORA LO ÚNICO QUE HACE ES ABRIRTE LA PESTAÑA Y PASARLE LA REFERENCIA AL SERVIDOR Y LOS NOMBRES
        //LO QUE VA A ABRIR AHORA ES AddFriendPopUpController
        listaPendientes.add(Nombre);
    }

    public void aux (String Nombre){
        try {
            this.stg = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddFriendPopUp.fxml"));
            Parent root = fxmlLoader.load();
            Scene Escena = new Scene(root, 540, 440);
            this.stg.setTitle("Solicitud de amistad de "+ Nombre);

            this.stg.setScene(Escena);
            this.stg.show();
            //System.out.println(this.getNombre());
            this.Controlador = fxmlLoader.getController();
            this.Controlador.setServidor(this.servidor);
            this.Controlador.setNombreAmigo(Nombre);
            this.Controlador.setIdMio(this.id);
            this.Controlador.setCliente(this.cliente);
            this.Controlador.setNombreMio(this.nombre);
            this.Controlador.setControler(this);
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            try {
                System.out.println(this.cliente.getMyLog(this.nombre));
                System.out.println(this.chatLog.get(FreindSelect.getText()));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
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
    public HashMap<String, String> getLog(){
        return this.chatLog;
    }
    @FXML
    void removeTab(ActionEvent event) {
        this.TABPANE.getTabs().remove(this.TABPANE.getSelectionModel().getSelectedIndex());
        if (this.TABPANE.getTabs().isEmpty()){
            //El servidor se quita su cliente y el de los otros, pero del mio me ocupo yo
            //PELELE, TIENES QUE INDICAR QUE TE HAS CERRADO DEL TODO
            try {
                this.servidor.unregisterForCallback(this.nombre, this.id);
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
                    chatLog.put(User,textArea.getText());
                    //System.out.println("Este si es senttext"+textArea.getText() + "\nLinea nueva ----" + User + ": " + Texto);
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
        try {
            if (this.cliente.getLista().containsKey(this.TABPANE.getTabs().get(this.TABPANE.getSelectionModel().getSelectedIndex()).getText()) && this.cliente.comprobarUsuario()) {
                if (tabContent instanceof Parent) {
                    TextArea textArea = findTextArea((Parent) tabContent);
                    TextField textField = findTextField((Parent) tabContent);
                    if (textArea.getText() != null) {
                        textArea.setText(textArea.getText() + "\n" + this.nombre + ": " + textField.getText());
                        chatLog.put(this.TABPANE.getTabs().get(this.TABPANE.getSelectionModel().getSelectedIndex()).getText(), textArea.getText());
                        //System.out.println("SendText: "+textArea.getText() + "\nLinea nueva ->" + this.nombre + ": " + textField.getText());
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
                } else {
                    //this.TABPANE.getTabs().remove(this.TABPANE.getSelectionModel().getSelectedIndex());
                }
            }
        }catch (RemoteException e){
            System.err.println("Hubo un error al conectarse con el servidor: " + e.getMessage());
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
