package Controlador.ventas;

import Vista.ventas.FormularioAgregarVenta;
import Modelo.ventas.VentaModel;
import Modelo.ventas.DetalleVentaModel;
import Modelo.inventario.InventarioModel;
import Modelo.clientes.ClienteModel;
import Modelo.Conexion;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
    private ArrayList<ItemVenta> itemsVenta;

    public FormularioAgregarVentaController(FormularioAgregarVenta vista) {
        this.vista = vista;
        this.ventaModel = new VentaModel();
        this.detalleVentaModel = new DetalleVentaModel();
        this.inventarioModel = new InventarioModel();
        this.itemsVenta = new ArrayList<>();
        inicializarEventos();
        cargarCombos();
        inicializarTablaDetalles();
        bloquearCamposCalculados();
        recalcularTotales();
    }

    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::guardar);

        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);

        if (vista.botonAgregarDecoracion != null) {
            vista.botonAgregarDecoracion.addActionListener(this::agregarDecoracion);
        }
        if (vista.botonQuitarItem != null) {
            vista.botonQuitarItem.addActionListener(e -> quitarItemSeleccionado());
        }
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
                    vista.comboBoxTipoPago.addItem(tipo.getDisplayName());
                }
            }

            // Cargar decoraciones (para el detalle)
            if (vista.comboBoxDecoracion != null) {
                vista.comboBoxDecoracion.removeAllItems();
                vista.comboBoxDecoracion.addItem("Seleccionar...");
                for (DecoracionVentaInfo d : obtenerDecoracionesDisponibles()) {
                    vista.comboBoxDecoracion.addItem(d.nombreDecoracion);
                }
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar combos: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar los combos. Por favor, intente nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bloquearCamposCalculados() {
        if (vista.inputSubtotal != null) {
            vista.inputSubtotal.setEditable(false);
        }
        if (vista.inputImpuesto != null) {
            vista.inputImpuesto.setEditable(false);
        }
        if (vista.inputDescuento != null) {
            vista.inputDescuento.setEditable(false);
        }
        if (vista.inputTotal != null) {
            vista.inputTotal.setEditable(false);
        }
        if (vista.inputCambio != null) {
            vista.inputCambio.setEditable(false);
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

            TotalesVenta totales = calcularTotalesDesdeItems();
            nuevaVenta.setSubtotalVenta(totales.subtotal);
            nuevaVenta.setDescuentoVenta(totales.descuento);
            nuevaVenta.setImpuestoVenta(totales.impuesto);
            nuevaVenta.setTotalVenta(totales.total);

            // Validar y convertir montos de pago
            String montoEfectivoStr = vista.inputMontoEfectivo.getText().trim();
            String montoTarjetaStr = vista.inputMontoTarjeta.getText().trim();

            try {
                double montoEfectivo = montoEfectivoStr.isEmpty() ? 0.0 : Double.parseDouble(montoEfectivoStr);
                double montoTarjeta = montoTarjetaStr.isEmpty() ? 0.0 : Double.parseDouble(montoTarjetaStr);
                double pagado = montoEfectivo + montoTarjeta;
                if (pagado < totales.total) {
                    JOptionPane.showMessageDialog(vista, "El monto pagado no puede ser menor al total a pagar.",
                            "Pago insuficiente", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double cambio = montoEfectivo > 0 ? (pagado - totales.total) : 0.0;

                nuevaVenta.setMontoEfectivo(montoEfectivo);
                nuevaVenta.setMontoTarjeta(montoTarjeta);
                nuevaVenta.setCambioVenta(cambio);
                vista.inputCambio.setText(String.format("%.2f", cambio));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Los montos de pago deben ser números válidos",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener tipo de pago del ComboBox
            String tipoPagoSeleccionado = (String) vista.comboBoxTipoPago.getSelectedItem();
            if (tipoPagoSeleccionado != null) {
                nuevaVenta.setTipoPagoVenta(TipoPagoVenta.fromDb(tipoPagoSeleccionado));
            }

            // Obtener cliente del ComboBox
            String clienteSeleccionado = (String) vista.comboBoxCliente.getSelectedItem();
            if (clienteSeleccionado != null && !clienteSeleccionado.equals("Seleccionar...")) {
                String idCliente = obtenerIdClientePorNombre(clienteSeleccionado);
                if (idCliente == null) {
                    JOptionPane.showMessageDialog(vista, "No se pudo obtener el ID del cliente seleccionado.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                nuevaVenta.setIdClienteVenta(idCliente);
            }

            // Vendedor: si no hay sesión enlazada, puede quedar null (DB permite null)
            nuevaVenta.setIdUsuarioVendedor(null);

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

        if (itemsVenta.isEmpty()) {
            errores.append("- Debe agregar al menos una decoración a la venta\n");
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

        if (vista.comboBoxDecoracion != null) {
            vista.comboBoxDecoracion.setSelectedIndex(0);
        }
        if (vista.inputCantidadDecoracion != null) {
            vista.inputCantidadDecoracion.setText("");
        }

        itemsVenta.clear();
        DefaultTableModel m = (DefaultTableModel) vista.tablaDetalles.getModel();
        m.setRowCount(0);
    }

    /**
     * Inicializa la tabla de detalles de venta
     */
    private void inicializarTablaDetalles() {
        if (vista.tablaDetalles == null) {
            return;
        }
        DefaultTableModel model = (DefaultTableModel) vista.tablaDetalles.getModel();
        model.setRowCount(0);
    }

    /**
     * Agrega una decoración al detalle de la venta con validación de stock
     */
    private void agregarDecoracion(java.awt.event.ActionEvent e) {
        String nombreDecoracion = (String) vista.comboBoxDecoracion.getSelectedItem();
        if (nombreDecoracion == null || "Seleccionar...".equals(nombreDecoracion)) {
            JOptionPane.showMessageDialog(vista, "Seleccione una decoración.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(vista.inputCantidadDecoracion.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "La cantidad debe ser un número válido.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(vista, "La cantidad debe ser mayor a cero.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        DecoracionVentaInfo info = obtenerDecoracionInfoPorNombre(nombreDecoracion);
        if (info == null) {
            JOptionPane.showMessageDialog(vista, "No se pudo obtener la información de la decoración.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (info.precioVenta <= 0) {
            JOptionPane.showMessageDialog(vista,
                    "La decoración no tiene precio de venta (requiere al menos una compra registrada).",
                    "Precio no disponible", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int yaEnCarrito = cantidadEnCarrito(info.idDecoracion);
        if (info.stock < (yaEnCarrito + cantidad)) {
            JOptionPane.showMessageDialog(vista,
                    "Stock insuficiente.\nDisponible: " + info.stock + "\nEn venta: " + yaEnCarrito
                            + "\nIntentando agregar: " + cantidad,
                    "Stock", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ItemVenta item = new ItemVenta();
        item.idDecoracion = info.idDecoracion;
        item.nombreDecoracion = info.nombreDecoracion;
        item.esColeccion = info.idColeccion != null;
        item.cantidad = cantidad;
        item.precioUnitario = info.precioVenta;
        item.descuentoPct = 0.0;
        itemsVenta.add(item);

        // Advertencia stock mínimo (sin bloquear la venta)
        if (info.stockMinimo > 0 && (info.stock - (yaEnCarrito + cantidad)) <= info.stockMinimo) {
            JOptionPane.showMessageDialog(vista,
                    "Advertencia: esta venta dejará la decoración en stock mínimo o bajo.\n"
                            + "Debe reabastecer pronto.",
                    "Stock mínimo", JOptionPane.WARNING_MESSAGE);
        }

        aplicarDescuentoColeccionSiAplica();
        refrescarTablaDetalles();
        recalcularTotales();

        vista.inputCantidadDecoracion.setText("");
    }

    /**
     * Actualiza la tabla de detalles de venta
     */
    private void actualizarTablaDetalles() {
        refrescarTablaDetalles();
    }

    /**
     * Calcula los totales de la venta incluyendo detalles
     */
    private void calcularTotales() {
        recalcularTotales();
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
            for (ItemVenta item : itemsVenta) {
                DetalleVentaType detalle = new DetalleVentaType();
                detalle.setIdDetalleVenta(UUID.randomUUID().toString());
                detalle.setIdVentaDetalle(venta.getIdVenta());
                detalle.setIdDecoracionDetalle(item.idDecoracion);
                detalle.setCantidadDetalleVenta(item.cantidad);
                detalle.setPrecioUnitarioVenta(item.precioUnitario);
                detalle.setDescuentoDetalle(item.descuentoPct);
                detalle.setSubtotalDetalleVenta(item.precioUnitario * item.cantidad);

                if (!detalleVentaModel.create(detalle)) {
                    // Si falla, eliminar la venta principal
                    ventaModel.delete(venta.getIdVenta());
                    return false;
                }

                // 3. Actualizar inventario (reducir stock)
                if (!inventarioModel.ajustarStock(item.idDecoracion,
                        -item.cantidad, "SALIDA")) {
                    logger.log(Level.WARNING, "No se pudo actualizar stock para: " + item.idDecoracion);
                }
            }

            return true;

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al procesar venta completa: " + ex.getMessage(), ex);
            return false;
        }
    }

    private void quitarItemSeleccionado() {
        int fila = vista.tablaDetalles.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione un item en la tabla.", "Quitar",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (fila >= itemsVenta.size()) {
            return;
        }
        itemsVenta.remove(fila);
        aplicarDescuentoColeccionSiAplica();
        refrescarTablaDetalles();
        recalcularTotales();
    }

    private void refrescarTablaDetalles() {
        DefaultTableModel m = (DefaultTableModel) vista.tablaDetalles.getModel();
        m.setRowCount(0);
        int i = 1;
        for (ItemVenta it : itemsVenta) {
            double subtotal = it.precioUnitario * it.cantidad;
            m.addRow(new Object[] { i++, it.nombreDecoracion, it.cantidad, it.precioUnitario, it.descuentoPct, subtotal });
        }
    }

    private void aplicarDescuentoColeccionSiAplica() {
        int totalColeccion = 0;
        for (ItemVenta it : itemsVenta) {
            if (it.esColeccion) {
                totalColeccion += it.cantidad;
            }
        }
        double pct = totalColeccion >= 2 ? 10.0 : 0.0;
        for (ItemVenta it : itemsVenta) {
            it.descuentoPct = (it.esColeccion ? pct : 0.0);
        }
    }

    private int cantidadEnCarrito(String idDecoracion) {
        int sum = 0;
        for (ItemVenta it : itemsVenta) {
            if (it.idDecoracion != null && it.idDecoracion.equals(idDecoracion)) {
                sum += it.cantidad;
            }
        }
        return sum;
    }

    private void recalcularTotales() {
        TotalesVenta t = calcularTotalesDesdeItems();
        vista.inputSubtotal.setText(String.format("%.2f", t.subtotal));
        vista.inputDescuento.setText(String.format("%.2f", t.descuento));
        vista.inputImpuesto.setText(String.format("%.2f", t.impuesto));
        vista.inputTotal.setText(String.format("%.2f", t.total));
    }

    private TotalesVenta calcularTotalesDesdeItems() {
        double subtotal = 0.0;
        double descuento = 0.0;
        for (ItemVenta it : itemsVenta) {
            double line = it.precioUnitario * it.cantidad;
            subtotal += line;
            descuento += line * (it.descuentoPct / 100.0);
        }
        double base = subtotal - descuento;
        double impuesto = base * 0.18;
        double total = base + impuesto;
        TotalesVenta t = new TotalesVenta();
        t.subtotal = subtotal;
        t.descuento = descuento;
        t.impuesto = impuesto;
        t.total = total;
        return t;
    }

    private String obtenerIdClientePorNombre(String nombreCliente) {
        if (nombreCliente == null || nombreCliente.trim().isEmpty() || "Seleccionar...".equals(nombreCliente)) {
            return null;
        }
        try {
            ClienteModel clienteModel = new ClienteModel();
            ArrayList<ClienteType> clientes = clienteModel.getAll();
            for (ClienteType c : clientes) {
                if (c != null && c.getNombreCliente() != null
                        && c.getNombreCliente().trim().equalsIgnoreCase(nombreCliente.trim())) {
                    return c.getIdCliente();
                }
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, "No se pudo mapear cliente: " + ex.getMessage(), ex);
        }
        return null;
    }

    private ArrayList<DecoracionVentaInfo> obtenerDecoracionesDisponibles() {
        ArrayList<DecoracionVentaInfo> out = new ArrayList<>();
        try (Connection c = new Conexion().getConxion();
                PreparedStatement ps = c.prepareStatement(
                        "SELECT id_decoracion, nombre_decoracion, stock_decoracion, stock_minimo_decoracion, precio_venta_decoracion, id_coleccion_decoracion FROM vista_detallada_inventario ORDER BY nombre_decoracion");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DecoracionVentaInfo d = new DecoracionVentaInfo();
                d.idDecoracion = rs.getString("id_decoracion");
                d.nombreDecoracion = rs.getString("nombre_decoracion");
                d.stock = rs.getInt("stock_decoracion");
                d.stockMinimo = rs.getInt("stock_minimo_decoracion");
                d.precioVenta = rs.getDouble("precio_venta_decoracion");
                d.idColeccion = rs.getString("id_coleccion_decoracion");
                out.add(d);
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, "No se pudieron cargar decoraciones disponibles: " + ex.getMessage(), ex);
        }
        return out;
    }

    private DecoracionVentaInfo obtenerDecoracionInfoPorNombre(String nombreDecoracion) {
        if (nombreDecoracion == null || nombreDecoracion.trim().isEmpty() || "Seleccionar...".equals(nombreDecoracion)) {
            return null;
        }
        try (Connection c = new Conexion().getConxion();
                PreparedStatement ps = c.prepareStatement(
                        "SELECT id_decoracion, nombre_decoracion, stock_decoracion, stock_minimo_decoracion, precio_venta_decoracion, id_coleccion_decoracion FROM vista_detallada_inventario WHERE nombre_decoracion = ?")) {
            ps.setString(1, nombreDecoracion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DecoracionVentaInfo d = new DecoracionVentaInfo();
                    d.idDecoracion = rs.getString("id_decoracion");
                    d.nombreDecoracion = rs.getString("nombre_decoracion");
                    d.stock = rs.getInt("stock_decoracion");
                    d.stockMinimo = rs.getInt("stock_minimo_decoracion");
                    d.precioVenta = rs.getDouble("precio_venta_decoracion");
                    d.idColeccion = rs.getString("id_coleccion_decoracion");
                    return d;
                }
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, "No se pudo leer info decoración: " + ex.getMessage(), ex);
        }
        return null;
    }

    private static class ItemVenta {
        String idDecoracion;
        String nombreDecoracion;
        int cantidad;
        double precioUnitario;
        boolean esColeccion;
        double descuentoPct;
    }

    private static class DecoracionVentaInfo {
        String idDecoracion;
        String nombreDecoracion;
        int stock;
        int stockMinimo;
        double precioVenta;
        String idColeccion;
    }

    private static class TotalesVenta {
        double subtotal;
        double descuento;
        double impuesto;
        double total;
    }
}
