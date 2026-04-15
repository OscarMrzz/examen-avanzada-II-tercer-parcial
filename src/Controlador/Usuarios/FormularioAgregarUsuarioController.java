package Controlador.Usuarios;

import Vista.usuarios.FormularioAgregarUsuario;
import Modelo.Conexion;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Controlador para el formulario de agregar usuario
 * 
 * @author ossca
 */
public class FormularioAgregarUsuarioController {

    private FormularioAgregarUsuario vista;
    private Connection conexion;

    public FormularioAgregarUsuarioController(FormularioAgregarUsuario vista) {
        this.vista = vista;
        Conexion conexionObj = new Conexion();
        this.conexion = conexionObj.getConxion();
        inicializarEventos();
    }

    private void inicializarEventos() {
        // Evento del botón Guardar
        vista.botonGuardar.addActionListener(this::guardarUsuario);

        // Evento del botón Cancelar
        vista.botonCancelar.addActionListener(this::cerrarFormulario);

        // Evento del botón Agregar Imagen (si aplica)
        vista.botonAgregarImagen.addActionListener(this::agregarImagen);
    }

    /**
     * Guarda un nuevo usuario en la base de datos
     */
    private void guardarUsuario(ActionEvent e) {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        String nombre = vista.inputNombre.getText().trim();
        String password = vista.inputPassword.getText().trim();
        String rol = (String) vista.comboBoxRol.getSelectedItem();
        String estado = "ACTIVO"; // Por defecto

        String sql = "INSERT INTO usuarios (nombre, password, rol, estado) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, password);
            stmt.setString(3, rol);
            stmt.setString(4, estado);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(vista, "Usuario agregado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                vista.dispose(); // Cerrar el formulario después de guardar
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo agregar el usuario",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al agregar el usuario: " + ex.getMessage(),
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
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            vista.dispose();
        }
    }

    /**
     * Agrega una imagen de perfil (función placeholder)
     */
    private void agregarImagen(ActionEvent e) {
        JOptionPane.showMessageDialog(vista,
                "Función de agregar imagen en desarrollo",
                "Agregar Imagen",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Valida que todos los campos estén completos
     * 
     * @return true si todos los campos son válidos, false de lo contrario
     */
    private boolean validarCampos() {
        String nombre = vista.inputNombre.getText().trim();
        String password = vista.inputPassword.getText().trim();
        String rol = (String) vista.comboBoxRol.getSelectedItem();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor ingrese el nombre del usuario",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            vista.inputNombre.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor ingrese la contraseña",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            vista.inputPassword.requestFocus();
            return false;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(vista, "La contraseña debe tener al menos 4 caracteres",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            vista.inputPassword.requestFocus();
            return false;
        }

        if (rol == null || rol.equals("Seleccionar...")) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un rol",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            vista.comboBoxRol.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Limpia todos los campos del formulario
     */
    private void limpiarCampos() {
        vista.inputNombre.setText("");
        vista.inputPassword.setText("");
        vista.comboBoxRol.setSelectedIndex(0);
    }
}
