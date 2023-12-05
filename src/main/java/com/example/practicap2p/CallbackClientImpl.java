package com.example.practicap2p;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Vector;

/**
 * This class implements the remote interface 
 * CallbackClientInterface.
 * @author M. L. Liu
 */

public class CallbackClientImpl extends UnicastRemoteObject
     implements CallbackClientInterface {
   private HashMap<String,CallbackClientInterface> clientList;
   public String nombre;
   private Object195Controller controlador;

   public CallbackClientImpl() throws RemoteException {
      super();
      this.clientList = new HashMap<>();
   }
   public void quitarAmigo(String User)throws RemoteException{
      //Esto ademas tiene que chaparte la pestaña
      //this.controlador.removeTab2(User);//Al parecer no le vale que se lo llame aquí
      if(this.clientList.containsKey(User)){
         this.clientList.remove(User);
      }
   }
   public HashMap<String, CallbackClientInterface> getLista(){
      return this.clientList;
   }
   public void setControlador(Object195Controller controlador)throws RemoteException{
      this.controlador = controlador;
   }
   public void setNombre(String nombre)throws RemoteException{
      this.nombre = nombre;
   }

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
}// end CallbackClientImpl class   
