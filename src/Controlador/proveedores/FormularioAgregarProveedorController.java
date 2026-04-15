package Controlador.proveedores;

import Vista.proveedores.FormularioAgregarProveedor;
import Modelo.proveedores.ProveedorModel;
import Type.proveedores.ProveedorType;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;

/**
 * Controlador para el formulario de agregar proveedor
 * 
 * @author ossca
 */
public class FormularioAgregarProveedorController {

    private static final Logger logger = Logger.getLogger(FormularioAgregarProveedorController.class.getName());
    private FormularioAgregarProveedor vista;
    private ProveedorModel modelo;

    public FormularioAgregarProveedorController(FormularioAgregarProveedor vista) {
        this.vista = vista;
        this.modelo = new ProveedorModel();
        inicializarEventos();
    }

    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::guardar);

        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);
    }

    /**
     * Guarda un nuevo proveedor en la base de datos
     */
    private void guardar(java.awt.event.ActionEvent e) {
        if (!validarCampos()) {
            return;
        }

        try {
            // Crear objeto ProveedorType con los datos del formulario
            ProveedorType nuevoProveedor = new ProveedorType();
            nuevoProveedor.setIdProveedor(UUID.randomUUID().toString());
            nuevoProveedor.setNombreProveedor(vista.inputNombreProveedor.getText().trim());
            nuevoProveedor.setRtnProveedor(vista.inputRTNProveedor.getText().trim());
            nuevoProveedor.setTelefonoProveedor(vista.inputTelefonoProveedor.getText().trim());
            nuevoProveedor.setEmailProveedor(vista.inputEmailProveedor.getText().trim());
            nuevoProveedor.setDireccionProveedor(vista.inputDireccionProveedor.getText().trim());
            nuevoProveedor.setContactoProveedor(vista.inputContactoProveedor.getText().trim());
            nuevoProveedor.setEstadoProveedor(true);

            // Guardar en base de datos usando el modelo
            if (modelo.create(nuevoProveedor)) {
                JOptionPane.showMessageDialog(vista, "Proveedor guardado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar el proveedor",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al guardar proveedor: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al guardar proveedor. Por favor, intente nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cierra el formulario sin guardar cambios
     */
    private void cancelar(java.awt.event.ActionEvent e) {
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

    /**
     * Valida los campos del formulario antes de guardar
     */
    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        // Validar nombre
        String nombre = vista.inputNombreProveedor.getText().trim();
        if (nombre.isEmpty()) {
            errores.append("- El nombre es obligatorio\n");
        } else if (nombre.length() < 3) {
            errores.append("- El nombre debe tener al menos 3 caracteres\n");
        }

        // Validar RTN (opcional)
        String rtn = vista.inputRTNProveedor.getText().trim();
        if (rtn != null && !rtn.isEmpty()) {
            // Validar formato de RTN hondureño (14 dígitos o formato con guiones)
            if (!Pattern.matches("^\\d{14}$|^\\d{4}-\\d{6}-\\d{4}$", rtn)) {
                errores.append("- El RTN debe tener 14 dígitos o formato XXXXXX-XXXXXX\n");
            }
        }

        // Validar teléfono
        String telefono = vista.inputTelefonoProveedor.getText().trim();
        if (telefono.isEmpty()) {
            errores.append("- El teléfono es obligatorio\n");
        } else if (!Pattern.matches("^\\d{8}$|^\\d{4}-\\d{4}$", telefono)) {
            errores.append("- El teléfono debe tener 8 dígitos o formato XXXX-XXXX\n");
        }

        // Validar email (opcional)
        String email = vista.inputEmailProveedor.getText().trim();
        if (email != null && !email.isEmpty()) {
            if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", email)) {
                errores.append("- El formato del email no es válido\n");
            }
        }

        // Validar contacto
        String contacto = vista.inputContactoProveedor.getText().trim();
        if (contacto.isEmpty()) {
            errores.append("- El contacto es obligatorio\n");
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
     * Limpia todos los campos del formulario
     */
    private void limpiarCampos() {
        vista.inputNombreProveedor.setText("");
        vista.inputRTNProveedor.setText("");
        vista.inputTelefonoProveedor.setText("");
        vista.inputEmailProveedor.setText("");
        vista.inputDireccionProveedor.setText("");
        vista.inputContactoProveedor.setText("");
    }
}
