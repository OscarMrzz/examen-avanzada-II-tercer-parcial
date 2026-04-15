package Controlador.Usuarios;

import Vista.usuarios.FormularioEditarUsuario;
import Modelo.Conexion;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Controlador para el formulario de editar usuario
 * 
 * @author ossca
 */
public class FormularioEditarUsuarioController {

    private FormularioEditarUsuario vista;
    private Connection conexion;
    private String idUsuario;

    public FormularioEditarUsuarioController(FormularioEditarUsuario vista, String idUsuario) {
        this.vista = vista;
        this.idUsuario = idUsuario;
        Conexion conexionObj = new Conexion();
        this.conexion = conexionObj.getConxion();
        inicializarEventos();
        cargarDatosUsuario();
    }

    private void inicializarEventos() {
        // Evento del botón Guardar
        vista.botonGuardar.addActionListener(this::actualizarUsuario);

        // Evento del botón Cancelar
        vista.botonCancelar.addActionListener(this::cerrarFormulario);
    }

    /**
     * Carga los datos del usuario en el formulario
     */
    private void cargarDatosUsuario() {
        String sql = "SELECT nombre_usuario, privilegio_usuario, estado_usuario FROM usuarios WHERE id_usuario = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    vista.inputNombre.setText(rs.getString("nombre_usuario"));
                    vista.comboBoxRol.setSelectedItem(rs.getString("privilegio_usuario"));
                    vista.comboBoxEstado.setSelectedItem(rs.getBoolean("estado_usuario") ? "ACTIVO" : "INACTIVO");
                } else {
                    JOptionPane.showMessageDialog(vista, "Usuario no encontrado",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    vista.dispose();
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar datos del usuario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            vista.dispose();
        }
    }

    /**
     * Actualiza los datos del usuario en la base de datos
     */
    private void actualizarUsuario(ActionEvent e) {
        if (!validarCampos()) {
            return;
        }

        String nombre = vista.inputNombre.getText().trim();
        String rol = (String) vista.comboBoxRol.getSelectedItem();
        String estado = (String) vista.comboBoxEstado.getSelectedItem();

        // Convertir "ACTIVO"/"INACTIVO" a boolean
        boolean estadoUsuario = "ACTIVO".equals(estado);

        String sql = "UPDATE usuarios SET nombre_usuario = ?, privilegio_usuario = ?, estado_usuario = ? WHERE id_usuario = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, rol);
            stmt.setBoolean(3, estadoUsuario);
            stmt.setString(4, idUsuario);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(vista, "Usuario actualizado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo actualizar el usuario",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar el usuario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cierra el formulario al presionar Cancelar
     */
    private void cerrarFormulario(ActionEvent e) {
        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de que desea cancelar? Los cambios no guardados se perderán.",
                "Confirmar Cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            vista.dispose();
        }
    }

    /**
     * Valida que todos los campos estén completos
     */
    private boolean validarCampos() {
        String nombre = vista.inputNombre.getText().trim();
        String rol = (String) vista.comboBoxRol.getSelectedItem();
        String estado = (String) vista.comboBoxEstado.getSelectedItem();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor ingrese el nombre del usuario",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            vista.inputNombre.requestFocus();
            return false;
        }

        if (rol == null || rol.equals("Seleccionar...")) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un rol",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            vista.comboBoxRol.requestFocus();
            return false;
        }

        if (estado == null || estado.equals("Seleccionar...")) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un estado",
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            vista.comboBoxEstado.requestFocus();
            return false;
        }

        return true;
    }
}
