package com.example.practicap2p;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface CallbackServerInterface extends Remote {

  public String sayHello( )   
    throws RemoteException;

// This remote method allows an object client to 
// register for callback
// @param callbackClientObject is a reference to the
//        object of the client; to be used by the server
//        to make its callbacks.

  public void registerForCallback(String Nombre,
    CallbackClientInterface callbackClientObject, String[] lista
    ) throws RemoteException;

  public String[] login(String User, String password) throws RemoteException;

// This remote method allows an object client to 
// cancel its registration for callback

  public void unregisterForCallback(
    String Nombre)
    throws RemoteException;
}
