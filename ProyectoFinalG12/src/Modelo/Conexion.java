package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mariadb://localhost:3306/gp12_spa_entrededos";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    // Constructor vacío
    public Conexion() {
    }

    public static Connection getConexion() {
        if (connection == null) {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("Conexion establecida con la base de datos.");
            } catch (ClassNotFoundException e) {
                System.out.println("Error no se encontro el Driver a Mariabd" + e.getMessage());
            } catch (SQLException ex) {
                System.out.println("Error de conexion" + ex.getMessage());
            }
        }
        return connection;
    }

    // Método para cerrar la conexión
    public static void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexion cerrada correctamente.");
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }
}
