package Controlador.Usuarios;

import Vista.usuarios.FormularioEditarUsuario;
import Modelo.usuarios.UsuarioModel;
import Type.usuarios.UsuarioType;
import Type.usuarios.PrivilegioUsuario;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Controlador para el formulario de editar usuario
 * 
 * @author ossca
 */
public class FormularioEditarUsuarioController {

    private static final Logger logger = Logger.getLogger(FormularioEditarUsuarioController.class.getName());
    private FormularioEditarUsuario vista;
    private UsuarioModel modelo;
    private String idUsuario;

    public FormularioEditarUsuarioController(FormularioEditarUsuario vista, String idUsuario) {
        this.vista = vista;
        this.idUsuario = idUsuario;
        this.modelo = new UsuarioModel();
        inicializarEventos();
        cargarCombos();
        cargarDatosUsuario();
    }

    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::actualizarUsuario);

        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);
    }

    /**
     * Carga los ComboBox con los valores del enum PrivilegioUsuario
     */
    private void cargarCombos() {
        try {
            // Cargar roles
            if (vista.comboBoxRol != null) {
                vista.comboBoxRol.removeAllItems();
                vista.comboBoxRol.addItem("Seleccionar...");

                for (PrivilegioUsuario privilegio : PrivilegioUsuario.values()) {
                    vista.comboBoxRol.addItem(privilegio.toString());
                }
            }

            // Cargar estados
            if (vista.comboBoxEstado != null) {
                vista.comboBoxEstado.removeAllItems();
                vista.comboBoxEstado.addItem("Seleccionar...");
                vista.comboBoxEstado.addItem("ACTIVO");
                vista.comboBoxEstado.addItem("INACTIVO");
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar combos: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar las opciones. Por favor, intente nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga los datos del usuario en el formulario usando el Modelo
     */
    private void cargarDatosUsuario() {
        try {
            UsuarioType usuario = modelo.getById(idUsuario);
            if (usuario != null) {
                vista.inputNombre.setText(usuario.getNombreUsuario());

                // Cargar rol
                if (vista.comboBoxRol != null && usuario.getPrivilegioUsuario() != null) {
                    vista.comboBoxRol.setSelectedItem(usuario.getPrivilegioUsuario().toString());
                }

                // Cargar estado
                if (vista.comboBoxEstado != null) {
                    vista.comboBoxEstado.setSelectedItem(usuario.isEstadoUsuario() ? "ACTIVO" : "INACTIVO");
                }

                // Cargar imagen (funcionalidad no disponible en este formulario)
                // usuario.getFotoUsuario() contiene la ruta de la foto del usuario
            } else {
                JOptionPane.showMessageDialog(vista, "Usuario no encontrado",
                        "Error", JOptionPane.ERROR_MESSAGE);
                vista.dispose();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar datos del usuario: " + ex.getMessage(), ex);
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

        try {
            // Crear objeto UsuarioType con los datos actualizados
            UsuarioType usuarioActualizado = new UsuarioType();
            usuarioActualizado.setIdUsuario(idUsuario);
            usuarioActualizado.setNombreUsuario(vista.inputNombre.getText().trim());
            usuarioActualizado.setUserUsuario(vista.inputNombre.getText().trim()); // Usar nombre como usuario
            usuarioActualizado.setPassUsuario(""); // No cambiar contraseña en edición
            usuarioActualizado.setFotoUsuario(""); // Mantener foto actual o dejarla vacía
            usuarioActualizado.setEstadoUsuario("ACTIVO".equals(vista.comboBoxEstado.getSelectedItem()));

            // Obtener privilegio del ComboBox
            String seleccion = (String) vista.comboBoxRol.getSelectedItem();
            if (seleccion != null && !seleccion.equals("Seleccionar...")) {
                usuarioActualizado.setPrivilegioUsuario(PrivilegioUsuario.valueOf(seleccion));
            }

            // Actualizar en base de datos usando el modelo
            if (modelo.update(usuarioActualizado)) {
                JOptionPane.showMessageDialog(vista, "Usuario actualizado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo actualizar el usuario",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al actualizar usuario: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al actualizar usuario. Por favor, intente nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Valida los campos del formulario antes de guardar
     */
    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        // Validar nombre
        String nombre = vista.inputNombre.getText().trim();
        if (nombre.isEmpty()) {
            errores.append("- El nombre es obligatorio\n");
        } else if (nombre.length() < 3) {
            errores.append("- El nombre debe tener al menos 3 caracteres\n");
        } else if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            errores.append("- El nombre solo puede contener letras y espacios\n");
        }

        // Validar ComboBox de rol
        String seleccion = (String) vista.comboBoxRol.getSelectedItem();
        if (seleccion == null || seleccion.equals("Seleccionar...")) {
            errores.append("- Debe seleccionar un rol\n");
        }

        // Validar ComboBox de estado
        String estado = (String) vista.comboBoxEstado.getSelectedItem();
        if (estado == null || estado.equals("Seleccionar...")) {
            errores.append("- Debe seleccionar un estado\n");
        }

        if (errores.length() > 0) {
            logger.log(Level.WARNING, "Errores de validación: {0}", errores.toString());
            JOptionPane.showMessageDialog(vista, "Corrija los siguientes errores:\n\n" + errores.toString(),
                    "Errores de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Cierra el formulario sin guardar cambios
     */
    private void cancelar(ActionEvent e) {
        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de que desea cancelar? Los cambios no se guardarán.",
                "Confirmar Cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            vista.dispose();
        }
    }
}
