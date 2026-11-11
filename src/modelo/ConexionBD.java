/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Miguel Aguilar
 */
import java.io.InputStream;
import java.sql.DriverManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

    private static final Properties propiedades = new Properties();

    static {
        try (InputStream input = ConexionBD.class.getResourceAsStream("/modelo/database.properties")) {
            if (input == null) {
                throw new RuntimeException("No se pudo encontrar el archivo database.properties");
            }
            propiedades.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo database.properties", e);
        }
    }

    public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName(propiedades.getProperty("db.driver"));
            return DriverManager.getConnection(propiedades.getProperty("db.url"),
                    propiedades.getProperty("db.user"),
                    propiedades.getProperty("db.password"));
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontro el driver de MySQL: " + e.getMessage(), e);
        }
    }

}
