package Controlador.colecciones;

import Vista.colecciones.FormularioAgregarColeccion;
import Modelo.colecciones.ColeccionModel;
import Type.colecciones.ColeccionType;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Controlador para el formulario de agregar colección
 * 
 * @author ossca
 */
public class FormularioAgregarColeccionController {

    private static final Logger logger = Logger.getLogger(FormularioAgregarColeccionController.class.getName());
    private FormularioAgregarColeccion vista;
    private ColeccionModel modelo;

    public FormularioAgregarColeccionController(FormularioAgregarColeccion vista) {
        this.vista = vista;
        this.modelo = new ColeccionModel();
        inicializarEventos();
    }

    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::guardar);

        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);
    }

    /**
     * Guarda una nueva colección en la base de datos
     */
    private void guardar(java.awt.event.ActionEvent e) {
        if (!validarCampos()) {
            return;
        }

        try {
            // Crear objeto ColeccionType con los datos del formulario
            ColeccionType nuevaColeccion = new ColeccionType();
            nuevaColeccion.setIdColeccion(UUID.randomUUID().toString());
            nuevaColeccion.setNombreColeccion(vista.inputNombreColeccion.getText().trim());
            nuevaColeccion.setDisenadorColeccion(vista.inputDisenadorColeccion.getText().trim());
            nuevaColeccion.setNumColeccionColeccion(vista.inputNumColeccionColeccion.getText().trim());
            
            // Validar y convertir año
            String anioStr = vista.inputAnioColeccion.getText().trim();
            try {
                int anio = Integer.parseInt(anioStr);
                // Validar que el año sea razonable (entre 1900 y 2100)
                if (anio < 1900 || anio > 2100) {
                    JOptionPane.showMessageDialog(vista, "El año debe estar entre 1900 y 2100",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    vista.inputAnioColeccion.requestFocus();
                    return;
                }
                nuevaColeccion.setAnioColeccion(anio);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El año debe ser un número válido",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputAnioColeccion.requestFocus();
                return;
            }
            
            nuevaColeccion.setDescripcionColeccion(vista.inputDescripcionColeccion.getText().trim());
            nuevaColeccion.setEstadoColeccion(true);

            // Guardar en base de datos usando el modelo
            if (modelo.create(nuevaColeccion)) {
                JOptionPane.showMessageDialog(vista, "Colección guardada correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar la colección",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al guardar colección: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al guardar colección. Por favor, intente nuevamente.",
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
        String nombre = vista.inputNombreColeccion.getText().trim();
        if (nombre.isEmpty()) {
            errores.append("- El nombre es obligatorio\n");
        } else if (nombre.length() < 3) {
            errores.append("- El nombre debe tener al menos 3 caracteres\n");
        }

        // Validar diseñador
        String disenador = vista.inputDisenadorColeccion.getText().trim();
        if (disenador.isEmpty()) {
            errores.append("- El diseñador es obligatorio\n");
        } else if (disenador.length() < 3) {
            errores.append("- El diseñador debe tener al menos 3 caracteres\n");
        }

        // Validar número de colección
        String numColeccion = vista.inputNumColeccionColeccion.getText().trim();
        if (numColeccion.isEmpty()) {
            errores.append("- El número de colección es obligatorio\n");
        }

        // Validar año
        String anio = vista.inputAnioColeccion.getText().trim();
        if (anio.isEmpty()) {
            errores.append("- El año es obligatorio\n");
        } else {
            try {
                int anioInt = Integer.parseInt(anio);
                if (anioInt < 1900 || anioInt > 2100) {
                    errores.append("- El año debe estar entre 1900 y 2100\n");
                }
            } catch (NumberFormatException ex) {
                errores.append("- El año debe ser un número válido\n");
            }
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
        vista.inputNombreColeccion.setText("");
        vista.inputDisenadorColeccion.setText("");
        vista.inputNumColeccionColeccion.setText("");
        vista.inputAnioColeccion.setText("");
        vista.inputDescripcionColeccion.setText("");
    }
}
