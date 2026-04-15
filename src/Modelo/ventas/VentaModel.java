package Modelo.ventas;

import Modelo.Conexion;
import Type.ventas.VentaType;
import Type.generales.TipoPagoVenta;
import Type.generales.EstadoVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class VentaModel extends Conexion {

    PreparedStatement preparedStatement = null;
    Connection connection;
    String sentenciaSQL;
    ResultSet resultSet;
    VentaType venta;

    /**
     * Crea un nuevo registro de venta
     * 
     * @param objeto - Objeto VentaType con los datos a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean create(VentaType objeto) {
        connection = getConxion();
        sentenciaSQL = "INSERT INTO ventas (id_venta, id_cliente_venta, numero_factura_venta, subtotal_venta, impuesto_venta, descuento_venta, total_venta, tipo_pago_venta, monto_efectivo, monto_tarjeta, cambio_venta, estado_venta, id_usuario_vendedor) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdVenta());
            preparedStatement.setString(2, objeto.getIdClienteVenta());
            preparedStatement.setString(3, objeto.getNumeroFacturaVenta());
            preparedStatement.setDouble(4, objeto.getSubtotalVenta());
            preparedStatement.setDouble(5, objeto.getImpuestoVenta());
            preparedStatement.setDouble(6, objeto.getDescuentoVenta());
            preparedStatement.setDouble(7, objeto.getTotalVenta());
            preparedStatement.setString(8, objeto.getTipoPagoVenta().toString());
            preparedStatement.setDouble(9, objeto.getMontoEfectivo());
            preparedStatement.setDouble(10, objeto.getMontoTarjeta());
            preparedStatement.setDouble(11, objeto.getCambioVenta());
            preparedStatement.setString(12, objeto.getEstadoVenta().toString());
            preparedStatement.setString(13, objeto.getIdUsuarioVendedor());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Venta ingresada correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(VentaModel.class.getName()).log(Level.SEVERE, null, e);
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
     * @return Objeto VentaType o null si no existe
     */
    public VentaType getById(String id) {
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM ventas WHERE id_venta = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id_venta = resultSet.getString(1);
                String id_cliente_venta = resultSet.getString(2);
                String numero_factura_venta = resultSet.getString(3);
                double subtotal_venta = resultSet.getDouble(4);
                double impuesto_venta = resultSet.getDouble(5);
                double descuento_venta = resultSet.getDouble(6);
                double total_venta = resultSet.getDouble(7);
                String tipo_pago_venta = resultSet.getString(8);
                double monto_efectivo = resultSet.getDouble(9);
                double monto_tarjeta = resultSet.getDouble(10);
                double cambio_venta = resultSet.getDouble(11);
                java.sql.Timestamp fecha_venta = resultSet.getTimestamp(12);
                String estado_venta = resultSet.getString(13);
                String id_usuario_vendedor = resultSet.getString(14);

                VentaType ventaBuscada = new VentaType();

                ventaBuscada.setIdVenta(id_venta);
                ventaBuscada.setIdClienteVenta(id_cliente_venta);
                ventaBuscada.setNumeroFacturaVenta(numero_factura_venta);
                ventaBuscada.setSubtotalVenta(subtotal_venta);
                ventaBuscada.setImpuestoVenta(impuesto_venta);
                ventaBuscada.setDescuentoVenta(descuento_venta);
                ventaBuscada.setTotalVenta(total_venta);
                ventaBuscada.setTipoPagoVenta(TipoPagoVenta.fromDb(tipo_pago_venta));
                ventaBuscada.setMontoEfectivo(monto_efectivo);
                ventaBuscada.setMontoTarjeta(monto_tarjeta);
                ventaBuscada.setCambioVenta(cambio_venta);
                ventaBuscada.setFechaVenta(fecha_venta);
                ventaBuscada.setEstadoVenta(EstadoVenta.fromDb(estado_venta));
                ventaBuscada.setIdUsuarioVendedor(id_usuario_vendedor);
                return ventaBuscada;
            }

            return null;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer" + e.getMessage());
            Logger.getLogger(VentaModel.class.getName()).log(Level.SEVERE, null, e);
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
    public ArrayList<VentaType> getAll() {
        ArrayList<VentaType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM ventas";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_venta = resultSet.getString(1);
                String id_cliente_venta = resultSet.getString(2);
                String numero_factura_venta = resultSet.getString(3);
                double subtotal_venta = resultSet.getDouble(4);
                double impuesto_venta = resultSet.getDouble(5);
                double descuento_venta = resultSet.getDouble(6);
                double total_venta = resultSet.getDouble(7);
                String tipo_pago_venta = resultSet.getString(8);
                double monto_efectivo = resultSet.getDouble(9);
                double monto_tarjeta = resultSet.getDouble(10);
                double cambio_venta = resultSet.getDouble(11);
                java.sql.Timestamp fecha_venta = resultSet.getTimestamp(12);
                String estado_venta = resultSet.getString(13);
                String id_usuario_vendedor = resultSet.getString(14);

                VentaType venta = new VentaType();

                venta.setIdVenta(id_venta);
                venta.setIdClienteVenta(id_cliente_venta);
                venta.setNumeroFacturaVenta(numero_factura_venta);
                venta.setSubtotalVenta(subtotal_venta);
                venta.setImpuestoVenta(impuesto_venta);
                venta.setDescuentoVenta(descuento_venta);
                venta.setTotalVenta(total_venta);
                venta.setTipoPagoVenta(TipoPagoVenta.fromDb(tipo_pago_venta));
                venta.setMontoEfectivo(monto_efectivo);
                venta.setMontoTarjeta(monto_tarjeta);
                venta.setCambioVenta(cambio_venta);
                venta.setFechaVenta(fecha_venta);
                venta.setEstadoVenta(EstadoVenta.fromDb(estado_venta));
                venta.setIdUsuarioVendedor(id_usuario_vendedor);
                lista.add(venta);

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
     * @param objeto - Objeto VentaType con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(VentaType objeto) {
        connection = getConxion();
        sentenciaSQL = "UPDATE ventas SET id_cliente_venta=?, numero_factura_venta=?, subtotal_venta=?, impuesto_venta=?, descuento_venta=?, total_venta=?, tipo_pago_venta=?, monto_efectivo=?, monto_tarjeta=?, cambio_venta=?, estado_venta=?, id_usuario_vendedor=? WHERE id_venta=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdClienteVenta());
            preparedStatement.setString(2, objeto.getNumeroFacturaVenta());
            preparedStatement.setDouble(3, objeto.getSubtotalVenta());
            preparedStatement.setDouble(4, objeto.getImpuestoVenta());
            preparedStatement.setDouble(5, objeto.getDescuentoVenta());
            preparedStatement.setDouble(6, objeto.getTotalVenta());
            preparedStatement.setString(7, objeto.getTipoPagoVenta().toString());
            preparedStatement.setDouble(8, objeto.getMontoEfectivo());
            preparedStatement.setDouble(9, objeto.getMontoTarjeta());
            preparedStatement.setDouble(10, objeto.getCambioVenta());
            preparedStatement.setString(11, objeto.getEstadoVenta().toString());
            preparedStatement.setString(12, objeto.getIdUsuarioVendedor());
            preparedStatement.setString(13, objeto.getIdVenta());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Venta actualizada correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(VentaModel.class.getName()).log(Level.SEVERE, null, e);
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
        sentenciaSQL = "UPDATE ventas SET estado_venta='Cancelada' WHERE id_venta=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Venta eliminada correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(VentaModel.class.getName()).log(Level.SEVERE, null, e);
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
     * Obtiene ventas de la vista detallada de ventas
     * 
     * @return ArrayList con todas las ventas con información detallada
     */
    public ArrayList<VentaType> getAll_vista_detallada_ventas() {
        ArrayList<VentaType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM vista_detallada_ventas";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Mapear campos de la vista a VentaType
                String id_venta = resultSet.getString("id_venta");
                String numero_factura_venta = resultSet.getString("numero_factura_venta");
                java.sql.Timestamp fecha_venta = resultSet.getTimestamp("fecha_venta");
                String id_cliente_venta = resultSet.getString("id_cliente");
                String id_usuario_vendedor = resultSet.getString("id_usuario");
                double subtotal_venta = resultSet.getDouble("subtotal_venta");
                double impuesto_venta = resultSet.getDouble("impuesto_venta");
                double descuento_venta = resultSet.getDouble("descuento_venta");
                double total_venta = resultSet.getDouble("total_venta");
                String tipo_pago_venta = resultSet.getString("tipo_pago_venta");
                double monto_efectivo = resultSet.getDouble("monto_efectivo");
                double monto_tarjeta = resultSet.getDouble("monto_tarjeta");
                double cambio_venta = resultSet.getDouble("cambio_venta");

                VentaType venta = new VentaType();
                venta.setIdVenta(id_venta);
                venta.setNumeroFacturaVenta(numero_factura_venta);
                venta.setFechaVenta(fecha_venta);
                venta.setIdClienteVenta(id_cliente_venta);
                venta.setIdUsuarioVendedor(id_usuario_vendedor);
                venta.setSubtotalVenta(subtotal_venta);
                venta.setImpuestoVenta(impuesto_venta);
                venta.setDescuentoVenta(descuento_venta);
                venta.setTotalVenta(total_venta);
                venta.setTipoPagoVenta(TipoPagoVenta.fromDb(tipo_pago_venta));
                venta.setMontoEfectivo(monto_efectivo);
                venta.setMontoTarjeta(monto_tarjeta);
                venta.setCambioVenta(cambio_venta);
                venta.setEstadoVenta(EstadoVenta.fromDb("Activa")); // Las vistas solo muestran activas
                lista.add(venta);
            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos de vista de ventas" + e.getMessage());
        }
        return lista;
    }// Fin de getAll_vista_detallada_ventas
}
