package Controlador.compras;

import Vista.compras.comprasVista;
import Vista.compras.FormularioAgregarCompra;
import Vista.compras.FormularioEditarCompra;
import Vista.compras.reportesCompras;
import Modelo.Conexion;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador para la vista de compras
 * 
 * @author ossca
 */
public class comprasVistaController {

    private comprasVista vista;
    private Connection conexion;

    public comprasVistaController(comprasVista vista) {
        this.vista = vista;
        Conexion conexionObj = new Conexion();
        this.conexion = conexionObj.getConxion();
        inicializarEventos();
        cargarTabla();
    }

    private void inicializarEventos() {
        // Evento del botón buscar
        vista.botonBuscar.addActionListener(this::buscar);

        // Evento del botón agregar
        vista.botonAgregar.addActionListener(this::abrirFormularioAgregar);

        // Evento del botón informe
        vista.botonInforme.addActionListener(this::generarReporte);

        // Evento del mouse en la tabla para menú contextual
        vista.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarMenuContextual(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarMenuContextual(e);
                }
            }
        });

        // Evento focus en el campo de búsqueda
        vista.inputBusqueda.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().equals("Buscar compra...")) {
                    vista.inputBusqueda.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().isEmpty()) {
                    vista.inputBusqueda.setText("Buscar compra...");
                }
            }
        });
    }

    /**
     * Carga todas las compras en la tabla
     */
    public void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);

        String sql = "SELECT c.id_compra, p.nombre as proveedor, c.fecha_compra, " +
                "c.total, c.estado, u.nombre as usuario " +
                "FROM compras c " +
                "LEFT JOIN proveedores p ON c.id_proveedor = p.id_proveedor " +
                "LEFT JOIN usuarios u ON c.id_usuario = u.id_usuario " +
                "ORDER BY c.id_compra DESC";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id_compra"),
                        rs.getString("proveedor"),
                        rs.getDate("fecha_compra"),
                        rs.getDouble("total"),
                        rs.getString("estado"),
                        rs.getString("usuario")
                };
                modelo.addRow(fila);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar las compras: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca compras según el texto ingresado
     */
    private void buscar(ActionEvent e) {
        String textoBusqueda = vista.inputBusqueda.getText().trim();

        if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar compra...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);

        String sql = "SELECT c.id_compra, p.nombre as proveedor, c.fecha_compra, " +
                "c.total, c.estado, u.nombre as usuario " +
                "FROM compras c " +
                "LEFT JOIN proveedores p ON c.id_proveedor = p.id_proveedor " +
                "LEFT JOIN usuarios u ON c.id_usuario = u.id_usuario " +
                "WHERE p.nombre LIKE ? OR c.estado LIKE ? OR u.nombre LIKE ? " +
                "ORDER BY c.id_compra DESC";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            String patron = "%" + textoBusqueda + "%";
            stmt.setString(1, patron);
            stmt.setString(2, patron);
            stmt.setString(3, patron);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                            rs.getInt("id_compra"),
                            rs.getString("proveedor"),
                            rs.getDate("fecha_compra"),
                            rs.getDouble("total"),
                            rs.getString("estado"),
                            rs.getString("usuario")
                    };
                    modelo.addRow(fila);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar compras: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra el menú contextual en la tabla
     */
    private void mostrarMenuContextual(MouseEvent e) {
        int fila = vista.tabla.rowAtPoint(e.getPoint());

        if (fila >= 0) {
            vista.tabla.setRowSelectionInterval(fila, fila);

            javax.swing.JPopupMenu menu = new javax.swing.JPopupMenu();

            javax.swing.JMenuItem menuItemEditar = new javax.swing.JMenuItem("Editar");
            menuItemEditar.addActionListener(evt -> abrirFormularioEditar());

            javax.swing.JMenuItem menuItemEliminar = new javax.swing.JMenuItem("Eliminar");
            menuItemEliminar.addActionListener(evt -> eliminar());

            menu.add(menuItemEditar);
            menu.add(menuItemEliminar);

            menu.show(vista.tabla, e.getX(), e.getY());
        }
    }

    /**
     * Abre el formulario para agregar una nueva compra
     */
    private void abrirFormularioAgregar(ActionEvent e) {
        FormularioAgregarCompra formulario = new FormularioAgregarCompra(new javax.swing.JFrame(), true);
        formulario.setVisible(true);

        // Recargar la tabla después de agregar
        cargarTabla();
    }

    /**
     * Abre el formulario para editar la compra seleccionada
     */
    private void abrirFormularioEditar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una compra para editar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos de la compra seleccionada (se cargarán según los campos del
        // formulario)

        FormularioEditarCompra formulario = new FormularioEditarCompra(new javax.swing.JFrame(), true);

        // Cargar los datos en el formulario (según los campos disponibles)
        // Nota: Necesitarás revisar los campos específicos del formulario de editar
        // compra

        formulario.setVisible(true);

        // Recargar la tabla después de editar
        cargarTabla();
    }

    /**
     * Elimina la compra seleccionada
     */
    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una compra para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCompra = (int) vista.tabla.getValueAt(fila, 0);
        String proveedor = (String) vista.tabla.getValueAt(fila, 1);

        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de que desea eliminar la compra #" + idCompra + " del proveedor \"" + proveedor + "\"?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM compras WHERE id_compra = ?";

            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setInt(1, idCompra);

                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(vista, "Compra eliminada correctamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo eliminar la compra",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(vista, "Error al eliminar la compra: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Abre la ventana de reportes de compras
     */
    private void generarReporte(ActionEvent e) {
        reportesCompras reporte = new reportesCompras(new javax.swing.JFrame(), true);
        reporte.setVisible(true);
    }
}
