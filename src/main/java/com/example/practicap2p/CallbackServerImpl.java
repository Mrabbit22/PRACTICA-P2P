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

// This remote method allows an object client to 
// cancel its registration for callback
// @param id is an ID for the client; to be used by
// the server to uniquely identify the registered client.
  public synchronized void unregisterForCallback(CallbackClientInterface callbackClientObject)
    throws RemoteException{
    if (clientList.removeElement(callbackClientObject)) {
      System.out.println("Cliente no registrado ");
    } else {
       System.out.println(
         "No Registrado: el cliente no estaba registrado.");
    }
  } 

  public synchronized void doCallbacks(String linea) throws RemoteException{
    // make callback to each registered client
    System.out.println("Llamadas iniciadas ---");

    for (int i = 0; i < clientList.size(); i++){
      // convert the vector object to a callback object
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
    }// end for
    System.out.println("********************************\n" +
                       "Ronda de llamadas acabada ---\n********************************");
  } // doCallbacks

}// end CallbackServerImpl class   
