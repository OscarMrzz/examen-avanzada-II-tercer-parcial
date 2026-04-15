package Controlador.compras;

import Vista.compras.FormularioAgregarCompra;
import Modelo.compras.FacturaCompraModel;
import Modelo.compras.DetalleCompraModel;
import Modelo.inventario.InventarioModel;
import Type.compras.FacturaCompraType;
import Type.compras.DetalleCompraType;
import Type.decoraciones.DecoracionType;
import Type.generales.TipoPago;
import Type.generales.EstadoFactura;
import java.util.UUID;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Controlador para el formulario de agregar compra
 * 
 * @author ossca
 */
public class FormularioAgregarCompraController {

    private static final Logger logger = Logger.getLogger(FormularioAgregarCompraController.class.getName());
    private FormularioAgregarCompra vista;
    private FacturaCompraModel facturaCompraModel;
    private DetalleCompraModel detalleCompraModel;
    private InventarioModel inventarioModel;
    private ArrayList<DetalleCompraType> detallesCompra;

    public FormularioAgregarCompraController(FormularioAgregarCompra vista) {
        this.vista = vista;
        this.facturaCompraModel = new FacturaCompraModel();
        this.detalleCompraModel = new DetalleCompraModel();
        this.inventarioModel = new InventarioModel();
        this.detallesCompra = new ArrayList<>();
        inicializarEventos();
        cargarCombos();
        inicializarTablaDetalles();
    }

    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::guardar);

        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);

        // Calcular precio venta automáticamente cuando cambia el costo
        if (vista.inputPrecioCosto != null && vista.inputPrecioVenta != null) {
            vista.inputPrecioVenta.setEditable(false);
            vista.inputPrecioCosto.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyReleased(java.awt.event.KeyEvent e) {
                    recalcularPrecioVenta();
                }
            });
        }
    }

    private void recalcularPrecioVenta() {
        try {
            if (vista.inputPrecioCosto == null || vista.inputPrecioVenta == null) {
                return;
            }
            String costoStr = vista.inputPrecioCosto.getText().trim();
            if (costoStr.isEmpty()) {
                vista.inputPrecioVenta.setText("");
                return;
            }
            double costo = Double.parseDouble(costoStr);
            if (costo <= 0) {
                vista.inputPrecioVenta.setText("");
                return;
            }
            double precioVenta = costo * 1.48;
            vista.inputPrecioVenta.setText(String.format("%.2f", precioVenta));
        } catch (Exception ex) {
            // Si el usuario está escribiendo y aún no es número válido, no bloquear
        }
    }

    /**
     * Carga los ComboBox con datos de proveedores y decoraciones
     */
    private void cargarCombos() {
        try {
            // Cargar proveedores
            if (vista.comboBoxProveedor != null) {
                vista.comboBoxProveedor.removeAllItems();
                vista.comboBoxProveedor.addItem("Seleccione un proveedor");

                // Cargar proveedores desde el modelo
                try {
                    Modelo.proveedores.ProveedorModel proveedorModel = new Modelo.proveedores.ProveedorModel();
                    java.util.ArrayList<Type.proveedores.ProveedorType> proveedores = proveedorModel.getAll();

                    for (Type.proveedores.ProveedorType proveedor : proveedores) {
                        if (proveedor.isEstadoProveedor()) {
                            vista.comboBoxProveedor.addItem(proveedor.getNombreProveedor());
                        }
                    }
                } catch (Exception ex) {
                    logger.log(Level.WARNING, "Error al cargar proveedores: " + ex.getMessage());
                }
            }

            // Cargar decoraciones
            if (vista.comboBoxDecoracion != null) {
                vista.comboBoxDecoracion.removeAllItems();
                vista.comboBoxDecoracion.addItem("Seleccione una decoración");

                // Cargar decoraciones desde el modelo
                try {
                    Modelo.decoraciones.DecoracionModel decoracionModel = new Modelo.decoraciones.DecoracionModel();
                    java.util.ArrayList<Type.decoraciones.DecoracionType> decoraciones = decoracionModel.getAll();

                    for (Type.decoraciones.DecoracionType decoracion : decoraciones) {
                        if (decoracion.isEstadoDecoracion()) {
                            vista.comboBoxDecoracion.addItem(decoracion.getNombreDecoracion());
                        }
                    }
                } catch (Exception ex) {
                    logger.log(Level.WARNING, "Error al cargar decoraciones: " + ex.getMessage());
                }
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar combos: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar los combos. Por favor, intente nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Guarda una nueva factura de compra en la base de datos
     */
    private void guardar(java.awt.event.ActionEvent e) {
        if (!validarCampos()) {
            return;
        }

        try {
            // Crear objeto FacturaCompraType con los datos del formulario
            FacturaCompraType nuevaFactura = new FacturaCompraType();
            nuevaFactura.setIdFacturaCompra(UUID.randomUUID().toString());

            // Obtener proveedor seleccionado
            String proveedorSeleccionado = (String) vista.comboBoxProveedor.getSelectedItem();
            String idProveedor = obtenerIdProveedorPorNombre(proveedorSeleccionado);
            if (idProveedor == null) {
                JOptionPane.showMessageDialog(vista, "No se pudo obtener el ID del proveedor seleccionado.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            nuevaFactura.setIdProveedorFacturaCompra(idProveedor);

            // Número de factura (autogenerado si la vista no lo pide)
            String numeroFactura = "FC-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            nuevaFactura.setNumeroFactura(numeroFactura);

            // Validar y convertir fecha de compra
            String fechaCompraStr = vista.inputFechaCompra.getText().trim();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaCompra = sdf.parse(fechaCompraStr);
                nuevaFactura.setFechaFacturaCompra(new java.sql.Date(fechaCompra.getTime()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "La fecha de compra debe tener formato yyyy-MM-dd",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputFechaCompra.requestFocus();
                return;
            }

            // Validar y convertir cantidad
            String cantidadStr = vista.inputCantidad.getText().trim();
            int cantidad;
            try {
                cantidad = Integer.parseInt(cantidadStr);
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(vista, "La cantidad debe ser mayor que cero",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    vista.inputCantidad.requestFocus();
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "La cantidad debe ser un número válido",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputCantidad.requestFocus();
                return;
            }

            // Validar y convertir precio costo
            String precioCostoStr = vista.inputPrecioCosto.getText().trim();
            double precioCosto;
            try {
                precioCosto = Double.parseDouble(precioCostoStr);
                if (precioCosto <= 0) {
                    JOptionPane.showMessageDialog(vista, "El precio costo debe ser mayor que cero",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    vista.inputPrecioCosto.requestFocus();
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El precio costo debe ser un número válido",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputPrecioCosto.requestFocus();
                return;
            }

            // Validar y convertir precio venta
            double precioVenta = precioCosto * 1.48; // margen obligatorio 48%
            if (vista.inputPrecioVenta != null) {
                vista.inputPrecioVenta.setText(String.format("%.2f", precioVenta));
            }

            // Calcular total
            double total = precioCosto * cantidad;
            nuevaFactura.setTotalFacturaCompra(total);

            // Tipo pago (Contado / Crédito) según estado
            // Si queda Pendiente -> Crédito; si Completada -> Contado
            String estadoSeleccionado = (String) vista.comboBoxEstado.getSelectedItem();
            TipoPago tipoPago = "Completada".equals(estadoSeleccionado) ? TipoPago.CONTADO : TipoPago.CREDITO;
            nuevaFactura.setTipoPagoFacturaCompra(tipoPago);

            // Obtener estado seleccionado
            if (estadoSeleccionado.equals("Completada")) {
                nuevaFactura.setEstadoFacturaCompra(Type.generales.EstadoFactura.PAGADA);
            } else {
                nuevaFactura.setEstadoFacturaCompra(Type.generales.EstadoFactura.PENDIENTE);
            }

            // Obtener observaciones
            String observaciones = vista.inputObservaciones.getText().trim();
            nuevaFactura.setCondicionFactura(observaciones.isEmpty() ? null : observaciones);

            // Construir 1 detalle de compra con los campos actuales del formulario
            String decoracionSeleccionada = (String) vista.comboBoxDecoracion.getSelectedItem();
            String idDecoracion = obtenerIdDecoracionPorNombre(decoracionSeleccionada);
            if (idDecoracion == null) {
                JOptionPane.showMessageDialog(vista, "No se pudo obtener el ID de la decoración seleccionada.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DetalleCompraType detalle = new DetalleCompraType();
            detalle.setIdDetalleCompra(UUID.randomUUID().toString());
            detalle.setIdDecoracionDetalle(idDecoracion);
            detalle.setCantidadDetalleCompra(cantidad);
            detalle.setPrecioCostoCompra(precioCosto);
            detalle.setPrecioVentaCompra(precioVenta);
            detalle.setSubtotalDetalleCompra(precioCosto * cantidad);

            detallesCompra.clear();
            detallesCompra.add(detalle);

            // Procesar compra completa con actualización de inventario
            if (procesarCompraCompleta(nuevaFactura)) {
                JOptionPane.showMessageDialog(vista, "Factura de compra guardada correctamente\n" +
                        "Stock actualizado automáticamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar la factura de compra",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al guardar factura de compra: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al guardar factura de compra. Por favor, intente nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerIdProveedorPorNombre(String nombreProveedor) {
        if (nombreProveedor == null || nombreProveedor.trim().isEmpty()
                || "Seleccione un proveedor".equals(nombreProveedor)) {
            return null;
        }
        try {
            Modelo.proveedores.ProveedorModel proveedorModel = new Modelo.proveedores.ProveedorModel();
            java.util.ArrayList<Type.proveedores.ProveedorType> proveedores = proveedorModel.getAll();
            for (Type.proveedores.ProveedorType p : proveedores) {
                if (p != null && p.getNombreProveedor() != null
                        && p.getNombreProveedor().trim().equalsIgnoreCase(nombreProveedor.trim())) {
                    return p.getIdProveedor();
                }
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, "No se pudo mapear proveedor: " + ex.getMessage());
        }
        return null;
    }

    private String obtenerIdDecoracionPorNombre(String nombreDecoracion) {
        if (nombreDecoracion == null || nombreDecoracion.trim().isEmpty()
                || "Seleccione una decoración".equals(nombreDecoracion)) {
            return null;
        }
        try {
            Modelo.decoraciones.DecoracionModel decoracionModel = new Modelo.decoraciones.DecoracionModel();
            java.util.ArrayList<Type.decoraciones.DecoracionType> decoraciones = decoracionModel.getAll();
            for (Type.decoraciones.DecoracionType d : decoraciones) {
                if (d != null && d.getNombreDecoracion() != null
                        && d.getNombreDecoracion().trim().equalsIgnoreCase(nombreDecoracion.trim())) {
                    return d.getIdDecoracion();
                }
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, "No se pudo mapear decoración: " + ex.getMessage());
        }
        return null;
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

        // Validar fecha de compra
        String fechaCompra = vista.inputFechaCompra.getText().trim();
        if (fechaCompra.isEmpty()) {
            errores.append("- La fecha de compra es obligatoria\n");
        }

        // Validar cantidad
        String cantidad = vista.inputCantidad.getText().trim();
        if (cantidad.isEmpty()) {
            errores.append("- La cantidad es obligatoria\n");
        } else {
            try {
                int cantidadInt = Integer.parseInt(cantidad);
                if (cantidadInt <= 0) {
                    errores.append("- La cantidad debe ser mayor a cero\n");
                }
            } catch (NumberFormatException e) {
                errores.append("- La cantidad debe ser un número válido\n");
            }
        }

        // Validar precio costo
        String precioCosto = vista.inputPrecioCosto.getText().trim();
        if (precioCosto.isEmpty()) {
            errores.append("- El precio costo es obligatorio\n");
        } else {
            try {
                double precioCostoDouble = Double.parseDouble(precioCosto);
                if (precioCostoDouble <= 0) {
                    errores.append("- El precio costo debe ser mayor a cero\n");
                }
            } catch (NumberFormatException e) {
                errores.append("- El precio costo debe ser un número válido\n");
            }
        }

        // Precio venta se calcula automáticamente (costo * 1.48).
        // No se valida como campo obligatorio de entrada.

        // Validar ComboBox de proveedor
        String proveedorSeleccionado = (String) vista.comboBoxProveedor.getSelectedItem();
        if (proveedorSeleccionado == null || proveedorSeleccionado.equals("Seleccione un proveedor")) {
            errores.append("- Debe seleccionar un proveedor\n");
        }

        // Validar ComboBox de decoración
        String decoracionSeleccionada = (String) vista.comboBoxDecoracion.getSelectedItem();
        if (decoracionSeleccionada == null || decoracionSeleccionada.equals("Seleccione una decoración")) {
            errores.append("- Debe seleccionar una decoración\n");
        }

        // Validar ComboBox de estado
        String estadoSeleccionado = (String) vista.comboBoxEstado.getSelectedItem();
        if (estadoSeleccionado == null) {
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
     * Limpia todos los campos del formulario
     */
    private void limpiarCampos() {
        vista.inputFechaCompra.setText("");
        vista.inputCantidad.setText("");
        vista.inputPrecioCosto.setText("");
        vista.inputPrecioVenta.setText("");
        vista.inputObservaciones.setText("");

        if (vista.comboBoxProveedor != null) {
            vista.comboBoxProveedor.setSelectedIndex(0);
        }

        if (vista.comboBoxDecoracion != null) {
            vista.comboBoxDecoracion.setSelectedIndex(0);
        }

        if (vista.comboBoxEstado != null) {
            vista.comboBoxEstado.setSelectedIndex(0);
        }
    }

    /**
     * Inicializa la tabla de detalles de compra
     */
    private void inicializarTablaDetalles() {
        try {
            // Configurar tabla de detalles si existe
            // Nota: La vista actual no tiene tabla de detalles, solo campos individuales
            // Este método queda preparado para futuras mejoras
            // (Sin log para no confundir como "error" en consola)
        } catch (Exception ex) {
            logger.log(Level.WARNING, "No se pudo inicializar tabla de detalles: " + ex.getMessage());
        }
    }

    /**
     * Agrega una decoración al detalle de la compra
     * Nota: Este método no se usa actualmente ya que la vista no tiene botón de
     * agregar decoración
     */
    private void agregarDecoracion(java.awt.event.ActionEvent e) {
        try {
            // Obtener decoración seleccionada del ComboBox
            String decoracionSeleccionada = (String) vista.comboBoxDecoracion.getSelectedItem();
            String cantidadStr = vista.inputCantidad.getText().trim();

            if (decoracionSeleccionada == null || decoracionSeleccionada.equals("Seleccione una decoración")
                    || cantidadStr.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Seleccione una decoración e ingrese cantidad",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cantidad;
            try {
                cantidad = Integer.parseInt(cantidadStr);
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(vista, "La cantidad debe ser mayor que cero",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "La cantidad debe ser un número válido",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener ID de la decoración seleccionada (simulado - debería obtenerse del
            // modelo)
            String idDecoracion = "DEC-001"; // Placeholder - implementar búsqueda real

            // Crear detalle de compra
            DetalleCompraType detalle = new DetalleCompraType();
            detalle.setIdDetalleCompra(UUID.randomUUID().toString());
            detalle.setIdDecoracionDetalle(idDecoracion);
            detalle.setCantidadDetalleCompra(cantidad);
            detalle.setPrecioCostoCompra(50.0); // Precio base - debería obtenerse de decoración
            detalle.setSubtotalDetalleCompra(cantidad * 50.0);

            // Agregar a la lista
            detallesCompra.add(detalle);

            // Actualizar tabla
            actualizarTablaDetalles();

            // Actualizar totales
            calcularTotales();

            JOptionPane.showMessageDialog(vista, "Decoración agregada al detalle de compra",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al agregar decoración: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al agregar decoración: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualiza la tabla de detalles de compra
     */
    private void actualizarTablaDetalles() {
        try {
            // Nota: La vista actual no tiene tabla de detalles
            // Este método queda preparado para futuras mejoras
            logger.log(Level.INFO, "Actualización de tabla de detalles - no implementada en vista actual");

        } catch (Exception ex) {
            logger.log(Level.WARNING, "Error al actualizar tabla de detalles: " + ex.getMessage());
        }
    }

    /**
     * Calcula los totales 
     */
    private void calcularTotales() {
        try {
            // Obtener valores de los campos
            String cantidadStr = vista.inputCantidad.getText().trim();
            String precioCostoStr = vista.inputPrecioCosto.getText().trim();

            if (!cantidadStr.isEmpty() && !precioCostoStr.isEmpty()) {
                int cantidad = Integer.parseInt(cantidadStr);
                double precioCosto = Double.parseDouble(precioCostoStr);
                double total = cantidad * precioCosto;

                // Mostrar total calculado (si hubiera un campo para mostrarlo)
                // Nota: La vista actual no tiene campo de total visible
                logger.log(Level.INFO, "Total calculado: " + String.format("%.2f", total));
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Los valores deben ser números válidos",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Calcula los totales (método para el botón)
     */
    private void calcularTotal(java.awt.event.ActionEvent e) {
        calcularTotales();
    }

    /**
     * Procesa la compra completa con actualización de inventario
     */
    private boolean procesarCompraCompleta(FacturaCompraType compra) {
        try {
            // 1. Guardar la compra principal
            if (!facturaCompraModel.create(compra)) {
                return false;
            }

            // 2. Guardar detalles de compra
            for (DetalleCompraType detalle : detallesCompra) {
                detalle.setIdFacturaCompraDetalle(compra.getIdFacturaCompra());
                if (!detalleCompraModel.create(detalle)) {
                    // Si falla, eliminar la compra principal
                    facturaCompraModel.delete(compra.getIdFacturaCompra());
                    return false;
                }

                // 3. Actualizar inventario (aumentar stock)
                if (!inventarioModel.ajustarStock(detalle.getIdDecoracionDetalle(),
                        detalle.getCantidadDetalleCompra(), "ENTRADA")) {
                    logger.log(Level.WARNING, "No se pudo actualizar stock para: " + detalle.getIdDecoracionDetalle());
                }
            }

            return true;

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al procesar compra completa: " + ex.getMessage(), ex);
            return false;
        }
    }
}
