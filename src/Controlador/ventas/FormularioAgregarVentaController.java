package Controlador.ventas;

import Vista.ventas.FormularioAgregarVenta;
import Modelo.ventas.VentaModel;
import Modelo.ventas.DetalleVentaModel;
import Modelo.inventario.InventarioModel;
import Modelo.clientes.ClienteModel;
import Type.ventas.VentaType;
import Type.ventas.DetalleVentaType;
import Type.decoraciones.DecoracionType;
import Type.clientes.ClienteType;
import Type.generales.TipoPagoVenta;
import Type.generales.EstadoVenta;
import java.util.UUID;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Controlador para el formulario de agregar venta
 * 
 * @author ossca
 */
public class FormularioAgregarVentaController {

    private static final Logger logger = Logger.getLogger(FormularioAgregarVentaController.class.getName());
    private FormularioAgregarVenta vista;
    private VentaModel ventaModel;
    private DetalleVentaModel detalleVentaModel;
    private InventarioModel inventarioModel;
    private ArrayList<DetalleVentaType> detallesVenta;

    public FormularioAgregarVentaController(FormularioAgregarVenta vista) {
        this.vista = vista;
        this.ventaModel = new VentaModel();
        this.detalleVentaModel = new DetalleVentaModel();
        this.inventarioModel = new InventarioModel();
        this.detallesVenta = new ArrayList<>();
        inicializarEventos();
        cargarCombos();
        // inicializarTablaDetalles() - no disponible, requiere componente en vista
    }

    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::guardar);

        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);

        // Nota: Los componentes botonAgregarDecoracion, botonCalcularTotal, tablaDetalles
        // inputIdDecoracion e inputCantidad no existen en FormularioAgregarVenta
        // La funcionalidad de agregar decoraciones y calcular totales requiere agregarlos a la vista
    }

    /**
     * Carga los ComboBox con datos de clientes y tipos de pago
     */
    private void cargarCombos() {
        try {
            // Cargar clientes
            if (vista.comboBoxCliente != null) {
                vista.comboBoxCliente.removeAllItems();
                vista.comboBoxCliente.addItem("Seleccionar...");

                ClienteModel clienteModel = new ClienteModel();
                ArrayList<ClienteType> clientes = clienteModel.getAll();
                for (ClienteType c : clientes) {
                    if (c != null && c.isEstadoCliente()) {
                        String nombre = c.getNombreCliente() != null ? c.getNombreCliente().trim() : "";
                        if (!nombre.isEmpty()) {
                            vista.comboBoxCliente.addItem(nombre);
                        }
                    }
                }
            }

            // Cargar tipos de pago
            if (vista.comboBoxTipoPago != null) {
                vista.comboBoxTipoPago.removeAllItems();
                for (TipoPagoVenta tipo : TipoPagoVenta.values()) {
                    vista.comboBoxTipoPago.addItem(tipo.toString());
                }
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar combos: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar los combos. Por favor, intente nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Guarda una nueva venta en la base de datos
     */
    private void guardar(java.awt.event.ActionEvent e) {
        if (!validarCampos()) {
            return;
        }

        try {
            // Crear objeto VentaType con los datos del formulario
            VentaType nuevaVenta = new VentaType();
            nuevaVenta.setIdVenta(UUID.randomUUID().toString());
            nuevaVenta.setNumeroFacturaVenta(vista.inputNumeroFactura.getText().trim());
            nuevaVenta.setEstadoVenta(EstadoVenta.ACTIVA);

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
                nuevaVenta.setSubtotalVenta(subtotal);
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
                nuevaVenta.setImpuestoVenta(impuesto);
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
                nuevaVenta.setDescuentoVenta(descuento);
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
                nuevaVenta.setTotalVenta(total);
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

                nuevaVenta.setMontoEfectivo(montoEfectivo);
                nuevaVenta.setMontoTarjeta(montoTarjeta);
                nuevaVenta.setCambioVenta(cambio);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Los montos de pago deben ser números válidos",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener tipo de pago del ComboBox
            String tipoPagoSeleccionado = (String) vista.comboBoxTipoPago.getSelectedItem();
            if (tipoPagoSeleccionado != null) {
                nuevaVenta.setTipoPagoVenta(TipoPagoVenta.valueOf(tipoPagoSeleccionado));
            }

            // Obtener cliente del ComboBox
            String clienteSeleccionado = (String) vista.comboBoxCliente.getSelectedItem();
            if (clienteSeleccionado != null && !clienteSeleccionado.equals("Seleccionar...")) {
                // Aquí deberías obtener el ID real del cliente
                nuevaVenta.setIdClienteVenta(clienteSeleccionado);
            }

            // Establecer vendedor (podría ser del usuario actual)
            nuevaVenta.setIdUsuarioVendedor("ADMIN"); // Placeholder

            // Validar que haya detalles de venta
            if (detallesVenta.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Debe agregar al menos una decoración a la venta",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Procesar venta completa con actualización de inventario
            if (procesarVentaCompleta(nuevaVenta)) {
                JOptionPane.showMessageDialog(vista, "Venta guardada correctamente\n" +
                        "Stock actualizado automáticamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar la venta. Verifique el stock disponible.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al guardar venta: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al guardar venta. Por favor, intente nuevamente.",
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

    /**
     * Limpia todos los campos del formulario
     */
    private void limpiarCampos() {
        vista.inputNumeroFactura.setText("");
        vista.inputSubtotal.setText("");
        vista.inputImpuesto.setText("");
        vista.inputDescuento.setText("");
        vista.inputTotal.setText("");
        vista.inputMontoEfectivo.setText("");
        vista.inputMontoTarjeta.setText("");
        vista.inputCambio.setText("");

        if (vista.comboBoxTipoPago != null) {
            vista.comboBoxTipoPago.setSelectedIndex(0);
        }

        if (vista.comboBoxCliente != null) {
            vista.comboBoxCliente.setSelectedIndex(0);
        }
    }

    /**
     * Inicializa la tabla de detalles de venta
     * Nota: tablaDetalles no existe en FormularioAgregarVenta
     */
    private void inicializarTablaDetalles() {
        // La funcionalidad de tabla de detalles requiere agregar el componente a la vista
        logger.log(Level.INFO, "Inicializacion de tabla de detalles - componente no disponible en vista");
    }

    /**
     * Agrega una decoración al detalle de la venta con validación de stock
     * Nota: inputIdDecoracion e inputCantidad no existen en FormularioAgregarVenta
     */
    private void agregarDecoracion(java.awt.event.ActionEvent e) {
        // La funcionalidad de agregar decoraciones requiere componentes en la vista
        JOptionPane.showMessageDialog(vista, "Funcionalidad no disponible. Agregue los componentes necesarios a la vista.",
                "Informacion", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Actualiza la tabla de detalles de venta
     * Nota: tablaDetalles no existe en FormularioAgregarVenta
     */
    private void actualizarTablaDetalles() {
        // La funcionalidad de tabla de detalles requiere el componente en la vista
        logger.log(Level.INFO, "Actualizacion de tabla de detalles - componente no disponible");
    }

    /**
     * Calcula los totales de la venta incluyendo detalles
     */
    private void calcularTotales() {
        try {
            double subtotal = 0.0;
            for (DetalleVentaType detalle : detallesVenta) {
                subtotal += detalle.getSubtotalDetalleVenta();
            }

            String impuestoStr = vista.inputImpuesto.getText().trim();
            String descuentoStr = vista.inputDescuento.getText().trim();

            double impuesto = impuestoStr.isEmpty() ? 0.0 : Double.parseDouble(impuestoStr);
            double descuento = descuentoStr.isEmpty() ? 0.0 : Double.parseDouble(descuentoStr);

            double total = subtotal + impuesto - descuento;

            vista.inputSubtotal.setText(String.format("%.2f", subtotal));
            vista.inputTotal.setText(String.format("%.2f", total));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Los valores deben ser números válidos",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Procesa la venta completa con actualización de inventario
     */
    private boolean procesarVentaCompleta(VentaType venta) {
        try {
            // 1. Guardar la venta principal
            if (!ventaModel.create(venta)) {
                return false;
            }

            // 2. Guardar detalles de venta
            for (DetalleVentaType detalle : detallesVenta) {
                detalle.setIdVentaDetalle(venta.getIdVenta());
                if (!detalleVentaModel.create(detalle)) {
                    // Si falla, eliminar la venta principal
                    ventaModel.delete(venta.getIdVenta());
                    return false;
                }

                // 3. Actualizar inventario (reducir stock)
                if (!inventarioModel.ajustarStock(detalle.getIdDecoracionDetalle(),
                        -detalle.getCantidadDetalleVenta(), "SALIDA")) {
                    logger.log(Level.WARNING, "No se pudo actualizar stock para: " + detalle.getIdDecoracionDetalle());
                }
            }

            return true;

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al procesar venta completa: " + ex.getMessage(), ex);
            return false;
        }
    }
}
