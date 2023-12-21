package com.example.practicap2p;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CallbackServerImpl extends UnicastRemoteObject implements CallbackServerInterface {

   private HashMap<String,CallbackClientInterface> clientList;

   private HashMap<UUID,String> listaTokens;

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
       return clientList.containsKey(nombre);
   }

   public void sendRequest (String yo, String nombreAmigo) throws RemoteException{
       CallbackClientInterface user = this.clientList.get(nombreAmigo);
       user.friendRequest(yo);
   }

   public ArrayList <String> obtenerSolicitudes (int yo) throws RemoteException{
       return this.conexion.obtenerSolicitudes(yo);
   }

   public boolean existeAmistad (int yo, int amigo) throws RemoteException{
       return conexion.existeAmistad(yo, amigo);
   }

   public void eliminarSolicitud (int yo, int amigo) throws RemoteException{
        conexion.eliminarSolicitud(yo, amigo);
   }

   public boolean existeSolicitud (int yo, int amigo) throws RemoteException{
       return conexion.existeSolicitud(yo,amigo);
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

   public void cambiarContrasena (String nombre, String nuevaContrasena) throws RemoteException{
       conexion.cambiarContrasena(nombre,nuevaContrasena);
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

    public void eliminarAmistad (int yo, int amigo, ArrayList <String> amigos) throws RemoteException{
       conexion.eliminarAmistad(yo,amigo);
        CallbackClientInterface amigo1 = this.clientList.get(amigos.get(0));
        CallbackClientInterface amigo2 = this.clientList.get(amigos.get(1));
        if (amigo1 != null){
            amigo1.deleteFriend(amigos.get(1));
            amigo1.updateFriendList();
        }
        if (amigo2 != null){
            amigo2.deleteFriend(amigos.get(0));
            amigo2.updateFriendList();
        }
    }

    public void amigoEnCliente (ArrayList <String> amigos) throws RemoteException{
       CallbackClientInterface amigo1 = this.clientList.get(amigos.get(0));
       CallbackClientInterface amigo2 = this.clientList.get(amigos.get(1));
       if (amigo1 != null){
           amigo1.addNewFriend(amigos.get(1),amigo2);
           amigo1.updateFriendList();
       }
       if(amigo2 != null){
           amigo2.addNewFriend(amigos.get(0),amigo1);
           amigo2.updateFriendList();
       }
    }

    public boolean comprobarUsuario (String nombre,UUID token) throws RemoteException{
       boolean toret = false;
       if(listaTokens.containsKey(token)){
           if (nombre.equals(listaTokens.get(token))){
                toret = true;
           }
       }
       return toret;
    }

   public void anadirSolicitud(int yo, int amigo) throws RemoteException{
       conexion.anadirSolicitud(yo,amigo);
   }

  public String sayHello( )
    throws RemoteException {
      return("hello");
  }

  public synchronized void registerForCallback(String nombre, CallbackClientInterface callbackClientObject)
    throws RemoteException{

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
          UUID token = UUID.randomUUID();
          callbackClientObject.setToken(token);
          listaTokens.put(token,nombre);
          clientList.put(nombre, callbackClientObject);
          for (String cliente : clientList.keySet()) {
              CallbackClientInterface client = clientList.get(cliente);
              client.updateFriendList();
          }
      }
  }

  public synchronized void unregisterForCallback(String Nombre, int id)
    throws RemoteException{
       ArrayList <String> listaPendientes;
       int idAmigo;
       //Tengo que ir por la lista de cliente y quitarles al amigo del hasmap.
      for(Map.Entry<String, CallbackClientInterface> token : clientList.entrySet()){
          if(!token.getKey().equals(Nombre)) {
              token.getValue().deleteFriend(Nombre);
          }
      }
      listaPendientes = (ArrayList<String>) clientList.get(Nombre).getListaPendientes();
      for (String nombre : listaPendientes){
          idAmigo = conexion.existeUsuario(nombre);
          if (idAmigo >= 0){
              conexion.anadirSolicitud(idAmigo, id);
          }
      }
      for (String cliente : clientList.keySet()) {
          CallbackClientInterface client = clientList.get(cliente);
          client.updateFriendList();
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