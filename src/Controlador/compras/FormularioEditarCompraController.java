package Controlador.compras;

import Vista.compras.FormularioEditarCompra;
import Modelo.compras.FacturaCompraModel;
import Type.compras.FacturaCompraType;
import Type.generales.TipoPago;
import Type.generales.EstadoFactura;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Controlador para el formulario de editar compra
 * 
 * @author ossca
 */
public class FormularioEditarCompraController {

    private static final Logger logger = Logger.getLogger(FormularioEditarCompraController.class.getName());
    private FormularioEditarCompra vista;
    private FacturaCompraModel modelo;
    private String idFactura;

    public FormularioEditarCompraController(FormularioEditarCompra vista, String idFactura) {
        this.vista = vista;
        this.idFactura = idFactura;
        this.modelo = new FacturaCompraModel();
        inicializarEventos();
        cargarDatosFactura();
    }

    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::actualizar);

        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);
    }

    /**
     * Carga los datos de la factura en el formulario usando el Modelo
     */
    private void cargarDatosFactura() {
        try {
            FacturaCompraType factura = modelo.getById(idFactura);
            if (factura != null) {
                vista.inputFechaCompra.setText(factura.getFechaFacturaCompra().toString());
                vista.inputTotalCompra.setText(String.valueOf(factura.getTotalFacturaCompra()));
                vista.inputObservaciones
                        .setText(factura.getCondicionFactura() != null ? factura.getCondicionFactura() : "");

                // Formatear fecha para mostrar
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (factura.getFechaFacturaCompra() != null) {
                    vista.inputFechaCompra.setText(sdf.format(factura.getFechaFacturaCompra()));
                }

                // Cargar estado en el ComboBox
                if (factura.getEstadoFacturaCompra() != null && vista.comboBoxEstado != null) {
                    if (factura.getEstadoFacturaCompra() == EstadoFactura.PAGADA) {
                        vista.comboBoxEstado.setSelectedItem("Completada");
                    } else {
                        vista.comboBoxEstado.setSelectedItem("Pendiente");
                    }
                }

                // Cargar proveedor en el ComboBox
                if (factura.getIdProveedorFacturaCompra() != null && vista.comboBoxProveedor != null) {
                    vista.comboBoxProveedor.setSelectedItem(factura.getIdProveedorFacturaCompra());
                }

            } else {
                JOptionPane.showMessageDialog(vista, "Factura de compra no encontrada",
                        "Error", JOptionPane.ERROR_MESSAGE);
                vista.dispose();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar datos de la factura: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar datos de la factura: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            vista.dispose();
        }
    }

    /**
     * Actualiza los datos de la factura en la base de datos
     */
    private void actualizar(java.awt.event.ActionEvent e) {
        if (!validarCampos()) {
            return;
        }

        try {
            // Crear objeto FacturaCompraType con los datos actualizados
            FacturaCompraType facturaActualizada = new FacturaCompraType();
            facturaActualizada.setIdFacturaCompra(idFactura);
            facturaActualizada.setCondicionFactura(vista.inputObservaciones.getText().trim());

            // Validar y convertir total
            String totalStr = vista.inputTotalCompra.getText().trim();
            try {
                double total = Double.parseDouble(totalStr);
                if (total <= 0) {
                    JOptionPane.showMessageDialog(vista, "El total debe ser mayor que cero",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    vista.inputTotalCompra.requestFocus();
                    return;
                }
                facturaActualizada.setTotalFacturaCompra(total);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El total debe ser un número válido",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputTotalCompra.requestFocus();
                return;
            }

            // Validar y convertir fecha de factura
            String fechaFacturaStr = vista.inputFechaCompra.getText().trim();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaFactura = sdf.parse(fechaFacturaStr);
                facturaActualizada.setFechaFacturaCompra(new java.sql.Date(fechaFactura.getTime()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "La fecha de factura debe tener formato yyyy-MM-dd",
                        "Error de Validacion", JOptionPane.ERROR_MESSAGE);
                vista.inputFechaCompra.requestFocus();
                return;
            }

            // Obtener estado del ComboBox
            String estadoSeleccionado = (String) vista.comboBoxEstado.getSelectedItem();
            if (estadoSeleccionado != null) {
                if (estadoSeleccionado.equals("Completada")) {
                    facturaActualizada.setEstadoFacturaCompra(Type.generales.EstadoFactura.PAGADA);
                } else {
                    facturaActualizada.setEstadoFacturaCompra(Type.generales.EstadoFactura.PENDIENTE);
                }
            }

            // Obtener proveedor del ComboBox
            String proveedorSeleccionado = (String) vista.comboBoxProveedor.getSelectedItem();
            if (proveedorSeleccionado != null && !proveedorSeleccionado.equals("Seleccione un proveedor")) {
                // Aquí deberías obtener el ID real del proveedor
                facturaActualizada.setIdProveedorFacturaCompra(proveedorSeleccionado);
            }

            // Actualizar en base de datos usando el modelo
            if (modelo.update(facturaActualizada)) {
                JOptionPane.showMessageDialog(vista, "Factura de compra actualizada correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo actualizar la factura de compra",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al actualizar factura de compra: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista,
                    "Error al actualizar factura de compra. Por favor, intente nuevamente.",
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

        // Validar total
        String total = vista.inputTotalCompra.getText().trim();
        if (total.isEmpty()) {
            errores.append("- El total es obligatorio\n");
        }

        // Validar fecha de factura
        String fechaFactura = vista.inputFechaCompra.getText().trim();
        if (fechaFactura.isEmpty()) {
            errores.append("- La fecha de compra es obligatoria\n");
        }

        // Validar ComboBox de estado
        String estadoSeleccionado = (String) vista.comboBoxEstado.getSelectedItem();
        if (estadoSeleccionado == null) {
            errores.append("- Debe seleccionar un estado\n");
        }

        // Validar ComboBox de proveedor
        String proveedorSeleccionado = (String) vista.comboBoxProveedor.getSelectedItem();
        if (proveedorSeleccionado == null || proveedorSeleccionado.equals("Seleccione un proveedor")) {
            errores.append("- Debe seleccionar un proveedor\n");
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
