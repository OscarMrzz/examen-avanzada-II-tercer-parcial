package Controlador.inventario;

import Vista.inventario.inventarioVista;
import Vista.inventario.FormularioAgregarInventario;
import Vista.inventario.FormularioEditarInventario;
import Vista.inventario.reportesInventario;
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
 * Controlador para la vista de inventario
 * @author ossca
 */
public class inventarioVistaController {
    
    private inventarioVista vista;
    private Connection conexion;
    
    public inventarioVistaController(inventarioVista vista) {
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
                if (vista.inputBusqueda.getText().equals("Buscar en inventario...")) {
                    vista.inputBusqueda.setText("");
                }
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().isEmpty()) {
                    vista.inputBusqueda.setText("Buscar en inventario...");
                }
            }
        });
    }
    
    /**
     * Carga todos los items del inventario en la tabla
     */
    public void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);
        
        String sql = "SELECT i.id_inventario, i.nombre, i.stock, i.stock_minimo, " +
                    "i.precio_venta, p.nombre as proveedor, " +
                    "CASE WHEN i.stock <= i.stock_minimo THEN 'BAJO' ELSE 'NORMAL' END as estado_stock " +
                    "FROM inventario i " +
                    "LEFT JOIN proveedores p ON i.id_proveedor = p.id_proveedor " +
                    "ORDER BY i.id_inventario";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_inventario"),
                    rs.getString("nombre"),
                    rs.getInt("stock"),
                    rs.getInt("stock_minimo"),
                    rs.getDouble("precio_venta"),
                    rs.getString("proveedor"),
                    rs.getString("estado_stock")
                };
                modelo.addRow(fila);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar el inventario: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Busca items en el inventario según el texto ingresado
     */
    private void buscar(ActionEvent e) {
        String textoBusqueda = vista.inputBusqueda.getText().trim();
        
        if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar en inventario...")) {
            cargarTabla();
            return;
        }
        
        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);
        
        String sql = "SELECT i.id_inventario, i.nombre, i.stock, i.stock_minimo, " +
                    "i.precio_venta, p.nombre as proveedor, " +
                    "CASE WHEN i.stock <= i.stock_minimo THEN 'BAJO' ELSE 'NORMAL' END as estado_stock " +
                    "FROM inventario i " +
                    "LEFT JOIN proveedores p ON i.id_proveedor = p.id_proveedor " +
                    "WHERE i.nombre LIKE ? OR p.nombre LIKE ? " +
                    "ORDER BY i.id_inventario";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            String patron = "%" + textoBusqueda + "%";
            stmt.setString(1, patron);
            stmt.setString(2, patron);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getInt("id_inventario"),
                        rs.getString("nombre"),
                        rs.getInt("stock"),
                        rs.getInt("stock_minimo"),
                        rs.getDouble("precio_venta"),
                        rs.getString("proveedor"),
                        rs.getString("estado_stock")
                    };
                    modelo.addRow(fila);
                }
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar en el inventario: " + ex.getMessage(), 
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
     * Abre el formulario para agregar un nuevo item al inventario
     */
    private void abrirFormularioAgregar(ActionEvent e) {
        FormularioAgregarInventario formulario = new FormularioAgregarInventario(new javax.swing.JFrame(), true);
        formulario.setVisible(true);
        
        // Recargar la tabla después de agregar
        cargarTabla();
    }
    
    /**
     * Abre el formulario para editar el item del inventario seleccionado
     */
    private void abrirFormularioEditar() {
        int fila = vista.tabla.getSelectedRow();
        
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un item para editar", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener datos del item seleccionado (se cargarán según los campos del formulario)
        
        FormularioEditarInventario formulario = new FormularioEditarInventario(new javax.swing.JFrame(), true);
        
        // Cargar los datos en el formulario (según los campos disponibles)
        // Nota: Necesitarás revisar los campos específicos del formulario de editar inventario
        
        formulario.setVisible(true);
        
        // Recargar la tabla después de editar
        cargarTabla();
    }
    
    /**
     * Elimina el item del inventario seleccionado
     */
    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();
        
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un item para eliminar", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idInventario = (int) vista.tabla.getValueAt(fila, 0);
        String nombreItem = (String) vista.tabla.getValueAt(fila, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(
            vista,
            "¿Está seguro de que desea eliminar \"" + nombreItem + "\" del inventario?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM inventario WHERE id_inventario = ?";
            
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setInt(1, idInventario);
                
                int filasAfectadas = stmt.executeUpdate();
                
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(vista, "Item eliminado correctamente del inventario", 
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo eliminar el item del inventario", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(vista, "Error al eliminar del inventario: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Abre la ventana de reportes de inventario
     */
    private void generarReporte(ActionEvent e) {
        reportesInventario reporte = new reportesInventario(new javax.swing.JFrame(), true);
        reporte.setVisible(true);
    }
}
