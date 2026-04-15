package Modelo.compras;

import Modelo.Conexion;
import Type.compras.FacturaCompraType;
import Type.generales.TipoPago;
import Type.generales.EstadoFactura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class FacturaCompraModel extends Conexion {

    PreparedStatement preparedStatement = null;
    Connection connection;
    String sentenciaSQL;
    ResultSet resultSet;
    FacturaCompraType facturaCompra;

    /**
     * Crea un nuevo registro de factura de compra
     * @param objeto - Objeto FacturaCompraType con los datos a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean create(FacturaCompraType objeto) {
        connection = getConxion();
        sentenciaSQL = "INSERT INTO facturas_compra (id_factura_compra, id_proveedor_factura_compra, numero_factura, total_factura_compra, tipo_pago_factura_compra, estado_factura_compra, fecha_factura_compra, fecha_vencimiento_factura, condicion_factura) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdFacturaCompra());
            preparedStatement.setString(2, objeto.getIdProveedorFacturaCompra());
            preparedStatement.setString(3, objeto.getNumeroFactura());
            preparedStatement.setDouble(4, objeto.getTotalFacturaCompra());
            preparedStatement.setString(5, objeto.getTipoPagoFacturaCompra() != null ? objeto.getTipoPagoFacturaCompra().getDisplayName() : null);
            preparedStatement.setString(6, objeto.getEstadoFacturaCompra() != null ? objeto.getEstadoFacturaCompra().getDisplayName() : null);
            preparedStatement.setDate(7, objeto.getFechaFacturaCompra());
            preparedStatement.setDate(8, objeto.getFechaVencimientoFactura());
            preparedStatement.setString(9, objeto.getCondicionFactura());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Factura de compra ingresada correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(FacturaCompraModel.class.getName()).log(Level.SEVERE, null, e);
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
     * @return Objeto FacturaCompraType o null si no existe
     */
    public FacturaCompraType getById(String id) {
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM facturas_compra WHERE id_factura_compra = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id_factura_compra = resultSet.getString(1);
                String id_proveedor_factura_compra = resultSet.getString(2);
                String numero_factura = resultSet.getString(3);
                double total_factura_compra = resultSet.getDouble(4);
                String tipo_pago_factura_compra = resultSet.getString(5);
                String estado_factura_compra = resultSet.getString(6);
                java.sql.Date fecha_factura_compra = resultSet.getDate(7);
                java.sql.Date fecha_vencimiento_factura = resultSet.getDate(8);
                String condicion_factura = resultSet.getString(9);
                java.sql.Timestamp created_at = resultSet.getTimestamp(10);

                FacturaCompraType facturaBuscada = new FacturaCompraType();

                facturaBuscada.setIdFacturaCompra(id_factura_compra);
                facturaBuscada.setIdProveedorFacturaCompra(id_proveedor_factura_compra);
                facturaBuscada.setNumeroFactura(numero_factura);
                facturaBuscada.setTotalFacturaCompra(total_factura_compra);
                facturaBuscada.setTipoPagoFacturaCompra(TipoPago.fromDb(tipo_pago_factura_compra));
                facturaBuscada.setEstadoFacturaCompra(EstadoFactura.fromDb(estado_factura_compra));
                facturaBuscada.setFechaFacturaCompra(fecha_factura_compra);
                facturaBuscada.setFechaVencimientoFactura(fecha_vencimiento_factura);
                facturaBuscada.setCondicionFactura(condicion_factura);
                facturaBuscada.setCreatedAt(created_at);
                return facturaBuscada;
            }

            return null;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer" + e.getMessage());
            Logger.getLogger(FacturaCompraModel.class.getName()).log(Level.SEVERE, null, e);
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
    public ArrayList<FacturaCompraType> getAll() {
        ArrayList<FacturaCompraType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM facturas_compra";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_factura_compra = resultSet.getString(1);
                String id_proveedor_factura_compra = resultSet.getString(2);
                String numero_factura = resultSet.getString(3);
                double total_factura_compra = resultSet.getDouble(4);
                String tipo_pago_factura_compra = resultSet.getString(5);
                String estado_factura_compra = resultSet.getString(6);
                java.sql.Date fecha_factura_compra = resultSet.getDate(7);
                java.sql.Date fecha_vencimiento_factura = resultSet.getDate(8);
                String condicion_factura = resultSet.getString(9);
                java.sql.Timestamp created_at = resultSet.getTimestamp(10);

                FacturaCompraType factura = new FacturaCompraType();

                factura.setIdFacturaCompra(id_factura_compra);
                factura.setIdProveedorFacturaCompra(id_proveedor_factura_compra);
                factura.setNumeroFactura(numero_factura);
                factura.setTotalFacturaCompra(total_factura_compra);
                factura.setTipoPagoFacturaCompra(TipoPago.fromDb(tipo_pago_factura_compra));
                factura.setEstadoFacturaCompra(EstadoFactura.fromDb(estado_factura_compra));
                factura.setFechaFacturaCompra(fecha_factura_compra);
                factura.setFechaVencimientoFactura(fecha_vencimiento_factura);
                factura.setCondicionFactura(condicion_factura);
                factura.setCreatedAt(created_at);
                lista.add(factura);

            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos" + e.getMessage());
        }
        return lista;
    }// Fin de getAll

    /**
     * Actualiza un registro existente
     * @param objeto - Objeto FacturaCompraType con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(FacturaCompraType objeto) {
        connection = getConxion();
        sentenciaSQL = "UPDATE facturas_compra SET id_proveedor_factura_compra=?, numero_factura=?, total_factura_compra=?, tipo_pago_factura_compra=?, estado_factura_compra=?, fecha_factura_compra=?, fecha_vencimiento_factura=?, condicion_factura=? WHERE id_factura_compra=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdProveedorFacturaCompra());
            preparedStatement.setString(2, objeto.getNumeroFactura());
            preparedStatement.setDouble(3, objeto.getTotalFacturaCompra());
            preparedStatement.setString(4, objeto.getTipoPagoFacturaCompra() != null ? objeto.getTipoPagoFacturaCompra().getDisplayName() : null);
            preparedStatement.setString(5, objeto.getEstadoFacturaCompra() != null ? objeto.getEstadoFacturaCompra().getDisplayName() : null);
            preparedStatement.setDate(6, objeto.getFechaFacturaCompra());
            preparedStatement.setDate(7, objeto.getFechaVencimientoFactura());
            preparedStatement.setString(8, objeto.getCondicionFactura());
            preparedStatement.setString(9, objeto.getIdFacturaCompra());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Factura de compra actualizada correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(FacturaCompraModel.class.getName()).log(Level.SEVERE, null, e);
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
        sentenciaSQL = "UPDATE facturas_compra SET estado_factura_compra='Cancelada' WHERE id_factura_compra=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Factura de compra eliminada correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(FacturaCompraModel.class.getName()).log(Level.SEVERE, null, e);
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
     * Obtiene facturas de la vista detallada de compras
     * @return ArrayList con todas las facturas con información detallada
     */
    public ArrayList<FacturaCompraType> getAll_vista_detallada_compras() {
        ArrayList<FacturaCompraType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM vista_detallada_compras";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Mapear campos de la vista a FacturaCompraType
                String id_factura_compra = resultSet.getString("id_factura_compra");
                String numero_factura = resultSet.getString("numero_factura");
                java.sql.Date fecha_factura_compra = resultSet.getDate("fecha_factura_compra");
                java.sql.Date fecha_vencimiento_factura = resultSet.getDate("fecha_vencimiento_factura");
                String id_proveedor_factura_compra = resultSet.getString("id_proveedor");
                double total_factura_compra = resultSet.getDouble("total_factura_compra");
                String tipo_pago_factura_compra = resultSet.getString("tipo_pago_factura_compra");
                String estado_factura_compra = resultSet.getString("estado_real_pago");
                String condicion_factura = resultSet.getString("condicion_factura");

                FacturaCompraType factura = new FacturaCompraType();
                factura.setIdFacturaCompra(id_factura_compra);
                factura.setNumeroFactura(numero_factura);
                factura.setFechaFacturaCompra(fecha_factura_compra);
                factura.setFechaVencimientoFactura(fecha_vencimiento_factura);
                factura.setIdProveedorFacturaCompra(id_proveedor_factura_compra);
                factura.setTotalFacturaCompra(total_factura_compra);
                factura.setTipoPagoFacturaCompra(TipoPago.fromDb(tipo_pago_factura_compra));
                factura.setEstadoFacturaCompra(EstadoFactura.fromDb(estado_factura_compra));
                factura.setCondicionFactura(condicion_factura);
                lista.add(factura);
            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos de vista de compras" + e.getMessage());
        }
        return lista;
    }// Fin de getAll_vista_detallada_compras
}
