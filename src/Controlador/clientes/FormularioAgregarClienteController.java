package Controlador.clientes;

import Vista.clientes.FormularioAgregarCliente;
import Modelo.Conexion;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Controlador para el formulario de agregar cliente
 * @author ossca
 */
public class FormularioAgregarClienteController {
    
    private FormularioAgregarCliente vista;
    private Connection conexion;
    
    public FormularioAgregarClienteController(FormularioAgregarCliente vista) {
        this.vista = vista;
        Conexion conexionObj = new Conexion();
        this.conexion = conexionObj.getConxion();
        inicializarEventos();
    }
    
    private void inicializarEventos() {
        // Evento del botón Guardar
        vista.botonGuardar.addActionListener(this::guardarCliente);
        
        // Evento del botón Cancelar
        vista.botonCancelar.addActionListener(this::cerrarFormulario);
    }
    
    /**
     * Guarda un nuevo cliente en la base de datos
     */
    private void guardarCliente(ActionEvent e) {
        if (!validarCampos()) {
            return;
        }
        
        String nombre = vista.inputNombreCliente.getText().trim();
        String rtn = vista.inputRTNCliente.getText().trim();
        String tipo = (String) vista.comboBoxTipoCliente.getSelectedItem();
        String telefono = vista.inputTelefonoCliente.getText().trim();
        String email = vista.inputEmailCliente.getText().trim();
        String direccion = vista.inputDireccionCliente.getText().trim();
        String estado = "ACTIVO"; // Por defecto
        
        String sql = "INSERT INTO clientes (nombre, rtn, tipo, telefono, email, direccion, estado) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, rtn);
            stmt.setString(3, tipo);
            stmt.setString(4, telefono);
            stmt.setString(5, email);
            stmt.setString(6, direccion);
            stmt.setString(7, estado);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(vista, "Cliente agregado correctamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo agregar el cliente", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al agregar el cliente: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Cierra el formulario al presionar Cancelar
     */
    private void cerrarFormulario(ActionEvent e) {
        int confirmacion = JOptionPane.showConfirmDialog(
            vista,
            "¿Está seguro de que desea cancelar? Los datos no guardados se perderán.",
            "Confirmar Cancelación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            vista.dispose();
        }
    }
    
    /**
     * Valida que todos los campos estén completos
     */
    private boolean validarCampos() {
        String nombre = vista.inputNombreCliente.getText().trim();
        String rtn = vista.inputRTNCliente.getText().trim();
        String tipo = (String) vista.comboBoxTipoCliente.getSelectedItem();
        String telefono = vista.inputTelefonoCliente.getText().trim();
        
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor ingrese el nombre del cliente", 
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            vista.inputNombreCliente.requestFocus();
            return false;
        }
        
        if (rtn.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor ingrese el RTN del cliente", 
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            vista.inputRTNCliente.requestFocus();
            return false;
        }
        
        if (tipo == null || tipo.equals("Seleccionar...")) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione el tipo de cliente", 
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            vista.comboBoxTipoCliente.requestFocus();
            return false;
        }
        
        if (telefono.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor ingrese el teléfono del cliente", 
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            vista.inputTelefonoCliente.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Limpia todos los campos del formulario
     */
    private void limpiarCampos() {
        vista.inputNombreCliente.setText("");
        vista.inputRTNCliente.setText("");
        vista.comboBoxTipoCliente.setSelectedIndex(0);
        vista.inputTelefonoCliente.setText("");
        vista.inputEmailCliente.setText("");
        vista.inputDireccionCliente.setText("");
    }
}
