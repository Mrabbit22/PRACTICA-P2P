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

   public CallbackClientImpl(String nombre) throws RemoteException {
      super();
      this.nombre = nombre;
   }

   public String notifyMe(String message){
      String returnMessage = "Recibido: " + message;
      System.out.println(returnMessage);
      return returnMessage;
   }      

   public void recibirObjeto(CallbackClientInterface objeto){
      clientList.addElement(objeto);
   }
}// end CallbackClientImpl class   
