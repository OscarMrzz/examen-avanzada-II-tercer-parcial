package Modelo.colecciones;

import Modelo.Conexion;
import Type.colecciones.ColeccionType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ColeccionModel extends Conexion {

    PreparedStatement preparedStatement = null;
    Connection connection;
    String sentenciaSQL;
    ResultSet resultSet;
    ColeccionType coleccion;

    /**
     * Crea un nuevo registro de colección
     * 
     * @param objeto - Objeto ColeccionType con los datos a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean create(ColeccionType objeto) {
        connection = getConxion();
        sentenciaSQL = "INSERT INTO colecciones (id_coleccion, nombre_coleccion, disenador_coleccion, num_coleccion_coleccion, anio_coleccion, descripcion_coleccion, estado_coleccion) VALUES (?,?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdColeccion());
            preparedStatement.setString(2, objeto.getNombreColeccion());
            preparedStatement.setString(3, objeto.getDisenadorColeccion());
            preparedStatement.setString(4, objeto.getNumColeccionColeccion());
            preparedStatement.setInt(5, objeto.getAnioColeccion());
            preparedStatement.setString(6, objeto.getDescripcionColeccion());
            preparedStatement.setBoolean(7, objeto.isEstadoColeccion());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Colección ingresada correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ColeccionModel.class.getName()).log(Level.SEVERE, null, e);
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
     * 
     * @param id - Identificador del registro
     * @return Objeto ColeccionType o null si no existe
     */
    public ColeccionType getById(String id) {
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM colecciones WHERE id_coleccion = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id_coleccion = resultSet.getString(1);
                String nombre_coleccion = resultSet.getString(2);
                String disenador_coleccion = resultSet.getString(3);
                String num_coleccion_coleccion = resultSet.getString(4);
                int anio_coleccion = resultSet.getInt(5);
                String descripcion_coleccion = resultSet.getString(6);
                java.sql.Timestamp fecha_creacion = resultSet.getTimestamp(7);
                boolean estado_coleccion = resultSet.getBoolean(8);

                ColeccionType coleccionBuscada = new ColeccionType();

                coleccionBuscada.setIdColeccion(id_coleccion);
                coleccionBuscada.setNombreColeccion(nombre_coleccion);
                coleccionBuscada.setDisenadorColeccion(disenador_coleccion);
                coleccionBuscada.setNumColeccionColeccion(num_coleccion_coleccion);
                coleccionBuscada.setAnioColeccion(anio_coleccion);
                coleccionBuscada.setDescripcionColeccion(descripcion_coleccion);
                coleccionBuscada.setFechaCreacion(fecha_creacion);
                coleccionBuscada.setEstadoColeccion(estado_coleccion);
                return coleccionBuscada;
            }

            return null;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer" + e.getMessage());
            Logger.getLogger(ColeccionModel.class.getName()).log(Level.SEVERE, null, e);
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
     * 
     * @return ArrayList con todos los objetos
     */
    public ArrayList<ColeccionType> getAll() {
        ArrayList<ColeccionType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM colecciones";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_coleccion = resultSet.getString(1);
                String nombre_coleccion = resultSet.getString(2);
                String disenador_coleccion = resultSet.getString(3);
                String num_coleccion_coleccion = resultSet.getString(4);
                int anio_coleccion = resultSet.getInt(5);
                String descripcion_coleccion = resultSet.getString(6);
                java.sql.Timestamp fecha_creacion = resultSet.getTimestamp(7);
                boolean estado_coleccion = resultSet.getBoolean(8);

                ColeccionType coleccion = new ColeccionType();

                coleccion.setIdColeccion(id_coleccion);
                coleccion.setNombreColeccion(nombre_coleccion);
                coleccion.setDisenadorColeccion(disenador_coleccion);
                coleccion.setNumColeccionColeccion(num_coleccion_coleccion);
                coleccion.setAnioColeccion(anio_coleccion);
                coleccion.setDescripcionColeccion(descripcion_coleccion);
                coleccion.setFechaCreacion(fecha_creacion);
                coleccion.setEstadoColeccion(estado_coleccion);
                lista.add(coleccion);

            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos" + e.getMessage());
        }
        return lista;
    }// Fin de getAll

    /**
     * Actualiza un registro existente
     * 
     * @param objeto - Objeto ColeccionType con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(ColeccionType objeto) {
        connection = getConxion();
        sentenciaSQL = "UPDATE colecciones SET nombre_coleccion=?, disenador_coleccion=?, num_coleccion_coleccion=?, anio_coleccion=?, descripcion_coleccion=?, estado_coleccion=? WHERE id_coleccion=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getNombreColeccion());
            preparedStatement.setString(2, objeto.getDisenadorColeccion());
            preparedStatement.setString(3, objeto.getNumColeccionColeccion());
            preparedStatement.setInt(4, objeto.getAnioColeccion());
            preparedStatement.setString(5, objeto.getDescripcionColeccion());
            preparedStatement.setBoolean(6, objeto.isEstadoColeccion());
            preparedStatement.setString(7, objeto.getIdColeccion());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Colección actualizada correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ColeccionModel.class.getName()).log(Level.SEVERE, null, e);
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
     * 
     * @param id - Identificador del registro a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean delete(String id) {
        connection = getConxion();
        sentenciaSQL = "UPDATE colecciones SET estado_coleccion=false WHERE id_coleccion=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Colección eliminada correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ColeccionModel.class.getName()).log(Level.SEVERE, null, e);
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
     * Obtiene el número de decoraciones asociadas a una colección
     * 
     * @param idColeccion ID de la colección
     * @return número de decoraciones en la colección
     */
    public int getConteoDecoracionesPorColeccion(String idColeccion) {
        connection = getConxion();
        sentenciaSQL = "SELECT COUNT(*) as total FROM decoraciones WHERE id_coleccion_decoracion = ? AND estado_decoracion = true";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, idColeccion);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

            return 0;
        } catch (SQLException e) {
            Logger.getLogger(ColeccionModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return 0;
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(ColeccionModel.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
