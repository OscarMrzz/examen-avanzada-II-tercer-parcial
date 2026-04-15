package Controlador.ventas;

import Vista.ventas.FormularioEditarVenta;
import Modelo.ventas.VentaModel;
import Type.ventas.VentaType;
import Type.generales.TipoPagoVenta;
import Type.generales.EstadoVenta;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Controlador para el formulario de editar venta
 * 
 * @author ossca
 */
public class FormularioEditarVentaController {

    private static final Logger logger = Logger.getLogger(FormularioEditarVentaController.class.getName());
    private FormularioEditarVenta vista;
    private VentaModel modelo;
    private String idVenta;

    public FormularioEditarVentaController(FormularioEditarVenta vista, String idVenta) {
        this.vista = vista;
        this.idVenta = idVenta;
        this.modelo = new VentaModel();
        inicializarEventos();
        cargarDatosVenta();
    }

    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::actualizar);

        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);
    }

    /**
     * Carga los datos de la venta en el formulario usando el Modelo
     */
    private void cargarDatosVenta() {
        try {
            VentaType venta = modelo.getById(idVenta);
            if (venta != null) {
                vista.inputNumeroFactura.setText(venta.getNumeroFacturaVenta());
                vista.inputSubtotal.setText(String.valueOf(venta.getSubtotalVenta()));
                vista.inputImpuesto.setText(String.valueOf(venta.getImpuestoVenta()));
                vista.inputDescuento.setText(String.valueOf(venta.getDescuentoVenta()));
                vista.inputTotal.setText(String.valueOf(venta.getTotalVenta()));
                vista.inputMontoEfectivo.setText(String.valueOf(venta.getMontoEfectivo()));
                vista.inputMontoTarjeta.setText(String.valueOf(venta.getMontoTarjeta()));
                vista.inputCambio.setText(String.valueOf(venta.getCambioVenta()));
                
                // Cargar tipo de pago en el ComboBox
                if (venta.getTipoPagoVenta() != null && vista.comboBoxTipoPago != null) {
                    vista.comboBoxTipoPago.setSelectedItem(venta.getTipoPagoVenta().toString());
                }
                
                // Cargar cliente en el ComboBox
                if (venta.getIdClienteVenta() != null && vista.comboBoxCliente != null) {
                    vista.comboBoxCliente.setSelectedItem(venta.getIdClienteVenta());
                }
                
            } else {
                JOptionPane.showMessageDialog(vista, "Venta no encontrada",
                        "Error", JOptionPane.ERROR_MESSAGE);
                vista.dispose();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar datos de la venta: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar datos de la venta: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            vista.dispose();
        }
    }

    /**
     * Actualiza los datos de la venta en la base de datos
     */
    private void actualizar(java.awt.event.ActionEvent e) {
        if (!validarCampos()) {
            return;
        }

        try {
            // Crear objeto VentaType con los datos actualizados
            VentaType ventaActualizada = new VentaType();
            ventaActualizada.setIdVenta(idVenta);
            ventaActualizada.setNumeroFacturaVenta(vista.inputNumeroFactura.getText().trim());
            ventaActualizada.setEstadoVenta(EstadoVenta.ACTIVA);

            // Validar y convertir subtotal
            String subtotalStr = vista.inputSubtotal.getText().trim();
            try {
                double subtotal = Double.parseDouble(subtotalStr);
                if (subtotal < 0) {
                    JOptionPane.showMessageDialog(vista, "El subtotal no puede ser negativo",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    vista.inputSubtotal.requestFocus();
                    return;
                }
                ventaActualizada.setSubtotalVenta(subtotal);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El subtotal debe ser un número válido",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputSubtotal.requestFocus();
                return;
            }

            // Validar y convertir impuesto
            String impuestoStr = vista.inputImpuesto.getText().trim();
            try {
                double impuesto = Double.parseDouble(impuestoStr);
                if (impuesto < 0) {
                    JOptionPane.showMessageDialog(vista, "El impuesto no puede ser negativo",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    vista.inputImpuesto.requestFocus();
                    return;
                }
                ventaActualizada.setImpuestoVenta(impuesto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El impuesto debe ser un número válido",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputImpuesto.requestFocus();
                return;
            }

            // Validar y convertir descuento
            String descuentoStr = vista.inputDescuento.getText().trim();
            try {
                double descuento = Double.parseDouble(descuentoStr);
                if (descuento < 0) {
                    JOptionPane.showMessageDialog(vista, "El descuento no puede ser negativo",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    vista.inputDescuento.requestFocus();
                    return;
                }
                ventaActualizada.setDescuentoVenta(descuento);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El descuento debe ser un número válido",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputDescuento.requestFocus();
                return;
            }

            // Validar y convertir total
            String totalStr = vista.inputTotal.getText().trim();
            try {
                double total = Double.parseDouble(totalStr);
                if (total < 0) {
                    JOptionPane.showMessageDialog(vista, "El total no puede ser negativo",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    vista.inputTotal.requestFocus();
                    return;
                }
                ventaActualizada.setTotalVenta(total);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El total debe ser un número válido",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputTotal.requestFocus();
                return;
            }

            // Validar y convertir montos de pago
            String montoEfectivoStr = vista.inputMontoEfectivo.getText().trim();
            String montoTarjetaStr = vista.inputMontoTarjeta.getText().trim();
            String cambioStr = vista.inputCambio.getText().trim();

            try {
                double montoEfectivo = montoEfectivoStr.isEmpty() ? 0.0 : Double.parseDouble(montoEfectivoStr);
                double montoTarjeta = montoTarjetaStr.isEmpty() ? 0.0 : Double.parseDouble(montoTarjetaStr);
                double cambio = cambioStr.isEmpty() ? 0.0 : Double.parseDouble(cambioStr);

                ventaActualizada.setMontoEfectivo(montoEfectivo);
                ventaActualizada.setMontoTarjeta(montoTarjeta);
                ventaActualizada.setCambioVenta(cambio);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Los montos de pago deben ser números válidos",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener tipo de pago del ComboBox
            String tipoPagoSeleccionado = (String) vista.comboBoxTipoPago.getSelectedItem();
            if (tipoPagoSeleccionado != null) {
                ventaActualizada.setTipoPagoVenta(TipoPagoVenta.valueOf(tipoPagoSeleccionado));
            }

            // Obtener cliente del ComboBox
            String clienteSeleccionado = (String) vista.comboBoxCliente.getSelectedItem();
            if (clienteSeleccionado != null && !clienteSeleccionado.equals("Seleccionar...")) {
                // Aquí deberías obtener el ID real del cliente
                ventaActualizada.setIdClienteVenta(clienteSeleccionado);
            }

            // Establecer vendedor (podría ser del usuario actual)
            ventaActualizada.setIdUsuarioVendedor("ADMIN"); // Placeholder

            // Actualizar en base de datos usando el modelo
            if (modelo.update(ventaActualizada)) {
                JOptionPane.showMessageDialog(vista, "Venta actualizada correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo actualizar la venta",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al actualizar venta: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al actualizar venta. Por favor, intente nuevamente.",
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
     * Calcula los totales de la venta
     */
    private void calcularTotal(java.awt.event.ActionEvent e) {
        try {
            String subtotalStr = vista.inputSubtotal.getText().trim();
            String impuestoStr = vista.inputImpuesto.getText().trim();
            String descuentoStr = vista.inputDescuento.getText().trim();

            if (subtotalStr.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Ingrese el subtotal primero",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double subtotal = Double.parseDouble(subtotalStr);
            double impuesto = impuestoStr.isEmpty() ? 0.0 : Double.parseDouble(impuestoStr);
            double descuento = descuentoStr.isEmpty() ? 0.0 : Double.parseDouble(descuentoStr);

            double total = subtotal + impuesto - descuento;

            vista.inputTotal.setText(String.format("%.2f", total));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Los valores deben ser números válidos",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Valida los campos del formulario antes de guardar
     */
    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        // Validar número de factura
        String numeroFactura = vista.inputNumeroFactura.getText().trim();
        if (numeroFactura.isEmpty()) {
            errores.append("- El número de factura es obligatorio\n");
        }

        // Validar subtotal
        String subtotal = vista.inputSubtotal.getText().trim();
        if (subtotal.isEmpty()) {
            errores.append("- El subtotal es obligatorio\n");
        }

        // Validar total
        String total = vista.inputTotal.getText().trim();
        if (total.isEmpty()) {
            errores.append("- El total es obligatorio\n");
        }

        // Validar ComboBox de tipo de pago
        String tipoPagoSeleccionado = (String) vista.comboBoxTipoPago.getSelectedItem();
        if (tipoPagoSeleccionado == null) {
            errores.append("- Debe seleccionar un tipo de pago\n");
        }

        // Validar ComboBox de cliente
        String clienteSeleccionado = (String) vista.comboBoxCliente.getSelectedItem();
        if (clienteSeleccionado == null || clienteSeleccionado.equals("Seleccionar...")) {
            errores.append("- Debe seleccionar un cliente\n");
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
