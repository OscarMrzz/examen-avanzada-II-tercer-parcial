package Controlador.proveedores;

import Vista.proveedores.FormularioEditarProveedor;
import Modelo.proveedores.ProveedorModel;
import Type.proveedores.ProveedorType;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;

/**
 * Controlador para el formulario de editar proveedor
 * 
 * @author ossca
 */
public class FormularioEditarProveedorController {

    private static final Logger logger = Logger.getLogger(FormularioEditarProveedorController.class.getName());
    private FormularioEditarProveedor vista;
    private ProveedorModel modelo;
    private String idProveedor;

    public FormularioEditarProveedorController(FormularioEditarProveedor vista, String idProveedor) {
        this.vista = vista;
        this.idProveedor = idProveedor;
        this.modelo = new ProveedorModel();
        inicializarEventos();
        cargarDatosProveedor();
    }

    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::actualizar);

        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);
    }

    /**
     * Carga los datos del proveedor en el formulario usando el Modelo
     */
    private void cargarDatosProveedor() {
        try {
            ProveedorType proveedor = modelo.getById(idProveedor);
            if (proveedor != null) {
                vista.inputNombreProveedor.setText(proveedor.getNombreProveedor());
                vista.inputRTNProveedor.setText(proveedor.getRtnProveedor() != null ? proveedor.getRtnProveedor() : "");
                vista.inputTelefonoProveedor.setText(proveedor.getTelefonoProveedor());
                vista.inputEmailProveedor.setText(proveedor.getEmailProveedor() != null ? proveedor.getEmailProveedor() : "");
                vista.inputDireccionProveedor.setText(proveedor.getDireccionProveedor() != null ? proveedor.getDireccionProveedor() : "");
                vista.inputContactoProveedor.setText(proveedor.getContactoProveedor() != null ? proveedor.getContactoProveedor() : "");
            } else {
                JOptionPane.showMessageDialog(vista, "Proveedor no encontrado",
                        "Error", JOptionPane.ERROR_MESSAGE);
                vista.dispose();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar datos del proveedor: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar datos del proveedor: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            vista.dispose();
        }
    }

    /**
     * Actualiza los datos del proveedor en la base de datos
     */
    private void actualizar(java.awt.event.ActionEvent e) {
        if (!validarCampos()) {
            return;
        }

        try {
            // Crear objeto ProveedorType con los datos actualizados
            ProveedorType proveedorActualizado = new ProveedorType();
            proveedorActualizado.setIdProveedor(idProveedor);
            proveedorActualizado.setNombreProveedor(vista.inputNombreProveedor.getText().trim());
            proveedorActualizado.setRtnProveedor(vista.inputRTNProveedor.getText().trim());
            proveedorActualizado.setTelefonoProveedor(vista.inputTelefonoProveedor.getText().trim());
            proveedorActualizado.setEmailProveedor(vista.inputEmailProveedor.getText().trim());
            proveedorActualizado.setDireccionProveedor(vista.inputDireccionProveedor.getText().trim());
            proveedorActualizado.setContactoProveedor(vista.inputContactoProveedor.getText().trim());
            proveedorActualizado.setEstadoProveedor(true);

            // Actualizar en base de datos usando el modelo
            if (modelo.update(proveedorActualizado)) {
                JOptionPane.showMessageDialog(vista, "Proveedor actualizado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo actualizar el proveedor",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al actualizar proveedor: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al actualizar proveedor. Por favor, intente nuevamente.",
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
}
