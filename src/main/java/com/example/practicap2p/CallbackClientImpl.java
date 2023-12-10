package com.example.practicap2p;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * This class implements the remote interface 
 * CallbackClientInterface.
 * @author M. L. Liu
 */

public class CallbackClientImpl extends UnicastRemoteObject
     implements CallbackClientInterface {
   private HashMap<String,CallbackClientInterface> clientList;
   private String nombre;

   private int id;
   private Object195Controller controlador;

   private CallbackServerInterface servidor;

   private Stage stg;
   private AddFriendPopUpController Controlador;

   public CallbackClientImpl(String nombre, CallbackServerInterface servidor, int id) throws RemoteException {
      super();
      this.clientList = new HashMap<>();
      this.nombre = nombre;
      this.servidor = servidor;
      this.id = id;
   }

   public void setControlador (Object195Controller controlador){
      this.controlador = controlador;
   }
   public void quitarAmigo(String User)throws RemoteException{
      //Esto ademas tiene que chaparte la pestaña
      //this.controlador.removeTab2(User);//Al parecer no le vale que se lo llame aquí
      if(this.clientList.containsKey(User)){
         this.clientList.remove(User);
      }
   }
   public String getMyLog(String User)throws RemoteException{
      if(this.controlador.getLog().containsKey(User)){
         return this.controlador.getLog().get(User);
      }
      return null;
   }
   public HashMap<String, CallbackClientInterface> getLista(){
      return this.clientList;
   }
   /*public void setControlador(Object195Controller controlador)throws RemoteException{
      this.controlador = controlador;
   }
   public void setNombre(String nombre)throws RemoteException{
      this.nombre = nombre;
   }*/

   public String notifyMe(String message){//Algo así pero que llame a la de escribir mensaje
      //Entonces he de modificar la de escribir mensajes para que mire el usuario
      String returnMessage = "Recibido: " + message;
      System.out.println(returnMessage);
      return returnMessage;
   }

   public void recibirObjeto(String Nombre, CallbackClientInterface objeto)throws RemoteException{
      clientList.put(Nombre,objeto);
   }
   public void recibirObjetoUPD(String Nombre, CallbackClientInterface objeto)throws RemoteException{
      clientList.put(Nombre,objeto);
      this.controlador.updateFriendLista();
   }
   public void sentText(String Nombre, String Texto)throws RemoteException{
      this.controlador.sentText(Texto,Nombre);
   }

   public void friendRequest(String nombreAmigo) throws RemoteException, RuntimeException{
      this.controlador.friendRequest(nombreAmigo);
   }

   public void updateFriendList () throws RemoteException{
      this.controlador.updateFriendLista();
   }

   public void ServeraddNewFriend(ArrayList <String> amigos) throws RemoteException{
      servidor.amigoEnCliente(amigos);
   }

   public void deleteFriend (String nombreAmigo) throws RemoteException{
      this.clientList.remove(nombreAmigo);
   }

   public void addNewFriend(String nombreAmigo, CallbackClientInterface refereciaAmigo) throws RemoteException{
      this.clientList.put(nombreAmigo,refereciaAmigo);
   }

   public int getId () throws RemoteException {
      return this.id;
   }

   public Object195Controller getControlador () throws RemoteException{
      return this.controlador;
   }
   public HashMap<String, CallbackClientInterface> getFriends() throws RemoteException{
      return this.clientList;
   }

   public ArrayList <String> getOnlineFriends () throws RemoteException{
      ArrayList <String> Friends = new ArrayList<>(this.clientList.keySet());
      return servidor.obtenerAmigosOnline(this.id, Friends);
   }

   public void setFriends () throws RemoteException{
      try{
         this.clientList = servidor.obtenerAmigos(this.id);
      } catch (RemoteException e){
         System.err.println("Mensaje de error: " + e.getMessage());
      }
   }
}// end CallbackClientImpl class   
