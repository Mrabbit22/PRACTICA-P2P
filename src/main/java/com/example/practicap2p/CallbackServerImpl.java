package com.example.practicap2p;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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
  public synchronized void registerForCallback(String nombre, CallbackClientInterface callbackClientObject, String[] lista)
    throws RemoteException{
       CallbackClientInterface aux;

      if (!(clientList.containsKey(nombre))) {
          for(String amigo : lista) {//Por cada uno de mis amigos, lo tengo que buscar
              for (Map.Entry<String, CallbackClientInterface> token : clientList.entrySet()) { //Si todos son amigos no compruebo nada
                  if (token.getKey().equals(amigo)) {
                      //Meto en el cliente nuevo un amigo
                      callbackClientObject.recibirObjeto(token.getKey(), token.getValue());
                      //Meto en el amigo un cliente nuevo
                      token.getValue().recibirObjetoUPD(nombre, callbackClientObject);
                      //Si le paso el controller al cliente, podría actualizarlo desde aquí, sin pasarlo al servidor
                  }
              }
          }
         clientList.put(nombre,callbackClientObject);
      }
  }  

  public String[] login(String User, String password){
      //LO QUE PUEDO HACER AHORA ES LEER UN ARCHIVO ALGO ASÍ:
      //USUARIO CONTRASEÑA AMIGO1,AMIGO2,AMIGO3.....
      //LEO, SEPARO POR ESPACIOS, MIRO SI COINCIDE NOMBRE, SI NO REPITO, SI SI
      //LE PASO EL NOMBRE A Object 195
      //LE PASO LA LISTA DE AMIGOS AL SERVIDOR AL HACER LA CONEXIÓN -> PARTE DIFICIL

      String[] secciones;
      //Abrir archivo
      try{
          FileInputStream fstream = new FileInputStream("datos.txt");
          DataInputStream in = new DataInputStream(fstream);
          BufferedReader br = new BufferedReader(new InputStreamReader(in));
          String linea;
          while ((linea = br.readLine()) != null){
              secciones = linea.split(" ");
              //Si coinciden el nombre y contraseña
              if(secciones[0].equals(User) && secciones[1].equals(password)){
                return secciones[2].split(",");
              }
          }
          in.close();
      } catch (Exception e){
          System.out.println(e.getMessage());
      }
      //Separar por espacios
      //Comprobar contra this.getNombre
      //Si no coincide repito (Posible while)
      //Si coincide reviso contraseña, y si también coincide
      //LOS AMIGOS SON ESTÁTICOS, NO PUEDE SER
      //TAN COMPLICADO PASARLE UNA LISTA DE NOMBRES AL SERVIDOR
      //MIRAR SI MI HASMAP DE USUARIO CONTIENE ESE AMIGO
      //Y QUE REVISE CADA VEZ QUE ALGUIEN SE CONECTA
      //AL CONECTARSE ALGUIEN
      //ITERO POR LA LISTA DE NOMBRES
      //ITERO POR EL HASHMAP DE USUARIOS
      //SI COINCIDE NOMBRE INTERCAMBIAN OBJETOS
      return null;
  }
  public synchronized void unregisterForCallback(String Nombre)
    throws RemoteException{clientList.remove(Nombre);}


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