package com.example.practicap2p;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class CallbackServer  {
  public static void main(String args[]) {
    ////AQUI EMPIEZA LO DE ANTES\\\\
    String registryURL;
    try{
      startRegistry(6789);
      CallbackServerImpl exportedObj = new CallbackServerImpl();
      registryURL = "rmi://localhost:6789/P2P";
      Naming.rebind(registryURL, exportedObj);//Aquí es donde envias cosas al objeto

    }
    catch (Exception re) {
      System.out.println(
        "Excepción en HelloServer.main: " + re);
    }
  }


  private static void startRegistry(int RMIPortNum)
    throws RemoteException{
    try {
      Registry registry = 
        LocateRegistry.getRegistry(RMIPortNum);
      registry.list( );  
        // This call will throw an exception
        // if the registry does not already exist
    }
    catch (RemoteException e) { 
      // No valid registry at that port.
      Registry registry = 
        LocateRegistry.createRegistry(RMIPortNum);
    }
  } // end startRegistry

} // end class
/*
File file = new File("C:\\Users\\ldiaz\\OneDrive\\Escritorio\\UNI AÑO 3\\CODIS\\PRACTICA-CALLBACK\\datos.txt");
    String linea;
    BufferedReader lector;
    try { //Leo el archivo
      lector = new BufferedReader(new FileReader(file));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    try { //Voy leyendo cada linea
      linea = lector.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    while(linea!=null){//Mientras la siguiente linea que lea no sea nula...
      //System.out.println(linea);//Por ahora lo imprimo y ya
      //Aquí tengo que enviarselo al cliente, pero tengo que hacerlo con hilo
      try {//Espero un segundo
        linea = lector.readLine();
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException | IOException e) {
        throw new RuntimeException(e);
      }
    }
 */