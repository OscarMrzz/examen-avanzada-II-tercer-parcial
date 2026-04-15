package Modelo.ventas;

import Modelo.Conexion;
import Type.ventas.ReciboPagoType;
import Type.generales.MetodoPago;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ReciboPagoModel extends Conexion {

    PreparedStatement preparedStatement = null;
    Connection connection;
    String sentenciaSQL;
    ResultSet resultSet;
    ReciboPagoType reciboPago;

    /**
     * Crea un nuevo registro de recibo de pago
     * @param objeto - Objeto ReciboPagoType con los datos a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean create(ReciboPagoType objeto) {
        connection = getConxion();
        sentenciaSQL = "INSERT INTO recibos_pago (id_recibo_pago, id_factura_compra_pago, id_proveedor_pago, monto_pago, fecha_pago, metodo_pago, referencia_pago, observaciones_pago) VALUES (?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdReciboPago());
            preparedStatement.setString(2, objeto.getIdFacturaCompraPago());
            preparedStatement.setString(3, objeto.getIdProveedorPago());
            preparedStatement.setDouble(4, objeto.getMontoPago());
            preparedStatement.setDate(5, objeto.getFechaPago());
            preparedStatement.setString(6, objeto.getMetodoPago().toString());
            preparedStatement.setString(7, objeto.getReferenciaPago());
            preparedStatement.setString(8, objeto.getObservacionesPago());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Recibo de pago ingresado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ReciboPagoModel.class.getName()).log(Level.SEVERE, null, e);
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
     * @return Objeto ReciboPagoType o null si no existe
     */
    public ReciboPagoType getById(String id) {
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM recibos_pago WHERE id_recibo_pago = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id_recibo_pago = resultSet.getString(1);
                String id_factura_compra_pago = resultSet.getString(2);
                String id_proveedor_pago = resultSet.getString(3);
                double monto_pago = resultSet.getDouble(4);
                java.sql.Date fecha_pago = resultSet.getDate(5);
                String metodo_pago = resultSet.getString(6);
                String referencia_pago = resultSet.getString(7);
                String observaciones_pago = resultSet.getString(8);
                java.sql.Timestamp created_at = resultSet.getTimestamp(9);

                ReciboPagoType reciboBuscado = new ReciboPagoType();

                reciboBuscado.setIdReciboPago(id_recibo_pago);
                reciboBuscado.setIdFacturaCompraPago(id_factura_compra_pago);
                reciboBuscado.setIdProveedorPago(id_proveedor_pago);
                reciboBuscado.setMontoPago(monto_pago);
                reciboBuscado.setFechaPago(fecha_pago);
                reciboBuscado.setMetodoPago(MetodoPago.valueOf(metodo_pago));
                reciboBuscado.setReferenciaPago(referencia_pago);
                reciboBuscado.setObservacionesPago(observaciones_pago);
                reciboBuscado.setCreatedAt(created_at);
                return reciboBuscado;
            }

            return null;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer" + e.getMessage());
            Logger.getLogger(ReciboPagoModel.class.getName()).log(Level.SEVERE, null, e);
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
    public ArrayList<ReciboPagoType> getAll() {
        ArrayList<ReciboPagoType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM recibos_pago";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_recibo_pago = resultSet.getString(1);
                String id_factura_compra_pago = resultSet.getString(2);
                String id_proveedor_pago = resultSet.getString(3);
                double monto_pago = resultSet.getDouble(4);
                java.sql.Date fecha_pago = resultSet.getDate(5);
                String metodo_pago = resultSet.getString(6);
                String referencia_pago = resultSet.getString(7);
                String observaciones_pago = resultSet.getString(8);
                java.sql.Timestamp created_at = resultSet.getTimestamp(9);

                ReciboPagoType recibo = new ReciboPagoType();

                recibo.setIdReciboPago(id_recibo_pago);
                recibo.setIdFacturaCompraPago(id_factura_compra_pago);
                recibo.setIdProveedorPago(id_proveedor_pago);
                recibo.setMontoPago(monto_pago);
                recibo.setFechaPago(fecha_pago);
                recibo.setMetodoPago(MetodoPago.valueOf(metodo_pago));
                recibo.setReferenciaPago(referencia_pago);
                recibo.setObservacionesPago(observaciones_pago);
                recibo.setCreatedAt(created_at);
                lista.add(recibo);

            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos" + e.getMessage());
        }
        return lista;
    }// Fin de getAll

    /**
     * Actualiza un registro existente
     * @param objeto - Objeto ReciboPagoType con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(ReciboPagoType objeto) {
        connection = getConxion();
        sentenciaSQL = "UPDATE recibos_pago SET id_factura_compra_pago=?, id_proveedor_pago=?, monto_pago=?, fecha_pago=?, metodo_pago=?, referencia_pago=?, observaciones_pago=? WHERE id_recibo_pago=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdFacturaCompraPago());
            preparedStatement.setString(2, objeto.getIdProveedorPago());
            preparedStatement.setDouble(3, objeto.getMontoPago());
            preparedStatement.setDate(4, objeto.getFechaPago());
            preparedStatement.setString(5, objeto.getMetodoPago().toString());
            preparedStatement.setString(6, objeto.getReferenciaPago());
            preparedStatement.setString(7, objeto.getObservacionesPago());
            preparedStatement.setString(8, objeto.getIdReciboPago());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Recibo de pago actualizado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ReciboPagoModel.class.getName()).log(Level.SEVERE, null, e);
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
        sentenciaSQL = "DELETE FROM recibos_pago WHERE id_recibo_pago=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Recibo de pago eliminado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ReciboPagoModel.class.getName()).log(Level.SEVERE, null, e);
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
     * Obtiene recibos de pago por factura de compra
     * @param idFacturaCompra - ID de la factura de compra
     * @return ArrayList con los recibos de pago de la factura
     */
    public ArrayList<ReciboPagoType> getByIdFacturaCompra(String idFacturaCompra) {
        ArrayList<ReciboPagoType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM recibos_pago WHERE id_factura_compra_pago = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, idFacturaCompra);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_recibo_pago = resultSet.getString(1);
                String id_factura_compra_pago = resultSet.getString(2);
                String id_proveedor_pago = resultSet.getString(3);
                double monto_pago = resultSet.getDouble(4);
                java.sql.Date fecha_pago = resultSet.getDate(5);
                String metodo_pago = resultSet.getString(6);
                String referencia_pago = resultSet.getString(7);
                String observaciones_pago = resultSet.getString(8);
                java.sql.Timestamp created_at = resultSet.getTimestamp(9);

                ReciboPagoType recibo = new ReciboPagoType();

                recibo.setIdReciboPago(id_recibo_pago);
                recibo.setIdFacturaCompraPago(id_factura_compra_pago);
                recibo.setIdProveedorPago(id_proveedor_pago);
                recibo.setMontoPago(monto_pago);
                recibo.setFechaPago(fecha_pago);
                recibo.setMetodoPago(MetodoPago.valueOf(metodo_pago));
                recibo.setReferenciaPago(referencia_pago);
                recibo.setObservacionesPago(observaciones_pago);
                recibo.setCreatedAt(created_at);
                lista.add(recibo);
            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos por factura de compra" + e.getMessage());
        }
        return lista;
    }// Fin de getByIdFacturaCompra

    /**
     * Obtiene recibos de pago por proveedor
     * @param idProveedor - ID del proveedor
     * @return ArrayList con los recibos de pago del proveedor
     */
    public ArrayList<ReciboPagoType> getByIdProveedor(String idProveedor) {
        ArrayList<ReciboPagoType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM recibos_pago WHERE id_proveedor_pago = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, idProveedor);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_recibo_pago = resultSet.getString(1);
                String id_factura_compra_pago = resultSet.getString(2);
                String id_proveedor_pago = resultSet.getString(3);
                double monto_pago = resultSet.getDouble(4);
                java.sql.Date fecha_pago = resultSet.getDate(5);
                String metodo_pago = resultSet.getString(6);
                String referencia_pago = resultSet.getString(7);
                String observaciones_pago = resultSet.getString(8);
                java.sql.Timestamp created_at = resultSet.getTimestamp(9);

                ReciboPagoType recibo = new ReciboPagoType();

                recibo.setIdReciboPago(id_recibo_pago);
                recibo.setIdFacturaCompraPago(id_factura_compra_pago);
                recibo.setIdProveedorPago(id_proveedor_pago);
                recibo.setMontoPago(monto_pago);
                recibo.setFechaPago(fecha_pago);
                recibo.setMetodoPago(MetodoPago.valueOf(metodo_pago));
                recibo.setReferenciaPago(referencia_pago);
                recibo.setObservacionesPago(observaciones_pago);
                recibo.setCreatedAt(created_at);
                lista.add(recibo);
            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos por proveedor" + e.getMessage());
        }
        return lista;
    }// Fin de getByIdProveedor
}
