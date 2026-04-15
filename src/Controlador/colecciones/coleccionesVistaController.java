package Controlador.colecciones;

import Vista.colecciones.coleccionesVista;
import Vista.colecciones.FormularioAgregarColeccion;
import Vista.colecciones.FormularioEditarColeccion;
import Vista.colecciones.reportesColecciones;
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
 * Controlador para la vista de colecciones
 * 
 * @author ossca
 */
public class coleccionesVistaController {

    private coleccionesVista vista;
    private Connection conexion;

    public coleccionesVistaController(coleccionesVista vista) {
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
                if (vista.inputBusqueda.getText().equals("Buscar colección...")) {
                    vista.inputBusqueda.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().isEmpty()) {
                    vista.inputBusqueda.setText("Buscar colección...");
                }
            }
        });
    }

    /**
     * Carga todas las colecciones en la tabla
     */
    public void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);

        String sql = "SELECT c.id_coleccion, c.nombre, c.disenador, c.numero_coleccion, c.anio, " +
                "p.nombre as proveedor, c.stock, c.precio_venta " +
                "FROM colecciones c " +
                "LEFT JOIN proveedores p ON c.id_proveedor = p.id_proveedor " +
                "ORDER BY c.id_coleccion";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id_coleccion"),
                        rs.getString("nombre"),
                        rs.getString("disenador"),
                        rs.getString("numero_coleccion"),
                        rs.getInt("anio"),
                        rs.getString("proveedor"),
                        rs.getInt("stock"),
                        rs.getDouble("precio_venta")
                };
                modelo.addRow(fila);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar las colecciones: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca colecciones según el texto ingresado
     */
    private void buscar(ActionEvent e) {
        String textoBusqueda = vista.inputBusqueda.getText().trim();

        if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar colección...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);

        String sql = "SELECT c.id_coleccion, c.nombre, c.disenador, c.numero_coleccion, c.anio, " +
                "p.nombre as proveedor, c.stock, c.precio_venta " +
                "FROM colecciones c " +
                "LEFT JOIN proveedores p ON c.id_proveedor = p.id_proveedor " +
                "WHERE c.nombre LIKE ? OR c.disenador LIKE ? OR c.numero_coleccion LIKE ? " +
                "ORDER BY c.id_coleccion";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            String patron = "%" + textoBusqueda + "%";
            stmt.setString(1, patron);
            stmt.setString(2, patron);
            stmt.setString(3, patron);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                            rs.getInt("id_coleccion"),
                            rs.getString("nombre"),
                            rs.getString("disenador"),
                            rs.getString("numero_coleccion"),
                            rs.getInt("anio"),
                            rs.getString("proveedor"),
                            rs.getInt("stock"),
                            rs.getDouble("precio_venta")
                    };
                    modelo.addRow(fila);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar colecciones: " + ex.getMessage(),
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
     * Abre el formulario para agregar una nueva colección
     */
    private void abrirFormularioAgregar(ActionEvent e) {
        FormularioAgregarColeccion formulario = new FormularioAgregarColeccion(new javax.swing.JFrame(), true);
        formulario.setVisible(true);

        // Recargar la tabla después de agregar
        cargarTabla();
    }

    /**
     * Abre el formulario para editar la colección seleccionada
     */
    private void abrirFormularioEditar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una colección para editar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos de la colección seleccionada
        String nombre = (String) vista.tabla.getValueAt(fila, 1);
        String disenador = (String) vista.tabla.getValueAt(fila, 2);
        String numeroColeccion = (String) vista.tabla.getValueAt(fila, 3);
        Integer anio = (Integer) vista.tabla.getValueAt(fila, 4);

        FormularioEditarColeccion formulario = new FormularioEditarColeccion(new javax.swing.JFrame(), true);

        // Cargar los datos en el formulario
        formulario.inputNombreColeccion.setText(nombre);
        formulario.inputDisenadorColeccion.setText(disenador);
        formulario.inputNumColeccionColeccion.setText(numeroColeccion);
        formulario.inputAnioColeccion.setText(anio.toString());

        formulario.setVisible(true);

        // Recargar la tabla después de editar
        cargarTabla();
    }

    /**
     * Elimina la colección seleccionada
     */
    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una colección para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idColeccion = (int) vista.tabla.getValueAt(fila, 0);
        String nombreColeccion = (String) vista.tabla.getValueAt(fila, 1);

        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de que desea eliminar la colección \"" + nombreColeccion + "\"?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM colecciones WHERE id_coleccion = ?";

            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setInt(1, idColeccion);

                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(vista, "Colección eliminada correctamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo eliminar la colección",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(vista, "Error al eliminar la colección: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Abre la ventana de reportes de colecciones
     */
    private void generarReporte(ActionEvent e) {
        reportesColecciones reporte = new reportesColecciones(new javax.swing.JFrame(), true);
        reporte.setVisible(true);
    }
}
