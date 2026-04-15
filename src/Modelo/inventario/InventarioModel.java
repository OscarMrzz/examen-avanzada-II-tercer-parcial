package Modelo.inventario;

import Modelo.Conexion;
import Type.decoraciones.DecoracionType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Modelo para gestionar el inventario de decoraciones
 * 
 * @author ossca
 */
public class InventarioModel extends Conexion {

    private static final Logger logger = Logger.getLogger(InventarioModel.class.getName());
    private Connection connection;
    private PreparedStatement preparedStatement;
    private String sentenciaSQL;
    private ResultSet resultSet;

    /**
     * Obtiene todos los productos del inventario con información de stock
     * @return ArrayList con todos los productos del inventario
     */
    public ArrayList<DecoracionType> getAll() {
        ArrayList<DecoracionType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM decoraciones ORDER BY nombre_decoracion";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_decoracion = resultSet.getString(1);
                String nombre_decoracion = resultSet.getString(2);
                int stock_decoracion = resultSet.getInt(3);
                int stock_minimo_decoracion = resultSet.getInt(4);
                int stock_maximo_decoracion = resultSet.getInt(5);
                String id_proveedor_decoracion = resultSet.getString(6);
                String imagen_decoracion = resultSet.getString(7);
                String id_coleccion_decoracion = resultSet.getString(8);
                String descripcion_decoracion = resultSet.getString(9);
                java.sql.Timestamp fecha_creacion = resultSet.getTimestamp(10);
                boolean estado_decoracion = resultSet.getBoolean(11);

                DecoracionType decoracion = new DecoracionType();

                decoracion.setIdDecoracion(id_decoracion);
                decoracion.setNombreDecoracion(nombre_decoracion);
                decoracion.setStockDecoracion(stock_decoracion);
                decoracion.setStockMinimoDecoracion(stock_minimo_decoracion);
                decoracion.setStockMaximoDecoracion(stock_maximo_decoracion);
                decoracion.setIdProveedorDecoracion(id_proveedor_decoracion);
                decoracion.setImagenDecoracion(imagen_decoracion);
                decoracion.setIdColeccionDecoracion(id_coleccion_decoracion);
                decoracion.setDescripcionDecoracion(descripcion_decoracion);
                decoracion.setFechaCreacion(fecha_creacion);
                decoracion.setEstadoDecoracion(estado_decoracion);
                lista.add(decoracion);

            }
            connection.close();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener inventario: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "No se pudo leer datos del inventario: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene productos con stock bajo (menor o igual al stock mínimo)
     * @return ArrayList con productos que necesitan reabastecimiento
     */
    public ArrayList<DecoracionType> getStockBajo() {
        ArrayList<DecoracionType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM decoraciones WHERE stock_decoracion <= stock_minimo_decoracion AND estado_decoracion = true ORDER BY nombre_decoracion";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_decoracion = resultSet.getString(1);
                String nombre_decoracion = resultSet.getString(2);
                int stock_decoracion = resultSet.getInt(3);
                int stock_minimo_decoracion = resultSet.getInt(4);
                int stock_maximo_decoracion = resultSet.getInt(5);
                String id_proveedor_decoracion = resultSet.getString(6);
                String imagen_decoracion = resultSet.getString(7);
                String id_coleccion_decoracion = resultSet.getString(8);
                String descripcion_decoracion = resultSet.getString(9);
                java.sql.Timestamp fecha_creacion = resultSet.getTimestamp(10);
                boolean estado_decoracion = resultSet.getBoolean(11);

                DecoracionType decoracion = new DecoracionType();

                decoracion.setIdDecoracion(id_decoracion);
                decoracion.setNombreDecoracion(nombre_decoracion);
                decoracion.setStockDecoracion(stock_decoracion);
                decoracion.setStockMinimoDecoracion(stock_minimo_decoracion);
                decoracion.setStockMaximoDecoracion(stock_maximo_decoracion);
                decoracion.setIdProveedorDecoracion(id_proveedor_decoracion);
                decoracion.setImagenDecoracion(imagen_decoracion);
                decoracion.setIdColeccionDecoracion(id_coleccion_decoracion);
                decoracion.setDescripcionDecoracion(descripcion_decoracion);
                decoracion.setFechaCreacion(fecha_creacion);
                decoracion.setEstadoDecoracion(estado_decoracion);
                lista.add(decoracion);

            }
            connection.close();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener stock bajo: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "No se pudo leer datos de stock bajo: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza el stock de un producto
     * @param idDecoracion ID del producto a actualizar
     * @param nuevoStock Nuevo valor de stock
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizarStock(String idDecoracion, int nuevoStock) {
        connection = getConxion();
        sentenciaSQL = "UPDATE decoraciones SET stock_decoracion = ? WHERE id_decoracion = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setInt(1, nuevoStock);
            preparedStatement.setString(2, idDecoracion);
            int filasAfectadas = preparedStatement.executeUpdate();
            
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Stock actualizado correctamente");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el producto para actualizar");
                return false;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar stock: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Error al actualizar stock: " + e.getMessage());
            return false;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error al cerrar conexión: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Realiza un ajuste de inventario (entrada o salida de stock)
     * @param idDecoracion ID del producto
     * @param cantidad Cantidad a ajustar (positiva para entrada, negativa para salida)
     * @param tipoAjuste Tipo de ajuste (ENTRADA, SALIDA)
     * @return true si el ajuste fue exitoso, false en caso contrario
     */
    public boolean ajustarStock(String idDecoracion, int cantidad, String tipoAjuste) {
        connection = getConxion();
        
        // Primero obtener el stock actual
        String sqlSelect = "SELECT stock_decoracion FROM decoraciones WHERE id_decoracion = ?";
        int stockActual = 0;
        
        try {
            PreparedStatement stmtSelect = connection.prepareStatement(sqlSelect);
            stmtSelect.setString(1, idDecoracion);
            ResultSet rs = stmtSelect.executeQuery();
            
            if (rs.next()) {
                stockActual = rs.getInt("stock_decoracion");
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado");
                return false;
            }
            rs.close();
            stmtSelect.close();
            
            // Calcular nuevo stock
            int nuevoStock = stockActual + cantidad;
            
            // Validar que el stock no sea negativo
            if (nuevoStock < 0) {
                JOptionPane.showMessageDialog(null, "No se puede realizar el ajuste. Stock insuficiente.");
                return false;
            }
            
            // Actualizar el stock
            String sqlUpdate = "UPDATE decoraciones SET stock_decoracion = ? WHERE id_decoracion = ?";
            PreparedStatement stmtUpdate = connection.prepareStatement(sqlUpdate);
            stmtUpdate.setInt(1, nuevoStock);
            stmtUpdate.setString(2, idDecoracion);
            
            int filasAfectadas = stmtUpdate.executeUpdate();
            stmtUpdate.close();
            
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Ajuste de inventario realizado correctamente. " +
                        tipoAjuste + ": " + Math.abs(cantidad) + " unidades. Stock actual: " + nuevoStock);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo realizar el ajuste de inventario");
                return false;
            }
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al realizar ajuste de stock: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Error al realizar ajuste de stock: " + e.getMessage());
            return false;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error al cerrar conexión: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Busca productos en el inventario según el texto ingresado
     * @param textoBusqueda Texto a buscar
     * @return ArrayList con productos que coinciden con la búsqueda
     */
    public ArrayList<DecoracionType> buscar(String textoBusqueda) {
        ArrayList<DecoracionType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM decoraciones WHERE " +
                "nombre_decoracion LIKE ? OR " +
                "descripcion_decoracion LIKE ? OR " +
                "id_coleccion_decoracion LIKE ? " +
                "ORDER BY nombre_decoracion";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            String patron = "%" + textoBusqueda + "%";
            preparedStatement.setString(1, patron);
            preparedStatement.setString(2, patron);
            preparedStatement.setString(3, patron);
            
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_decoracion = resultSet.getString(1);
                String nombre_decoracion = resultSet.getString(2);
                int stock_decoracion = resultSet.getInt(3);
                int stock_minimo_decoracion = resultSet.getInt(4);
                int stock_maximo_decoracion = resultSet.getInt(5);
                String id_proveedor_decoracion = resultSet.getString(6);
                String imagen_decoracion = resultSet.getString(7);
                String id_coleccion_decoracion = resultSet.getString(8);
                String descripcion_decoracion = resultSet.getString(9);
                java.sql.Timestamp fecha_creacion = resultSet.getTimestamp(10);
                boolean estado_decoracion = resultSet.getBoolean(11);

                DecoracionType decoracion = new DecoracionType();

                decoracion.setIdDecoracion(id_decoracion);
                decoracion.setNombreDecoracion(nombre_decoracion);
                decoracion.setStockDecoracion(stock_decoracion);
                decoracion.setStockMinimoDecoracion(stock_minimo_decoracion);
                decoracion.setStockMaximoDecoracion(stock_maximo_decoracion);
                decoracion.setIdProveedorDecoracion(id_proveedor_decoracion);
                decoracion.setImagenDecoracion(imagen_decoracion);
                decoracion.setIdColeccionDecoracion(id_coleccion_decoracion);
                decoracion.setDescripcionDecoracion(descripcion_decoracion);
                decoracion.setFechaCreacion(fecha_creacion);
                decoracion.setEstadoDecoracion(estado_decoracion);
                lista.add(decoracion);

            }
            connection.close();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar en inventario: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Error al buscar en inventario: " + e.getMessage());
        }
        return lista;
    }
}
