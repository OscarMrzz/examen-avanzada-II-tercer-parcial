package Modelo.ventas;

import Modelo.Conexion;
import Type.ventas.DetalleVentaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DetalleVentaModel extends Conexion {

    PreparedStatement preparedStatement = null;
    Connection connection;
    String sentenciaSQL;
    ResultSet resultSet;
    DetalleVentaType detalleVenta;

    /**
     * Crea un nuevo registro de detalle de venta
     * @param objeto - Objeto DetalleVentaType con los datos a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean create(DetalleVentaType objeto) {
        connection = getConxion();
        sentenciaSQL = "INSERT INTO detalle_venta (id_detalle_venta, id_venta_detalle, id_decoracion_detalle, cantidad_detalle_venta, precio_unitario_venta, subtotal_detalle_venta, descuento_detalle) VALUES (?,?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdDetalleVenta());
            preparedStatement.setString(2, objeto.getIdVentaDetalle());
            preparedStatement.setString(3, objeto.getIdDecoracionDetalle());
            preparedStatement.setInt(4, objeto.getCantidadDetalleVenta());
            preparedStatement.setDouble(5, objeto.getPrecioUnitarioVenta());
            preparedStatement.setDouble(6, objeto.getSubtotalDetalleVenta());
            preparedStatement.setDouble(7, objeto.getDescuentoDetalle());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Detalle de venta ingresado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DetalleVentaModel.class.getName()).log(Level.SEVERE, null, e);
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
     * @return Objeto DetalleVentaType o null si no existe
     */
    public DetalleVentaType getById(String id) {
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM detalle_venta WHERE id_detalle_venta = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id_detalle_venta = resultSet.getString(1);
                String id_venta_detalle = resultSet.getString(2);
                String id_decoracion_detalle = resultSet.getString(3);
                int cantidad_detalle_venta = resultSet.getInt(4);
                double precio_unitario_venta = resultSet.getDouble(5);
                double subtotal_detalle_venta = resultSet.getDouble(6);
                double descuento_detalle = resultSet.getDouble(7);

                DetalleVentaType detalleBuscado = new DetalleVentaType();

                detalleBuscado.setIdDetalleVenta(id_detalle_venta);
                detalleBuscado.setIdVentaDetalle(id_venta_detalle);
                detalleBuscado.setIdDecoracionDetalle(id_decoracion_detalle);
                detalleBuscado.setCantidadDetalleVenta(cantidad_detalle_venta);
                detalleBuscado.setPrecioUnitarioVenta(precio_unitario_venta);
                detalleBuscado.setSubtotalDetalleVenta(subtotal_detalle_venta);
                detalleBuscado.setDescuentoDetalle(descuento_detalle);
                return detalleBuscado;
            }

            return null;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer" + e.getMessage());
            Logger.getLogger(DetalleVentaModel.class.getName()).log(Level.SEVERE, null, e);
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
    public ArrayList<DetalleVentaType> getAll() {
        ArrayList<DetalleVentaType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM detalle_venta";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_detalle_venta = resultSet.getString(1);
                String id_venta_detalle = resultSet.getString(2);
                String id_decoracion_detalle = resultSet.getString(3);
                int cantidad_detalle_venta = resultSet.getInt(4);
                double precio_unitario_venta = resultSet.getDouble(5);
                double subtotal_detalle_venta = resultSet.getDouble(6);
                double descuento_detalle = resultSet.getDouble(7);

                DetalleVentaType detalle = new DetalleVentaType();

                detalle.setIdDetalleVenta(id_detalle_venta);
                detalle.setIdVentaDetalle(id_venta_detalle);
                detalle.setIdDecoracionDetalle(id_decoracion_detalle);
                detalle.setCantidadDetalleVenta(cantidad_detalle_venta);
                detalle.setPrecioUnitarioVenta(precio_unitario_venta);
                detalle.setSubtotalDetalleVenta(subtotal_detalle_venta);
                detalle.setDescuentoDetalle(descuento_detalle);
                lista.add(detalle);

            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos" + e.getMessage());
        }
        return lista;
    }// Fin de getAll

    /**
     * Actualiza un registro existente
     * @param objeto - Objeto DetalleVentaType con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(DetalleVentaType objeto) {
        connection = getConxion();
        sentenciaSQL = "UPDATE detalle_venta SET id_venta_detalle=?, id_decoracion_detalle=?, cantidad_detalle_venta=?, precio_unitario_venta=?, subtotal_detalle_venta=?, descuento_detalle=? WHERE id_detalle_venta=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdVentaDetalle());
            preparedStatement.setString(2, objeto.getIdDecoracionDetalle());
            preparedStatement.setInt(3, objeto.getCantidadDetalleVenta());
            preparedStatement.setDouble(4, objeto.getPrecioUnitarioVenta());
            preparedStatement.setDouble(5, objeto.getSubtotalDetalleVenta());
            preparedStatement.setDouble(6, objeto.getDescuentoDetalle());
            preparedStatement.setString(7, objeto.getIdDetalleVenta());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Detalle de venta actualizado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DetalleVentaModel.class.getName()).log(Level.SEVERE, null, e);
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
     * Elimina un registro por su ID
     * @param id - Identificador del registro a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean delete(String id) {
        connection = getConxion();
        sentenciaSQL = "DELETE FROM detalle_venta WHERE id_detalle_venta=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Detalle de venta eliminado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DetalleVentaModel.class.getName()).log(Level.SEVERE, null, e);
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
     * Obtiene detalles de venta por venta
     * @param idVenta - ID de la venta
     * @return ArrayList con los detalles de la venta
     */
    public ArrayList<DetalleVentaType> getByIdVenta(String idVenta) {
        ArrayList<DetalleVentaType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM detalle_venta WHERE id_venta_detalle = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, idVenta);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_detalle_venta = resultSet.getString(1);
                String id_venta_detalle = resultSet.getString(2);
                String id_decoracion_detalle = resultSet.getString(3);
                int cantidad_detalle_venta = resultSet.getInt(4);
                double precio_unitario_venta = resultSet.getDouble(5);
                double subtotal_detalle_venta = resultSet.getDouble(6);
                double descuento_detalle = resultSet.getDouble(7);

                DetalleVentaType detalle = new DetalleVentaType();

                detalle.setIdDetalleVenta(id_detalle_venta);
                detalle.setIdVentaDetalle(id_venta_detalle);
                detalle.setIdDecoracionDetalle(id_decoracion_detalle);
                detalle.setCantidadDetalleVenta(cantidad_detalle_venta);
                detalle.setPrecioUnitarioVenta(precio_unitario_venta);
                detalle.setSubtotalDetalleVenta(subtotal_detalle_venta);
                detalle.setDescuentoDetalle(descuento_detalle);
                lista.add(detalle);
            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos por venta" + e.getMessage());
        }
        return lista;
    }// Fin de getByIdVenta

    /**
     * Obtiene detalles de venta de la vista detallada de detalles de ventas
     * @return ArrayList con todos los detalles de ventas con información detallada
     */
    public ArrayList<DetalleVentaType> getAll_vista_detallada_detalle_ventas() {
        ArrayList<DetalleVentaType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM vista_detallada_detalle_ventas";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Mapear campos de la vista a DetalleVentaType
                String id_detalle_venta = resultSet.getString("id_detalle_venta");
                String id_venta_detalle = resultSet.getString("id_venta_detalle");
                String id_decoracion_detalle = resultSet.getString("id_decoracion");
                int cantidad_detalle_venta = resultSet.getInt("cantidad_detalle_venta");
                double precio_unitario_venta = resultSet.getDouble("precio_unitario_venta");
                double subtotal_detalle_venta = resultSet.getDouble("subtotal_detalle_venta");
                double descuento_detalle = resultSet.getDouble("descuento_detalle");

                DetalleVentaType detalle = new DetalleVentaType();
                detalle.setIdDetalleVenta(id_detalle_venta);
                detalle.setIdVentaDetalle(id_venta_detalle);
                detalle.setIdDecoracionDetalle(id_decoracion_detalle);
                detalle.setCantidadDetalleVenta(cantidad_detalle_venta);
                detalle.setPrecioUnitarioVenta(precio_unitario_venta);
                detalle.setSubtotalDetalleVenta(subtotal_detalle_venta);
                detalle.setDescuentoDetalle(descuento_detalle);
                lista.add(detalle);
            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos de vista de detalles de ventas" + e.getMessage());
        }
        return lista;
    }// Fin de getAll_vista_detallada_detalle_ventas
}
