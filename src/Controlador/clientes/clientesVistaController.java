package Controlador.clientes;

import Vista.clientes.clientesVista;
import Vista.clientes.FormularioAgregarCliente;
import Vista.clientes.FormularioEditarCliente;
import Vista.clientes.reportesClientes;
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
 * Controlador para la vista de clientes
 * 
 * @author ossca
 */
public class clientesVistaController {

    private clientesVista vista;
    private Connection conexion;

    public clientesVistaController(clientesVista vista) {
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
                if (vista.inputBusqueda.getText().equals("Buscar cliente...")) {
                    vista.inputBusqueda.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().isEmpty()) {
                    vista.inputBusqueda.setText("Buscar cliente...");
                }
            }
        });
    }

    /**
     * Carga todos los clientes en la tabla
     */
    public void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);

        String sql = "SELECT id_cliente, nombre, rtn, tipo, telefono, estado FROM clientes ORDER BY id_cliente";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("rtn"),
                        rs.getString("tipo"),
                        rs.getString("telefono"),
                        rs.getString("estado")
                };
                modelo.addRow(fila);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar los clientes: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca clientes según el texto ingresado
     */
    private void buscar(ActionEvent e) {
        String textoBusqueda = vista.inputBusqueda.getText().trim();

        if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar cliente...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);

        String sql = "SELECT id_cliente, nombre, rtn, tipo, telefono, estado FROM clientes " +
                "WHERE nombre LIKE ? OR rtn LIKE ? OR telefono LIKE ? ORDER BY id_cliente";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            String patron = "%" + textoBusqueda + "%";
            stmt.setString(1, patron);
            stmt.setString(2, patron);
            stmt.setString(3, patron);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                            rs.getInt("id_cliente"),
                            rs.getString("nombre"),
                            rs.getString("rtn"),
                            rs.getString("tipo"),
                            rs.getString("telefono"),
                            rs.getString("estado")
                    };
                    modelo.addRow(fila);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar clientes: " + ex.getMessage(),
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
     * Abre el formulario para agregar un nuevo cliente
     */
    private void abrirFormularioAgregar(ActionEvent e) {
        FormularioAgregarCliente formulario = new FormularioAgregarCliente(new javax.swing.JFrame(), true);
        formulario.setVisible(true);

        // Recargar la tabla después de agregar
        cargarTabla();
    }

    /**
     * Abre el formulario para editar el cliente seleccionado
     */
    private void abrirFormularioEditar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un cliente para editar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos del cliente seleccionado
        String nombre = (String) vista.tabla.getValueAt(fila, 1);
        String rtn = (String) vista.tabla.getValueAt(fila, 2);
        String tipo = (String) vista.tabla.getValueAt(fila, 3);
        String telefono = (String) vista.tabla.getValueAt(fila, 4);

        FormularioEditarCliente formulario = new FormularioEditarCliente(new javax.swing.JFrame(), true);

        // Cargar los datos en el formulario
        formulario.inputNombreCliente.setText(nombre);
        formulario.inputRTNCliente.setText(rtn);
        formulario.comboBoxTipoCliente.setSelectedItem(tipo);
        formulario.inputTelefonoCliente.setText(telefono);

        // Guardar el ID del cliente en el formulario para la edición
        // El ID del cliente se puede manejar a través de una variable estática o método
        // setter en el formulario

        formulario.setVisible(true);

        // Recargar la tabla después de editar
        cargarTabla();
    }

    /**
     * Elimina el cliente seleccionado
     */
    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un cliente para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCliente = (int) vista.tabla.getValueAt(fila, 0);
        String nombreCliente = (String) vista.tabla.getValueAt(fila, 1);

        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de que desea eliminar al cliente \"" + nombreCliente + "\"?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM clientes WHERE id_cliente = ?";

            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setInt(1, idCliente);

                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(vista, "Cliente eliminado correctamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo eliminar el cliente",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(vista, "Error al eliminar el cliente: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Abre la ventana de reportes de clientes
     */
    private void generarReporte(ActionEvent e) {
        reportesClientes reporte = new reportesClientes(new javax.swing.JFrame(), true);
        reporte.setVisible(true);
    }
}
