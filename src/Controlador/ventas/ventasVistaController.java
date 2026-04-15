package Controlador.ventas;

import Vista.ventas.ventasVista;
import Vista.ventas.FormularioAgregarVenta;
import Vista.ventas.FormularioEditarVenta;
import Vista.ventas.reportesVentas;
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
 * Controlador para la vista de ventas
 * @author ossca
 */
public class ventasVistaController {
    
    private ventasVista vista;
    private Connection conexion;
    
    public ventasVistaController(ventasVista vista) {
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
                if (vista.inputBusqueda.getText().equals("Buscar venta...")) {
                    vista.inputBusqueda.setText("");
                }
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().isEmpty()) {
                    vista.inputBusqueda.setText("Buscar venta...");
                }
            }
        });
    }
    
    /**
     * Carga todas las ventas en la tabla
     */
    public void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);
        
        String sql = "SELECT v.id_venta, v.numero_factura, v.fecha_venta, " +
                    "c.nombre as cliente, v.total, v.tipo_pago, v.estado " +
                    "FROM ventas v " +
                    "LEFT JOIN clientes c ON v.id_cliente = c.id_cliente " +
                    "ORDER BY v.id_venta DESC";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_venta"),
                    rs.getString("numero_factura"),
                    rs.getDate("fecha_venta"),
                    rs.getString("cliente"),
                    rs.getDouble("total"),
                    rs.getString("tipo_pago"),
                    rs.getString("estado")
                };
                modelo.addRow(fila);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar las ventas: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Busca ventas según el texto ingresado
     */
    private void buscar(ActionEvent e) {
        String textoBusqueda = vista.inputBusqueda.getText().trim();
        
        if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar venta...")) {
            cargarTabla();
            return;
        }
        
        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);
        
        String sql = "SELECT v.id_venta, v.numero_factura, v.fecha_venta, " +
                    "c.nombre as cliente, v.total, v.tipo_pago, v.estado " +
                    "FROM ventas v " +
                    "LEFT JOIN clientes c ON v.id_cliente = c.id_cliente " +
                    "WHERE v.numero_factura LIKE ? OR c.nombre LIKE ? OR v.estado LIKE ? " +
                    "ORDER BY v.id_venta DESC";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            String patron = "%" + textoBusqueda + "%";
            stmt.setString(1, patron);
            stmt.setString(2, patron);
            stmt.setString(3, patron);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getInt("id_venta"),
                        rs.getString("numero_factura"),
                        rs.getDate("fecha_venta"),
                        rs.getString("cliente"),
                        rs.getDouble("total"),
                        rs.getString("tipo_pago"),
                        rs.getString("estado")
                    };
                    modelo.addRow(fila);
                }
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar ventas: " + ex.getMessage(), 
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
     * Abre el formulario para agregar una nueva venta
     */
    private void abrirFormularioAgregar(ActionEvent e) {
        FormularioAgregarVenta formulario = new FormularioAgregarVenta(new javax.swing.JFrame(), true);
        formulario.setVisible(true);
        
        // Recargar la tabla después de agregar
        cargarTabla();
    }
    
    /**
     * Abre el formulario para editar la venta seleccionada
     */
    private void abrirFormularioEditar() {
        int fila = vista.tabla.getSelectedRow();
        
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una venta para editar", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener datos de la venta seleccionada (se cargarán según los campos del formulario)
        
        FormularioEditarVenta formulario = new FormularioEditarVenta(new javax.swing.JFrame(), true);
        
        // Cargar los datos en el formulario (según los campos disponibles)
        // Nota: Necesitarás revisar los campos específicos del formulario de editar venta
        
        formulario.setVisible(true);
        
        // Recargar la tabla después de editar
        cargarTabla();
    }
    
    /**
     * Elimina la venta seleccionada
     */
    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();
        
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una venta para eliminar", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idVenta = (int) vista.tabla.getValueAt(fila, 0);
        String numeroFactura = (String) vista.tabla.getValueAt(fila, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(
            vista,
            "¿Está seguro de que desea eliminar la venta con factura #" + numeroFactura + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM ventas WHERE id_venta = ?";
            
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setInt(1, idVenta);
                
                int filasAfectadas = stmt.executeUpdate();
                
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(vista, "Venta eliminada correctamente", 
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo eliminar la venta", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(vista, "Error al eliminar la venta: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Abre la ventana de reportes de ventas
     */
    private void generarReporte(ActionEvent e) {
        reportesVentas reporte = new reportesVentas(new javax.swing.JFrame(), true);
        reporte.setVisible(true);
    }
}
