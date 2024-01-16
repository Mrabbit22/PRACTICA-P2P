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
                        int id = login(nombre_usuario,contrasena);
                        insertarAmigo(id,9);

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
                    }
                    rs.close();
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            }

            return loginExitoso;
        }

        public int existeUsuario(String nombre_usuario){
            String SQL;
            int id = -1;

            try {
                if (conexion != null) {
                    SQL = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
                    PreparedStatement ps = conexion.prepareStatement(SQL);

                    ps.setString(1, nombre_usuario);

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        // Las credenciales son válidas
                        id = rs.getInt("id");
                    }
                    rs.close();
                    ps.close();

                    return id;
                }
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            }

            return id;
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

        public void anadirSolicitud(int yo, int amigo){
            String ISQL;
            int error;
            try {
                if (conexion != null) {

                    ISQL = "INSERT INTO solicitudes (solicitante,solicitado) VALUES (?,?)";

                    PreparedStatement ps = conexion.prepareStatement(ISQL);

                    ps.setInt(1,yo);
                    ps.setInt(2,amigo);

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

        public boolean existeAmistad (int yo, int amigo){
            String SQL;
            boolean id = false;

            try {
                if (conexion != null) {
                    SQL = "SELECT * FROM amistad WHERE amigo1 = ? and amigo2 = ?";
                    PreparedStatement ps = conexion.prepareStatement(SQL);

                    ps.setInt(1, yo);
                    ps.setInt(2,amigo);

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        // Las credenciales son válidas
                        id = true;
                    }
                    rs.close();
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            }

            return id;
        }

        public void eliminarSolicitud (int yo, int amigo){
        String ISQL;
        int error;
        try {
            if (conexion != null) {

                ISQL = "DELETE FROM solicitudes where (solicitante = ? and solicitado = ?)";

                PreparedStatement ps = conexion.prepareStatement(ISQL);

                ps.setInt(1,yo);
                ps.setInt(2,amigo);

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

    public boolean existeSolicitud (int yo, int amigo){
        String SQL;
        boolean id = false;

        try {
            if (conexion != null) {
                SQL = "SELECT * FROM solicitudes WHERE solicitante = ? and solicitado = ?";
                PreparedStatement ps = conexion.prepareStatement(SQL);

                ps.setInt(1, yo);
                ps.setInt(2,amigo);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    // Las credenciales son válidas
                    id = true;
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }

        return id;
    }

    public ArrayList <String> obtenerSolicitudes (int yo){
        ArrayList<String> listaAmigos = new ArrayList<>();
        int id;
        String SQL, ISQL;

        SQL = "SELECT * FROM solicitudes WHERE solicitado = ?";


        try{
            PreparedStatement ps = conexion.prepareStatement(SQL);

            ps.setInt(1, yo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Las credenciales son válidas
                id = rs.getInt("solicitante");
                ISQL = "SELECT * FROM usuarios WHERE id = ?";
                PreparedStatement ps2 = conexion.prepareStatement(ISQL);
                ps2.setInt(1, id);
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void eliminarAmistad (int yo, int amigo){
        String ISQL;
        int error;
        try {
            if (conexion != null) {

                ISQL = "DELETE FROM amistad WHERE (amigo1 = ? AND amigo2 = ?)";

                PreparedStatement ps = conexion.prepareStatement(ISQL);

                ps.setInt(1,yo);
                ps.setInt(2,amigo);

                error = ps.executeUpdate();

                if (error > 0){
                } else{
                    System.out.println("Hubo un error al insertar los datos");
                }

                ps.setInt(1,amigo);
                ps.setInt(2,yo);

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

    public void cambiarContrasena(String nombre_usuario, String nuevaContraseña){
        int error;
        String SQL;

        try {
            if (conexion != null) {
                SQL = "UPDATE usuarios SET contraseña = ? WHERE nombre_usuario = ?";
                PreparedStatement ps = conexion.prepareStatement(SQL);

                ps.setString(1, nuevaContraseña);
                ps.setString(2,nombre_usuario);

               error = ps.executeUpdate();
               if(error > 0){
                   System.out.println("Todo bien");
               } else{
                   System.out.println("Hubo un error");
               }
                ps.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

}
