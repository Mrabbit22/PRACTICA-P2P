package com.example.practicap2p;

import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;

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
   private UUID token;

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

   public boolean comprobarUsuario () throws RemoteException{
      boolean toret = false;
      try {
         toret = servidor.comprobarUsuario(nombre,token);
      } catch (RemoteException e) {
         System.err.println("Existe un error al conectar con el servidor; " + e.getMessage());
      }
      return toret;
   }

   public void getAmigos () throws RemoteException{
      System.out.println(clientList.size());
      for (String amigo : clientList.keySet()){
         System.out.println("Nombre del amigo: " + amigo);
      }
   }
   public HashMap<String, CallbackClientInterface> getLista(){
      return this.clientList;
   }

   public void setToken (UUID token) throws RemoteException{
      this.token = token;
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

   public ArrayList <String> getListaPendientes () throws RemoteException{
      return controlador.getListaPendientes();
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
