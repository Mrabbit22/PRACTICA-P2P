package com.example.practicap2p;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public interface CallbackClientInterface
  extends Remote{
    public void setControlador(Object195Controller controlador) throws RemoteException;
    public void sentText(String Nombre, String Texto)throws RemoteException;
    public HashMap<String, CallbackClientInterface> getFriends()throws RemoteException;
    public void setToken (UUID token) throws RemoteException;
    public int getId () throws RemoteException;
    public boolean comprobarUsuario () throws RemoteException;
    public void friendRequest(String nombreAmigo) throws RemoteException, RuntimeException;
    public ArrayList <String> getListaPendientes () throws RemoteException;
    public void ServeraddNewFriend(ArrayList<String> amigos) throws RemoteException;
    public void updateFriendList () throws RemoteException;
    public void getAmigos () throws RemoteException;
    public void deleteFriend (String nombreAmigo) throws RemoteException;
    public void addNewFriend(String nombreAmigo, CallbackClientInterface refereciaAmigo) throws RemoteException;
    public void setFriends () throws RemoteException;
    public String getMyLog(String User)throws RemoteException;
}
