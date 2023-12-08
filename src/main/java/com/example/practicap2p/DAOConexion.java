package com.example.practicap2p;
import java.sql.*;
import java.util.ArrayList;

public class DAOConexion {

        Connection conexion;
        public DAOConexion (){
            String url_database = "jdbc:postgresql://localhost:5432/whatsapp";
            String usuario_database = "admin";
            String password_database = "1234";

            try{
                this.conexion = DriverManager.getConnection(url_database, usuario_database, password_database);
                System.out.println("Se consiguió conectar correctamente con la base de datos");
            } catch (SQLException e){
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            }
        }

        public void insertar_Usuario(String nombre_usuario, String contrasena){
            String ISQL;
            int error;
            try {
                if (conexion != null) {
                    ISQL = "INSERT INTO usuarios (nombre_usuario, contraseña) VALUES (?,?)";

                    PreparedStatement ps = conexion.prepareStatement(ISQL);

                    ps.setString(1, nombre_usuario);
                    ps.setString(2,contrasena);

                    error = ps.executeUpdate();

                    if (error > 0){
                        System.out.println("Se insertaron los datos exitosamente");
                    } else{
                        System.out.println("Hubo un error al insertar los datos");
                    }

                    ps.close();
                }
            }catch (SQLException e){
                System.err.println("Hubo un error al conectar con la base de datos: " + e.getMessage());
            }
        }

        public int login (String nombre_usuario, String contrasena){
            String SQL;
            int loginExitoso = -1;

            try {
                if (conexion != null) {
                    SQL = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contraseña = ?";
                    PreparedStatement ps = conexion.prepareStatement(SQL);

                    ps.setString(1, nombre_usuario);
                    ps.setString(2, contrasena);

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        // Las credenciales son válidas
                        loginExitoso = rs.getInt("id");
                        System.out.println("Inicio de sesión exitoso");
                    }
                    rs.close();
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            }

            return loginExitoso;
        }

        public int existeUsuario(int id, String nombre_usuario){
            String SQL, ISQL;
            int idAmigo = -1;

            try {
                if (conexion != null) {
                    SQL = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
                    PreparedStatement ps = conexion.prepareStatement(SQL);

                    ps.setString(1, nombre_usuario);

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        // Las credenciales son válidas
                        idAmigo = rs.getInt("id");
                    }
                    rs.close();
                    ps.close();

                    return idAmigo;
                }
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            }

            return idAmigo;
        }

        public void insertarAmigo (int id, int idAmigo){
            String SQL, ISQL;
            int error;
            try {
                if (conexion != null) {

                    ISQL = "INSERT INTO amistad (amigo1,amigo2) VALUES (?,?)";

                    PreparedStatement ps = conexion.prepareStatement(ISQL);

                    ps.setInt(1,id);
                    ps.setInt(2,idAmigo);

                    error = ps.executeUpdate();

                    if (error > 0){
                    } else{
                        System.out.println("Hubo un error al insertar los datos");
                    }

                    ps.setInt(1,idAmigo);
                    ps.setInt(2,id);

                    error = ps.executeUpdate();

                    if (error > 0){
                    } else{
                        System.out.println("Hubo un error al insertar los datos");
                    }

                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            }
        }

        public ArrayList <String> obtenerAmigos(int id)  {

            ArrayList<String> listaAmigos = new ArrayList<>();
            ArrayList<Integer> listaid = new ArrayList<>();
            String SQL, ISQL;

            SQL = "SELECT * FROM amistad WHERE amigo1 = ?";


            try{
                PreparedStatement ps = conexion.prepareStatement(SQL);

                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    // Las credenciales son válidas
                    listaid.add(rs.getInt("amigo2"));
                    ISQL = "SELECT * FROM usuarios WHERE id = ?";
                    PreparedStatement ps2 = conexion.prepareStatement(ISQL);
                    ps2.setInt(1, listaid.remove(0));
                    ResultSet rs2 = ps2.executeQuery();
                    while(rs2.next()){
                        listaAmigos.add(rs2.getString("nombre_usuario"));
                    }
                    ps2.close();
                    rs2.close();
                }
                ps.close();
                rs.close();

            } catch (SQLException e){
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            }
            return listaAmigos;
        }
}
