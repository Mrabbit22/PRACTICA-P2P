package com.example.practicap2p;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class CallbackServerImpl extends UnicastRemoteObject implements CallbackServerInterface {

   private HashMap<String,CallbackClientInterface> clientList;

   private final DAOConexion conexion;

   public CallbackServerImpl() throws RemoteException {
      super( );
     clientList = new HashMap<>();
     this.conexion = new DAOConexion();
   }

   private DAOConexion getConexion (){
       return this.conexion;
   }

   public void registrarUsuario (String username, String password) throws RemoteException {
       this.getConexion().insertar_Usuario(username,password);
   }

   public int login (String username, String password) throws RemoteException {
       return this.getConexion().login(username,password);
   }

   public int existeUsuario(String username) throws RemoteException {
       return this.getConexion().existeUsuario(username);
   }

   public void nuevoAmigo (int id, int amigo) throws RemoteException {
       this.getConexion().insertarAmigo(id, amigo);
   }

   public boolean isUsuarioConectado(String nombre) throws RemoteException{
       boolean isUsuarioConectado = false;
       if(clientList.containsKey(nombre)){
           isUsuarioConectado = true;
       }
       return isUsuarioConectado;
   }

   public void sendRequest (String yo, String nombreAmigo) throws RemoteException{
       CallbackClientInterface user = this.clientList.get(nombreAmigo);
       user.friendRequest(yo);
   }

   public HashMap <String,CallbackClientInterface> obtenerAmigos (int id) throws RemoteException{
       HashMap <String,CallbackClientInterface> amigos = new HashMap<>();
       ArrayList <String> aux = this.getConexion().obtenerAmigos(id);
       for (String nombre : aux){
           if(isUsuarioConectado(nombre)){
               amigos.put(nombre,clientList.get(nombre));
           }else {
               amigos.put(nombre,null);
           }
       }
        return amigos;
   }

   public ArrayList <String> obtenerAmigosOnline (int id, ArrayList <String> amigos) throws RemoteException{
       ArrayList <String> amigosConectados = new ArrayList<>();
       for (String key : clientList.keySet()){
           if (amigos.contains(key)){
               amigosConectados.add(key);
           }
       }
       return amigosConectados;
   }

    public void amigoEnCliente (ArrayList <String> amigos) throws RemoteException{
       CallbackClientInterface amigo1 = this.clientList.get(amigos.get(0));
       CallbackClientInterface amigo2 = this.clientList.get(amigos.get(1));
       amigo1.addNewFriend(amigos.get(1),amigo2);
       amigo1.getControlador().updateFriendLista();
       amigo2.addNewFriend(amigos.get(0),amigo1);
       amigo2.getControlador().updateFriendLista();
    }

   public void añadirSolicitud (int yo, int amigo) throws RemoteException{
       conexion.añadirSolicitud(yo,amigo);
   }

  public String sayHello( )
    throws RemoteException {
      return("hello");
  }

  public synchronized void registerForCallback(String nombre, CallbackClientInterface callbackClientObject)
    throws RemoteException{
       CallbackClientInterface aux;

      if (!(clientList.containsKey(nombre))) {
          /*for(String amigo : lista) {//Por cada uno de mis amigos, lo tengo que buscar
              for (Map.Entry<String, CallbackClientInterface> token : clientList.entrySet()) { //Si todos son amigos no compruebo nada
                  if (token.getKey().equals(amigo)) {
                      //Meto en el cliente nuevo un amigo
                      callbackClientObject.recibirObjeto(token.getKey(), token.getValue());
                      //Meto en el amigo un cliente nuevo
                      token.getValue().recibirObjetoUPD(nombre, callbackClientObject);
                      //Si le paso el controller al cliente, podría actualizarlo desde aquí, sin pasarlo al servidor
                  }
              }
          }*/
         clientList.put(nombre,callbackClientObject);
      }
  }

  public synchronized void unregisterForCallback(String Nombre)
    throws RemoteException{
       //Tengo que ir por la lista de cliente y quitarles al amigo del hasmap.
      for(Map.Entry<String, CallbackClientInterface> token : clientList.entrySet()){
          if(!token.getKey().equals(Nombre)) {
              token.getValue().quitarAmigo(Nombre);
          }
      }
      clientList.remove(Nombre);
   }


    public synchronized void doCallbacks(String linea) throws RemoteException {
        // make callback to each registered client
        System.out.println("Llamadas iniciadas ---");
    }
    /*
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
  */
}