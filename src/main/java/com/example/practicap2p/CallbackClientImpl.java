package com.example.practicap2p;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * This class implements the remote interface 
 * CallbackClientInterface.
 * @author M. L. Liu
 */

public class CallbackClientImpl extends UnicastRemoteObject
     implements CallbackClientInterface {
   public Vector clientList;
   public String nombre;

   public CallbackClientImpl() throws RemoteException {
      super();
      this.clientList = new Vector();
   }
   public void setNombre(String nombre)throws RemoteException{
      this.nombre = nombre;
   }

   public String notifyMe(String message){
      String returnMessage = "Recibido: " + message;
      System.out.println(returnMessage);
      return returnMessage;
   }      

   public void recibirObjeto(String Nombre, CallbackClientInterface objeto)throws RemoteException{
      clientList.addElement(objeto);
   }
}// end CallbackClientImpl class   
