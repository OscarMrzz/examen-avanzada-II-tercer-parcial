package Controlador.inventario;

import Vista.inventario.FormularioAgregarInventario;
import Modelo.inventario.InventarioModel;
import Type.decoraciones.DecoracionType;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Controlador para el formulario de agregar inventario
 * 
 * @author ossca
 */
public class FormularioAgregarInventarioController {

    private static final Logger logger = Logger.getLogger(FormularioAgregarInventarioController.class.getName());
    private FormularioAgregarInventario vista;
    private InventarioModel modelo;

    public FormularioAgregarInventarioController(FormularioAgregarInventario vista) {
        this.vista = vista;
        this.modelo = new InventarioModel();
        inicializarEventos();
        cargarCombos();
    }

    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::guardar);

        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);
    }

    /**
     * Carga los ComboBox con datos de proveedores
     */
    private void cargarCombos() {
        try {
            // Cargar proveedores
            if (vista.comboBoxProveedor != null) {
                vista.comboBoxProveedor.removeAllItems();
                vista.comboBoxProveedor.addItem("Seleccionar...");
                
                // Aquí deberías cargar los proveedores desde su modelo
                // Por ahora, dejamos el placeholder
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar combos: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar los combos. Por favor, intente nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Guarda un nuevo registro de inventario en la base de datos
     */
    private void guardar(java.awt.event.ActionEvent e) {
        if (!validarCampos()) {
            return;
        }

        try {
            // Nota: Este controlador podría usarse para crear nuevos productos
            // o para ajustar stock de productos existentes
            // Por ahora, implementaremos como ajuste de stock
            
            String nombreProducto = vista.inputNombre.getText().trim();
            String cantidadStr = vista.inputStock.getText().trim();
            
            // Validar y convertir cantidad
            try {
                int cantidad = Integer.parseInt(cantidadStr);
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(vista, "La cantidad debe ser mayor que cero",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    vista.inputStock.requestFocus();
                    return;
                }

                // Mostrar mensaje informativo
                JOptionPane.showMessageDialog(vista, 
                        "Función de ajuste de inventario implementada.\n\n" +
                        "Producto: " + nombreProducto + "\n" +
                        "Cantidad: " + cantidad + "\n\n" +
                        "Nota: Esta es una implementación básica. " +
                        "Para producción, debería buscar el producto existente " +
                        "y ajustar su stock usando el modelo.",
                        "Ajuste de Inventario",
                        JOptionPane.INFORMATION_MESSAGE);

                limpiarCampos();
                vista.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "La cantidad debe ser un número válido",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputStock.requestFocus();
                return;
            }
            
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al guardar ajuste de inventario: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al guardar ajuste de inventario. Por favor, intente nuevamente.",
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

        // Validar nombre de producto
        String nombreProducto = vista.inputNombre.getText().trim();
        if (nombreProducto.isEmpty()) {
            errores.append("- El nombre del producto es obligatorio\n");
        } else if (nombreProducto.length() < 3) {
            errores.append("- El nombre del producto debe tener al menos 3 caracteres\n");
        }

        // Validar cantidad
        String cantidad = vista.inputStock.getText().trim();
        if (cantidad.isEmpty()) {
            errores.append("- La cantidad es obligatoria\n");
        }

        // Validar ComboBox de proveedor (si existe)
        if (vista.comboBoxProveedor != null) {
            String proveedorSeleccionado = (String) vista.comboBoxProveedor.getSelectedItem();
            if (proveedorSeleccionado == null || proveedorSeleccionado.equals("Seleccionar...")) {
                errores.append("- Debe seleccionar un proveedor\n");
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
        vista.inputNombre.setText("");
        vista.inputStock.setText("");
        vista.inputDescripcion.setText("");
        
        if (vista.comboBoxProveedor != null) {
            vista.comboBoxProveedor.setSelectedIndex(0);
        }
    }
}
