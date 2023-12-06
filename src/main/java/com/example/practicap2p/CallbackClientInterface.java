package com.example.practicap2p;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface CallbackClientInterface
  extends Remote{
    public String notifyMe(String message) 
      throws RemoteException;
    public void recibirObjeto(String nombre, CallbackClientInterface objeto) throws RemoteException;
    public void recibirObjetoUPD(String nombre, CallbackClientInterface objeto) throws RemoteException;
    public void setNombre(String nombre) throws RemoteException;
    public void setControlador(Object195Controller controlador) throws RemoteException;
    public void sentText(String Nombre, String Texto)throws RemoteException;
    public void quitarAmigo(String User)throws RemoteException;
    public String getMyLog(String User)throws RemoteException;
}
