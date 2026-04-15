package Modelo.compras;

import Modelo.Conexion;
import Type.compras.DetalleCompraType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DetalleCompraModel extends Conexion {

    PreparedStatement preparedStatement = null;
    Connection connection;
    String sentenciaSQL;
    ResultSet resultSet;
    DetalleCompraType detalleCompra;

    /**
     * Crea un nuevo registro de detalle de compra
     * @param objeto - Objeto DetalleCompraType con los datos a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean create(DetalleCompraType objeto) {
        connection = getConxion();
        sentenciaSQL = "INSERT INTO detalle_compra (id_detalle_compra, id_factura_compra_detalle, id_decoracion_detalle, cantidad_detalle_compra, precio_costo_compra, precio_venta_compra, subtotal_detalle_compra) VALUES (?,?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdDetalleCompra());
            preparedStatement.setString(2, objeto.getIdFacturaCompraDetalle());
            preparedStatement.setString(3, objeto.getIdDecoracionDetalle());
            preparedStatement.setInt(4, objeto.getCantidadDetalleCompra());
            preparedStatement.setDouble(5, objeto.getPrecioCostoCompra());
            preparedStatement.setDouble(6, objeto.getPrecioVentaCompra());
            preparedStatement.setDouble(7, objeto.getSubtotalDetalleCompra());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Detalle de compra ingresado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DetalleCompraModel.class.getName()).log(Level.SEVERE, null, e);
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
     * @return Objeto DetalleCompraType o null si no existe
     */
    public DetalleCompraType getById(String id) {
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM detalle_compra WHERE id_detalle_compra = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id_detalle_compra = resultSet.getString(1);
                String id_factura_compra_detalle = resultSet.getString(2);
                String id_decoracion_detalle = resultSet.getString(3);
                int cantidad_detalle_compra = resultSet.getInt(4);
                double precio_costo_compra = resultSet.getDouble(5);
                double precio_venta_compra = resultSet.getDouble(6);
                double subtotal_detalle_compra = resultSet.getDouble(7);

                DetalleCompraType detalleBuscado = new DetalleCompraType();

                detalleBuscado.setIdDetalleCompra(id_detalle_compra);
                detalleBuscado.setIdFacturaCompraDetalle(id_factura_compra_detalle);
                detalleBuscado.setIdDecoracionDetalle(id_decoracion_detalle);
                detalleBuscado.setCantidadDetalleCompra(cantidad_detalle_compra);
                detalleBuscado.setPrecioCostoCompra(precio_costo_compra);
                detalleBuscado.setPrecioVentaCompra(precio_venta_compra);
                detalleBuscado.setSubtotalDetalleCompra(subtotal_detalle_compra);
                return detalleBuscado;
            }

            return null;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer" + e.getMessage());
            Logger.getLogger(DetalleCompraModel.class.getName()).log(Level.SEVERE, null, e);
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
    public ArrayList<DetalleCompraType> getAll() {
        ArrayList<DetalleCompraType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM detalle_compra";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_detalle_compra = resultSet.getString(1);
                String id_factura_compra_detalle = resultSet.getString(2);
                String id_decoracion_detalle = resultSet.getString(3);
                int cantidad_detalle_compra = resultSet.getInt(4);
                double precio_costo_compra = resultSet.getDouble(5);
                double precio_venta_compra = resultSet.getDouble(6);
                double subtotal_detalle_compra = resultSet.getDouble(7);

                DetalleCompraType detalle = new DetalleCompraType();

                detalle.setIdDetalleCompra(id_detalle_compra);
                detalle.setIdFacturaCompraDetalle(id_factura_compra_detalle);
                detalle.setIdDecoracionDetalle(id_decoracion_detalle);
                detalle.setCantidadDetalleCompra(cantidad_detalle_compra);
                detalle.setPrecioCostoCompra(precio_costo_compra);
                detalle.setPrecioVentaCompra(precio_venta_compra);
                detalle.setSubtotalDetalleCompra(subtotal_detalle_compra);
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
     * @param objeto - Objeto DetalleCompraType con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(DetalleCompraType objeto) {
        connection = getConxion();
        sentenciaSQL = "UPDATE detalle_compra SET id_factura_compra_detalle=?, id_decoracion_detalle=?, cantidad_detalle_compra=?, precio_costo_compra=?, precio_venta_compra=?, subtotal_detalle_compra=? WHERE id_detalle_compra=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getIdFacturaCompraDetalle());
            preparedStatement.setString(2, objeto.getIdDecoracionDetalle());
            preparedStatement.setInt(3, objeto.getCantidadDetalleCompra());
            preparedStatement.setDouble(4, objeto.getPrecioCostoCompra());
            preparedStatement.setDouble(5, objeto.getPrecioVentaCompra());
            preparedStatement.setDouble(6, objeto.getSubtotalDetalleCompra());
            preparedStatement.setString(7, objeto.getIdDetalleCompra());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Detalle de compra actualizado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DetalleCompraModel.class.getName()).log(Level.SEVERE, null, e);
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
        sentenciaSQL = "DELETE FROM detalle_compra WHERE id_detalle_compra=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Detalle de compra eliminado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DetalleCompraModel.class.getName()).log(Level.SEVERE, null, e);
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
     * Obtiene detalles de compra por factura de compra
     * @param idFacturaCompra - ID de la factura de compra
     * @return ArrayList con los detalles de la compra
     */
    public ArrayList<DetalleCompraType> getByIdFacturaCompra(String idFacturaCompra) {
        ArrayList<DetalleCompraType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM detalle_compra WHERE id_factura_compra_detalle = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, idFacturaCompra);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id_detalle_compra = resultSet.getString(1);
                String id_factura_compra_detalle = resultSet.getString(2);
                String id_decoracion_detalle = resultSet.getString(3);
                int cantidad_detalle_compra = resultSet.getInt(4);
                double precio_costo_compra = resultSet.getDouble(5);
                double precio_venta_compra = resultSet.getDouble(6);
                double subtotal_detalle_compra = resultSet.getDouble(7);

                DetalleCompraType detalle = new DetalleCompraType();

                detalle.setIdDetalleCompra(id_detalle_compra);
                detalle.setIdFacturaCompraDetalle(id_factura_compra_detalle);
                detalle.setIdDecoracionDetalle(id_decoracion_detalle);
                detalle.setCantidadDetalleCompra(cantidad_detalle_compra);
                detalle.setPrecioCostoCompra(precio_costo_compra);
                detalle.setPrecioVentaCompra(precio_venta_compra);
                detalle.setSubtotalDetalleCompra(subtotal_detalle_compra);
                lista.add(detalle);
            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos por factura de compra" + e.getMessage());
        }
        return lista;
    }// Fin de getByIdFacturaCompra
}
