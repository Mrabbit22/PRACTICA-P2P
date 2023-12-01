package com.example.practicap2p;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class CallbackServerImpl extends UnicastRemoteObject implements CallbackServerInterface {

   private Vector clientList;


   public CallbackServerImpl() throws RemoteException {
      super( );
     clientList = new Vector();
   }

  public String sayHello( )
    throws RemoteException {
      return("hello");
  }
  public synchronized void registerForCallback(CallbackClientInterface callbackClientObject)
    throws RemoteException{
       CallbackClientInterface aux;

      if (!(clientList.contains(callbackClientObject))) {
          for(Object token : clientList) { //Si todos son amigos no compruebo nada
              aux = (CallbackClientInterface) token;
              //Meto en el cliente nuevo un amigo
              callbackClientObject.recibirObjeto(aux);
              //Meto en el amigo un cliente nuevo
              aux.recibirObjeto(callbackClientObject);
          }
         clientList.addElement(callbackClientObject);
      }
  }  

  public synchronized void unregisterForCallback(CallbackClientInterface callbackClientObject)
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