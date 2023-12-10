package com.example.practicap2p;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;


public interface CallbackClientInterface
  extends Remote{
    public String notifyMe(String message) 
      throws RemoteException;
    public void recibirObjeto(String nombre, CallbackClientInterface objeto) throws RemoteException;
    public void recibirObjetoUPD(String nombre, CallbackClientInterface objeto) throws RemoteException;
    //public void setNombre(String nombre) throws RemoteException;
    public void setControlador(Object195Controller controlador) throws RemoteException;
    public void sentText(String Nombre, String Texto)throws RemoteException;
    public HashMap<String, CallbackClientInterface> getFriends()throws RemoteException;
    public int getId () throws RemoteException;
    public Object195Controller getControlador () throws RemoteException;
    public void friendRequest(String nombreAmigo) throws RemoteException, RuntimeException;
    public void ServeraddNewFriend(ArrayList<String> amigos) throws RemoteException;
    public void updateFriendList () throws RemoteException;
    public void deleteFriend (String nombreAmigo) throws RemoteException;
    public void addNewFriend(String nombreAmigo, CallbackClientInterface refereciaAmigo) throws RemoteException;
    public void setFriends () throws RemoteException;
    public void quitarAmigo(String User)throws RemoteException;
    public String getMyLog(String User)throws RemoteException;
}
