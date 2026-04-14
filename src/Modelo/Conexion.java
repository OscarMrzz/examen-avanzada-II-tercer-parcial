package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    Connection connection = null;

    private String dbName = "db_decoraciones";
    private String dbUser = "root";
    private String dbPassword = "12345678";
    private String dbPort = "3306";
    private String dbURL = "jdbc:mysql://localhost:" + dbPort + "/" + dbName;

    public Connection getConxion() {
        System.out.println("iniciando conexion");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            System.out.println("Conexión exitosa");

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver: " + e.getMessage());

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());

        }
        return connection;
    }

    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        conexion.getConxion();
    }

}