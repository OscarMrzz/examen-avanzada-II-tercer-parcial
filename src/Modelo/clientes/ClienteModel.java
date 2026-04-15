package Modelo.clientes;

import Modelo.Conexion;
import Type.clientes.ClienteType;
import Type.clientes.TipoCliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ClienteModel extends Conexion {

    PreparedStatement preparedStatement = null;
    Connection connection;
    String sentenciaSQL;
    ResultSet resultSet;
    ClienteType cliente;

    /**
     * Crea un nuevo registro de cliente
     * @param objeto - Objeto ClienteType con los datos a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean create(ClienteType objeto) {
        connection = getConxion();
        sentenciaSQL = "INSERT INTO clientes (id_cliente, nombre_cliente, rtn_cliente, tipo_cliente, telefono_cliente, email_cliente, direccion_cliente, estado_cliente) VALUES (?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdCliente());
            preparedStatement.setString(2, objeto.getNombreCliente());
            preparedStatement.setString(3, objeto.getRtnCliente());
            preparedStatement.setString(4, objeto.getTipoCliente().toString());
            preparedStatement.setString(5, objeto.getTelefonoCliente());
            preparedStatement.setString(6, objeto.getEmailCliente());
            preparedStatement.setString(7, objeto.getDireccionCliente());
            preparedStatement.setBoolean(8, objeto.isEstadoCliente());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Cliente ingresado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, e);
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
     * @return Objeto ClienteType o null si no existe
     */
    public ClienteType getById(String id) {
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM clientes WHERE id_cliente = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id_cliente = resultSet.getString(1);
                String nombre_cliente = resultSet.getString(2);
                String rtn_cliente = resultSet.getString(3);
                String tipo_cliente = resultSet.getString(4);
                String telefono_cliente = resultSet.getString(5);
                String email_cliente = resultSet.getString(6);
                String direccion_cliente = resultSet.getString(7);
                java.sql.Timestamp fecha_registro = resultSet.getTimestamp(8);
                boolean estado_cliente = resultSet.getBoolean(9);

                ClienteType clienteBuscado = new ClienteType();

                clienteBuscado.setIdCliente(id_cliente);
                clienteBuscado.setNombreCliente(nombre_cliente);
                clienteBuscado.setRtnCliente(rtn_cliente);
                clienteBuscado.setTipoCliente(TipoCliente.valueOf(tipo_cliente));
                clienteBuscado.setTelefonoCliente(telefono_cliente);
                clienteBuscado.setEmailCliente(email_cliente);
                clienteBuscado.setDireccionCliente(direccion_cliente);
                clienteBuscado.setFechaRegistro(fecha_registro);
                clienteBuscado.setEstadoCliente(estado_cliente);
                return clienteBuscado;
            }

            return null;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer" + e.getMessage());
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, e);
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
    public ArrayList<ClienteType> getAll() {
        ArrayList<ClienteType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM clientes";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_cliente = resultSet.getString(1);
                String nombre_cliente = resultSet.getString(2);
                String rtn_cliente = resultSet.getString(3);
                String tipo_cliente = resultSet.getString(4);
                String telefono_cliente = resultSet.getString(5);
                String email_cliente = resultSet.getString(6);
                String direccion_cliente = resultSet.getString(7);
                java.sql.Timestamp fecha_registro = resultSet.getTimestamp(8);
                boolean estado_cliente = resultSet.getBoolean(9);

                ClienteType cliente = new ClienteType();

                cliente.setIdCliente(id_cliente);
                cliente.setNombreCliente(nombre_cliente);
                cliente.setRtnCliente(rtn_cliente);
                cliente.setTipoCliente(TipoCliente.valueOf(tipo_cliente));
                cliente.setTelefonoCliente(telefono_cliente);
                cliente.setEmailCliente(email_cliente);
                cliente.setDireccionCliente(direccion_cliente);
                cliente.setFechaRegistro(fecha_registro);
                cliente.setEstadoCliente(estado_cliente);
                lista.add(cliente);

            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos" + e.getMessage());
        }
        return lista;
    }// Fin de getAll

    /**
     * Actualiza un registro existente
     * @param objeto - Objeto ClienteType con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(ClienteType objeto) {
        connection = getConxion();
        sentenciaSQL = "UPDATE clientes SET nombre_cliente=?, rtn_cliente=?, tipo_cliente=?, telefono_cliente=?, email_cliente=?, direccion_cliente=?, estado_cliente=? WHERE id_cliente=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getNombreCliente());
            preparedStatement.setString(2, objeto.getRtnCliente());
            preparedStatement.setString(3, objeto.getTipoCliente().toString());
            preparedStatement.setString(4, objeto.getTelefonoCliente());
            preparedStatement.setString(5, objeto.getEmailCliente());
            preparedStatement.setString(6, objeto.getDireccionCliente());
            preparedStatement.setBoolean(7, objeto.isEstadoCliente());
            preparedStatement.setString(8, objeto.getIdCliente());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, e);
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
        sentenciaSQL = "UPDATE clientes SET estado_cliente=false WHERE id_cliente=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, e);
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
