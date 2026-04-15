package Controlador.decoraciones;

import Vista.decoraciones.decoracionesVista;
import Vista.decoraciones.FormularioAgregarDecoracion;
import Vista.decoraciones.FormularioEditarDecoracion;
import Vista.decoraciones.reportesDecoraciones;
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
 * Controlador para la vista de decoraciones
 * 
 * @author ossca
 */
public class decoracionesVistaController {

    private decoracionesVista vista;
    private Connection conexion;

    public decoracionesVistaController(decoracionesVista vista) {
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
                if (vista.inputBusqueda.getText().equals("Buscar decoración...")) {
                    vista.inputBusqueda.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().isEmpty()) {
                    vista.inputBusqueda.setText("Buscar decoración...");
                }
            }
        });
    }

    /**
     * Carga todas las decoraciones en la tabla
     */
    public void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);

        String sql = "SELECT d.id_decoracion, d.nombre, d.stock, d.precio_venta, " +
                "p.nombre as proveedor, d.estado " +
                "FROM decoraciones d " +
                "LEFT JOIN proveedores p ON d.id_proveedor = p.id_proveedor " +
                "ORDER BY d.id_decoracion";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id_decoracion"),
                        rs.getString("nombre"),
                        rs.getInt("stock"),
                        rs.getDouble("precio_venta"),
                        rs.getString("proveedor"),
                        rs.getString("estado")
                };
                modelo.addRow(fila);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar las decoraciones: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca decoraciones según el texto ingresado
     */
    private void buscar(ActionEvent e) {
        String textoBusqueda = vista.inputBusqueda.getText().trim();

        if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar decoración...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);

        String sql = "SELECT d.id_decoracion, d.nombre, d.stock, d.precio_venta, " +
                "p.nombre as proveedor, d.estado " +
                "FROM decoraciones d " +
                "LEFT JOIN proveedores p ON d.id_proveedor = p.id_proveedor " +
                "WHERE d.nombre LIKE ? OR p.nombre LIKE ? OR d.estado LIKE ? " +
                "ORDER BY d.id_decoracion";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            String patron = "%" + textoBusqueda + "%";
            stmt.setString(1, patron);
            stmt.setString(2, patron);
            stmt.setString(3, patron);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                            rs.getInt("id_decoracion"),
                            rs.getString("nombre"),
                            rs.getInt("stock"),
                            rs.getDouble("precio_venta"),
                            rs.getString("proveedor"),
                            rs.getString("estado")
                    };
                    modelo.addRow(fila);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar decoraciones: " + ex.getMessage(),
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
     * Abre el formulario para agregar una nueva decoración
     */
    private void abrirFormularioAgregar(ActionEvent e) {
        FormularioAgregarDecoracion formulario = new FormularioAgregarDecoracion(new javax.swing.JFrame(), true);
        formulario.setVisible(true);

        // Recargar la tabla después de agregar
        cargarTabla();
    }

    /**
     * Abre el formulario para editar la decoración seleccionada
     */
    private void abrirFormularioEditar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una decoración para editar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos de la decoración seleccionada (se cargarán según los campos del
        // formulario)

        FormularioEditarDecoracion formulario = new FormularioEditarDecoracion(new javax.swing.JFrame(), true);

        // Cargar los datos en el formulario (según los campos disponibles)
        // Nota: Necesitarás revisar los campos específicos del formulario de editar
        // decoración

        formulario.setVisible(true);

        // Recargar la tabla después de editar
        cargarTabla();
    }

    /**
     * Elimina la decoración seleccionada
     */
    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una decoración para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idDecoracion = (int) vista.tabla.getValueAt(fila, 0);
        String nombreDecoracion = (String) vista.tabla.getValueAt(fila, 1);

        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de que desea eliminar la decoración \"" + nombreDecoracion + "\"?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM decoraciones WHERE id_decoracion = ?";

            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setInt(1, idDecoracion);

                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(vista, "Decoración eliminada correctamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo eliminar la decoración",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(vista, "Error al eliminar la decoración: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Abre la ventana de reportes de decoraciones
     */
    private void generarReporte(ActionEvent e) {
        reportesDecoraciones reporte = new reportesDecoraciones(new javax.swing.JFrame(), true);
        reporte.setVisible(true);
    }
}
