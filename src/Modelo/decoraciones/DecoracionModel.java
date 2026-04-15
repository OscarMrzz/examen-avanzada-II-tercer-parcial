package Modelo.decoraciones;

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

public class DecoracionModel extends Conexion {

    PreparedStatement preparedStatement = null;
    Connection connection;
    String sentenciaSQL;
    ResultSet resultSet;
    DecoracionType decoracion;

    /**
     * Crea un nuevo registro de decoración
     * @param objeto - Objeto DecoracionType con los datos a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean create(DecoracionType objeto) {
        connection = getConxion();
        sentenciaSQL = "INSERT INTO decoraciones (id_decoracion, nombre_decoracion, stock_decoracion, stock_minimo_decoracion, stock_maximo_decoracion, id_proveedor_decoracion, imagen_decoracion, id_coleccion_decoracion, descripcion_decoracion, estado_decoracion) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdDecoracion());
            preparedStatement.setString(2, objeto.getNombreDecoracion());
            preparedStatement.setInt(3, objeto.getStockDecoracion());
            preparedStatement.setInt(4, objeto.getStockMinimoDecoracion());
            preparedStatement.setInt(5, objeto.getStockMaximoDecoracion());
            preparedStatement.setString(6, objeto.getIdProveedorDecoracion());
            preparedStatement.setString(7, objeto.getImagenDecoracion());
            preparedStatement.setString(8, objeto.getIdColeccionDecoracion());
            preparedStatement.setString(9, objeto.getDescripcionDecoracion());
            preparedStatement.setBoolean(10, objeto.isEstadoDecoracion());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Decoración ingresada correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DecoracionModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return false;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de crear

    /**
     * Obtiene un registro específico por su ID
     * @param id - Identificador del registro
     * @return Objeto DecoracionType o null si no existe
     */
    public DecoracionType getById(String id) {
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM decoraciones WHERE id_decoracion = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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

                DecoracionType decoracionBuscada = new DecoracionType();

                decoracionBuscada.setIdDecoracion(id_decoracion);
                decoracionBuscada.setNombreDecoracion(nombre_decoracion);
                decoracionBuscada.setStockDecoracion(stock_decoracion);
                decoracionBuscada.setStockMinimoDecoracion(stock_minimo_decoracion);
                decoracionBuscada.setStockMaximoDecoracion(stock_maximo_decoracion);
                decoracionBuscada.setIdProveedorDecoracion(id_proveedor_decoracion);
                decoracionBuscada.setImagenDecoracion(imagen_decoracion);
                decoracionBuscada.setIdColeccionDecoracion(id_coleccion_decoracion);
                decoracionBuscada.setDescripcionDecoracion(descripcion_decoracion);
                decoracionBuscada.setFechaCreacion(fecha_creacion);
                decoracionBuscada.setEstadoDecoracion(estado_decoracion);
                return decoracionBuscada;
            }

            return null;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer" + e.getMessage());
            Logger.getLogger(DecoracionModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return null;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de getById

    /**
     * Obtiene todos los registros de la tabla
     * @return ArrayList con todos los objetos
     */
    public ArrayList<DecoracionType> getAll() {
        ArrayList<DecoracionType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM decoraciones";

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
            JOptionPane.showMessageDialog(null, "No se pudo leer datos" + e.getMessage());
        }
        return lista;
    }// Fin de getAll

    /**
     * Actualiza un registro existente
     * @param objeto - Objeto DecoracionType con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(DecoracionType objeto) {
        connection = getConxion();
        sentenciaSQL = "UPDATE decoraciones SET nombre_decoracion=?, stock_decoracion=?, stock_minimo_decoracion=?, stock_maximo_decoracion=?, id_proveedor_decoracion=?, imagen_decoracion=?, id_coleccion_decoracion=?, descripcion_decoracion=?, estado_decoracion=? WHERE id_decoracion=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getNombreDecoracion());
            preparedStatement.setInt(2, objeto.getStockDecoracion());
            preparedStatement.setInt(3, objeto.getStockMinimoDecoracion());
            preparedStatement.setInt(4, objeto.getStockMaximoDecoracion());
            preparedStatement.setString(5, objeto.getIdProveedorDecoracion());
            preparedStatement.setString(6, objeto.getImagenDecoracion());
            preparedStatement.setString(7, objeto.getIdColeccionDecoracion());
            preparedStatement.setString(8, objeto.getDescripcionDecoracion());
            preparedStatement.setBoolean(9, objeto.isEstadoDecoracion());
            preparedStatement.setString(10, objeto.getIdDecoracion());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Decoración actualizada correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DecoracionModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return false;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de update

    /**
     * Elimina un registro por su ID (eliminación lógica)
     * @param id - Identificador del registro a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean delete(String id) {
        connection = getConxion();
        sentenciaSQL = "UPDATE decoraciones SET estado_decoracion=false WHERE id_decoracion=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Decoración eliminada correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DecoracionModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return false;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de delete

    /**
     * Obtiene decorations de la vista detallada de inventario
     * @return ArrayList con todas las decoraciones con información de inventario
     */
    public ArrayList<DecoracionType> getAll_vista_detallada_inventario() {
        ArrayList<DecoracionType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM vista_detallada_inventario";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Mapear campos de la vista a DecoracionType
                String id_decoracion = resultSet.getString("id_decoracion");
                String nombre_decoracion = resultSet.getString("nombre_decoracion");
                int stock_decoracion = resultSet.getInt("stock_decoracion");
                int stock_minimo_decoracion = resultSet.getInt("stock_minimo_decoracion");
                int stock_maximo_decoracion = resultSet.getInt("stock_maximo_decoracion");
                String id_proveedor_decoracion = resultSet.getString("id_proveedor");
                String descripcion_decoracion = resultSet.getString("descripcion_decoracion");
                boolean estado_decoracion = resultSet.getBoolean("estado_decoracion");

                DecoracionType decoracion = new DecoracionType();
                decoracion.setIdDecoracion(id_decoracion);
                decoracion.setNombreDecoracion(nombre_decoracion);
                decoracion.setStockDecoracion(stock_decoracion);
                decoracion.setStockMinimoDecoracion(stock_minimo_decoracion);
                decoracion.setStockMaximoDecoracion(stock_maximo_decoracion);
                decoracion.setIdProveedorDecoracion(id_proveedor_decoracion);
                decoracion.setDescripcionDecoracion(descripcion_decoracion);
                decoracion.setEstadoDecoracion(estado_decoracion);
                lista.add(decoracion);
            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos de vista de inventario" + e.getMessage());
        }
        return lista;
    }// Fin de getAll_vista_detallada_inventario
}
