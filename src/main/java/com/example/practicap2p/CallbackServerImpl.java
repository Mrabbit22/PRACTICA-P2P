package com.example.practicap2p;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CallbackServerImpl extends UnicastRemoteObject implements CallbackServerInterface {

   private HashMap<String,CallbackClientInterface> clientList;


   public CallbackServerImpl() throws RemoteException {
      super( );
     clientList = new HashMap<>();
   }

  public String sayHello( )
    throws RemoteException {
      return("hello");
  }
  public synchronized void registerForCallback(CallbackClientInterface callbackClientObject,String nombre)
    throws RemoteException{
       CallbackClientInterface aux;

      if (!(clientList.containsKey(nombre))) {
          for( Map.Entry<String, CallbackClientInterface> token : clientList.entrySet()) { //Si todos son amigos no compruebo nada
              //Meto en el cliente nuevo un amigo
              callbackClientObject.recibirObjeto(token.getKey(),token.getValue());
              //Meto en el amigo un cliente nuevo
              token.getValue().recibirObjeto(nombre,callbackClientObject);
          }
         clientList.put(nombre,callbackClientObject);
      }
  }  

  public synchronized void unregisterForCallback(String Nombre, CallbackClientInterface callbackClientObject)
    throws RemoteException{clientList.removeElement(callbackClientObject);}

  public synchronized void doCallbacks(String linea) throws RemoteException{
    // make callback to each registered client
    System.out.println("Llamadas iniciadas ---");

    for (int i = 0; i < clientList.size(); i++){
      CallbackClientInterface nextClient = (CallbackClientInterface)clientList.elementAt(i);
      //Comprobar si está conectado
        //Eliminarlo del clientList
        try{
            nextClient.notifyMe(linea);
        }catch (RemoteException a){
            //Elimina al cliente
            System.out.println("Alguien se ha desconectado forzosamente");
            this.unregisterForCallback(nextClient);
        }
    }
  }
}