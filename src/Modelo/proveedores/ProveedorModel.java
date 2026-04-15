package Modelo.proveedores;

import Modelo.Conexion;
import Type.proveedores.ProveedorType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ProveedorModel extends Conexion {

    PreparedStatement preparedStatement = null;
    Connection connection;
    String sentenciaSQL;
    ResultSet resultSet;
    ProveedorType proveedor;

    /**
     * Crea un nuevo registro de proveedor
     * @param objeto - Objeto ProveedorType con los datos a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean create(ProveedorType objeto) {
        connection = getConxion();
        sentenciaSQL = "INSERT INTO proveedores (id_proveedor, nombre_proveedor, rtn_proveedor, telefono_proveedor, email_proveedor, direccion_proveedor, contacto_proveedor, estado_proveedor) VALUES (?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdProveedor());
            preparedStatement.setString(2, objeto.getNombreProveedor());
            preparedStatement.setString(3, objeto.getRtnProveedor());
            preparedStatement.setString(4, objeto.getTelefonoProveedor());
            preparedStatement.setString(5, objeto.getEmailProveedor());
            preparedStatement.setString(6, objeto.getDireccionProveedor());
            preparedStatement.setString(7, objeto.getContactoProveedor());
            preparedStatement.setBoolean(8, objeto.isEstadoProveedor());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Proveedor ingresado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ProveedorModel.class.getName()).log(Level.SEVERE, null, e);
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
     * @return Objeto ProveedorType o null si no existe
     */
    public ProveedorType getById(String id) {
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM proveedores WHERE id_proveedor = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id_proveedor = resultSet.getString(1);
                String nombre_proveedor = resultSet.getString(2);
                String rtn_proveedor = resultSet.getString(3);
                String telefono_proveedor = resultSet.getString(4);
                String email_proveedor = resultSet.getString(5);
                String direccion_proveedor = resultSet.getString(6);
                String contacto_proveedor = resultSet.getString(7);
                java.sql.Timestamp fecha_registro = resultSet.getTimestamp(8);
                boolean estado_proveedor = resultSet.getBoolean(9);

                ProveedorType proveedorBuscado = new ProveedorType();

                proveedorBuscado.setIdProveedor(id_proveedor);
                proveedorBuscado.setNombreProveedor(nombre_proveedor);
                proveedorBuscado.setRtnProveedor(rtn_proveedor);
                proveedorBuscado.setTelefonoProveedor(telefono_proveedor);
                proveedorBuscado.setEmailProveedor(email_proveedor);
                proveedorBuscado.setDireccionProveedor(direccion_proveedor);
                proveedorBuscado.setContactoProveedor(contacto_proveedor);
                proveedorBuscado.setFechaRegistro(fecha_registro);
                proveedorBuscado.setEstadoProveedor(estado_proveedor);
                return proveedorBuscado;
            }

            return null;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer" + e.getMessage());
            Logger.getLogger(ProveedorModel.class.getName()).log(Level.SEVERE, null, e);
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
    public ArrayList<ProveedorType> getAll() {
        ArrayList<ProveedorType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM proveedores";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_proveedor = resultSet.getString(1);
                String nombre_proveedor = resultSet.getString(2);
                String rtn_proveedor = resultSet.getString(3);
                String telefono_proveedor = resultSet.getString(4);
                String email_proveedor = resultSet.getString(5);
                String direccion_proveedor = resultSet.getString(6);
                String contacto_proveedor = resultSet.getString(7);
                java.sql.Timestamp fecha_registro = resultSet.getTimestamp(8);
                boolean estado_proveedor = resultSet.getBoolean(9);

                ProveedorType proveedor = new ProveedorType();

                proveedor.setIdProveedor(id_proveedor);
                proveedor.setNombreProveedor(nombre_proveedor);
                proveedor.setRtnProveedor(rtn_proveedor);
                proveedor.setTelefonoProveedor(telefono_proveedor);
                proveedor.setEmailProveedor(email_proveedor);
                proveedor.setDireccionProveedor(direccion_proveedor);
                proveedor.setContactoProveedor(contacto_proveedor);
                proveedor.setFechaRegistro(fecha_registro);
                proveedor.setEstadoProveedor(estado_proveedor);
                lista.add(proveedor);

            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos" + e.getMessage());
        }
        return lista;
    }// Fin de getAll

    /**
     * Actualiza un registro existente
     * @param objeto - Objeto ProveedorType con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(ProveedorType objeto) {
        connection = getConxion();
        sentenciaSQL = "UPDATE proveedores SET nombre_proveedor=?, rtn_proveedor=?, telefono_proveedor=?, email_proveedor=?, direccion_proveedor=?, contacto_proveedor=?, estado_proveedor=? WHERE id_proveedor=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getNombreProveedor());
            preparedStatement.setString(2, objeto.getRtnProveedor());
            preparedStatement.setString(3, objeto.getTelefonoProveedor());
            preparedStatement.setString(4, objeto.getEmailProveedor());
            preparedStatement.setString(5, objeto.getDireccionProveedor());
            preparedStatement.setString(6, objeto.getContactoProveedor());
            preparedStatement.setBoolean(7, objeto.isEstadoProveedor());
            preparedStatement.setString(8, objeto.getIdProveedor());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Proveedor actualizado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ProveedorModel.class.getName()).log(Level.SEVERE, null, e);
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
        sentenciaSQL = "UPDATE proveedores SET estado_proveedor=false WHERE id_proveedor=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Proveedor eliminado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ProveedorModel.class.getName()).log(Level.SEVERE, null, e);
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
}
