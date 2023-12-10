package com.example.practicap2p;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;


public interface CallbackServerInterface extends Remote {

  public String sayHello( )   
    throws RemoteException;

// This remote method allows an object client to 
// register for callback
// @param callbackClientObject is a reference to the
//        object of the client; to be used by the server
//        to make its callbacks.

  public void registerForCallback(String Nombre,
    CallbackClientInterface callbackClientObject) throws RemoteException;

  public void registrarUsuario(String username, String password) throws RemoteException;

  public int login (String username, String password) throws RemoteException;

  public int existeUsuario(String username) throws RemoteException;

  public void nuevoAmigo (int id, int amigo) throws RemoteException;
  public void amigoEnCliente (ArrayList <String> amigos) throws RemoteException;

  public boolean isUsuarioConectado(String nombre) throws RemoteException;

  public void añadirSolicitud (int yo, int amigo) throws RemoteException;

  public void sendRequest (String yo, String nombreAmigo) throws RemoteException;

  public void eliminarAmistad (int yo, int amigo, ArrayList <String> amigos) throws RemoteException;

  public void cambiarContrasena (String nombre, String nuevaContrasena) throws RemoteException;

  public HashMap<String,CallbackClientInterface> obtenerAmigos (int id) throws RemoteException;

  public ArrayList <String> obtenerAmigosOnline (int id, ArrayList <String> amigos) throws RemoteException;

// This remote method allows an object client to 
// cancel its registration for callback

  public void unregisterForCallback(
    String Nombre)
    throws RemoteException;
}
