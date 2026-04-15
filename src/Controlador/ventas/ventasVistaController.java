package Controlador.ventas;

import Vista.ventas.ventasVista;
import Vista.ventas.FormularioAgregarVenta;
import Vista.ventas.FormularioEditarVenta;
import Vista.ventas.reportesVentas;
import Modelo.reportes.JasperService;
import Modelo.clientes.ClienteModel;
import Type.clientes.ClienteType;
import Modelo.ventas.VentaModel;
import Type.ventas.VentaType;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador principal para la vista de ventas
 * 
 * @author ossca
 */
public class ventasVistaController {

    private static final Logger logger = Logger.getLogger(ventasVistaController.class.getName());
    private ventasVista vista;
    private VentaModel modelo;
    private FormularioAgregarVenta formularioAgregar;
    private FormularioEditarVenta formularioEditar;
    private reportesVentas reportes;
    private JasperService jasper;

    public ventasVistaController(ventasVista vista, FormularioAgregarVenta formularioAgregar,
            FormularioEditarVenta formularioEditar, reportesVentas reportes) {
        this.vista = vista;
        this.formularioAgregar = formularioAgregar;
        this.formularioEditar = formularioEditar;
        this.reportes = reportes;
        this.modelo = new VentaModel();
        this.jasper = new JasperService();
        inicializarEventos();
        cargarTabla();
    }

    private void inicializarEventos() {
        // Evento del botón buscar
        vista.botonBuscar.addActionListener(this::buscar);

        // Evento del botón agregar
        vista.botonAgregar.addActionListener(this::abrirFormularioAgregar);

        // Evento del botón informe
        vista.botonInforme.addActionListener(e -> abrirDialogoReportes());

        // Evento del mouse en la tabla para menú contextual
        vista.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarMenuContextual(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarMenuContextual(e);
                }
            }
        });

        // Evento focus en el campo de búsqueda
        vista.inputBusqueda.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().equals("Buscar venta...")) {
                    vista.inputBusqueda.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().isEmpty()) {
                    vista.inputBusqueda.setText("Buscar venta...");
                }
            }
        });

        // Botones en el diálogo de reportes
        reportes.botonGenerar.addActionListener(e -> generarReporte());
        reportes.botonCancelar.addActionListener(e -> reportes.setVisible(false));
    }

    private void abrirDialogoReportes() {
        try {
            cargarClientesEnComboReporte();
        } catch (Exception ignored) {
        }
        try {
            reportes.pack();
            reportes.setLocationRelativeTo(vista);
            reportes.setAlwaysOnTop(true);
            reportes.setVisible(true);
            reportes.toFront();
            reportes.requestFocus();
        } finally {
            reportes.setAlwaysOnTop(false);
        }
    }

    private void cargarClientesEnComboReporte() {
        if (reportes.comboBoxCliente == null) {
            return;
        }
        reportes.comboBoxCliente.removeAllItems();
        reportes.comboBoxCliente.addItem("Todos");

        ClienteModel clienteModel = new ClienteModel();
        ArrayList<ClienteType> clientes = clienteModel.getAll();
        for (ClienteType c : clientes) {
            if (c != null && c.isEstadoCliente()) {
                String nombre = c.getNombreCliente() != null ? c.getNombreCliente().trim() : "";
                if (!nombre.isEmpty()) {
                    reportes.comboBoxCliente.addItem(nombre);
                }
            }
        }
        reportes.comboBoxCliente.setSelectedIndex(0);
    }

    private void generarReporte() {
        String tipoReporte = (String) reportes.comboBoxTipoReporte.getSelectedItem();
        if (tipoReporte == null) {
            JOptionPane.showMessageDialog(reportes, "Seleccione un tipo de reporte.", "Reporte", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date fi = JasperService.parseSqlDateOrNull(reportes.inputFechaInicio.getText());
        Date ff = JasperService.parseSqlDateOrNull(reportes.inputFechaFin.getText());

        try {
            // Ocultar el diálogo de filtros para que no tape el visor del reporte
            reportes.setVisible(false);

            switch (tipoReporte) {
                case "Ventas por Fecha": {
                    if (fi == null || ff == null) {
                        JOptionPane.showMessageDialog(reportes,
                                "Ingrese Fecha Inicio y Fecha Fin en formato AAAA-MM-DD.",
                                "Fechas requeridas", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    Map<String, Object> p = JasperService.params(
                            "titulo", "Ventas por rango de fechas",
                            "fechaInicio", fi,
                            "fechaFin", ff);
                    jasper.verReporte("/reportes/ventas_por_fechas.jrxml", p);
                    break;
                }
                case "Ventas Generales": {
                    Map<String, Object> p = JasperService.params(
                            "titulo", "Ventas diarias (incluye gráfica)",
                            "fechaInicio", fi,
                            "fechaFin", ff);
                    jasper.verReporte("/reportes/ventas_diarias.jrxml", p);
                    break;
                }
                case "Productos Más Vendidos": {
                    Map<String, Object> p = JasperService.params(
                            "titulo", "Decoraciones más vendidas (incluye gráfica)");
                    jasper.verReporte("/reportes/decoraciones_mas_vendidas.jrxml", p);
                    break;
                }
                case "Ventas por Cliente": {
                    String nombreCliente = null;
                    if (reportes.comboBoxCliente != null && reportes.comboBoxCliente.getSelectedItem() != null) {
                        String v = String.valueOf(reportes.comboBoxCliente.getSelectedItem()).trim();
                        if (!v.isEmpty() && !v.equalsIgnoreCase("Seleccione") && !v.equalsIgnoreCase("Todos")) {
                            nombreCliente = v;
                        }
                    }
                    if (nombreCliente == null) {
                        String v = JOptionPane.showInputDialog(reportes,
                                "Ingrese el nombre del cliente (exacto) o deje vacío para todos:",
                                "Cliente", JOptionPane.QUESTION_MESSAGE);
                        if (v == null) {
                            return;
                        }
                        v = v.trim();
                        nombreCliente = v.isEmpty() ? null : v;
                    }
                    Map<String, Object> p = JasperService.params(
                            "titulo", "Ventas por cliente",
                            "nombreCliente", nombreCliente,
                            "fechaInicio", fi,
                            "fechaFin", ff);
                    jasper.verReporte("/reportes/ventas_por_cliente.jrxml", p);
                    break;
                }
                case "Ventas por Vendedor": {
                    String vendedor = JOptionPane.showInputDialog(reportes,
                            "Ingrese el vendedor (exacto) o deje vacío para todos:",
                            "Vendedor", JOptionPane.QUESTION_MESSAGE);
                    if (vendedor == null) {
                        return;
                    }
                    vendedor = vendedor.trim();
                    Map<String, Object> p = JasperService.params(
                            "titulo", "Ventas por vendedor",
                            "vendedor", vendedor.isEmpty() ? null : vendedor,
                            "fechaInicio", fi,
                            "fechaFin", ff);
                    jasper.verReporte("/reportes/ventas_por_vendedor.jrxml", p);
                    break;
                }
                default:
                    JOptionPane.showMessageDialog(reportes,
                            "Tipo de reporte no soportado: " + tipoReporte,
                            "Reporte", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(reportes,
                    "No se pudo generar el reporte.\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga todas las ventas en la tabla usando el Modelo
     */
    public void cargarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<VentaType> ventas = modelo.getAll();
            int index = 1;

            for (VentaType venta : ventas) {
                Object[] fila = {
                        index++, // Número de registro/índice
                        venta.getNumeroFacturaVenta(),
                        venta.getFechaVenta(),
                        venta.getIdClienteVenta() != null ? venta.getIdClienteVenta() : "N/A",
                        venta.getIdUsuarioVendedor() != null ? venta.getIdUsuarioVendedor() : "N/A",
                        venta.getSubtotalVenta(),
                        venta.getImpuestoVenta(),
                        venta.getDescuentoVenta(),
                        venta.getTotalVenta(),
                        venta.getTipoPagoVenta() != null ? venta.getTipoPagoVenta().toString() : "N/A",
                        venta.getMontoEfectivo(),
                        venta.getMontoTarjeta(),
                        venta.getCambioVenta(),
                        venta.getEstadoVenta() != null ? venta.getEstadoVenta().toString() : "N/A"
                };
                modeloTabla.addRow(fila);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar las ventas: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar las ventas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca ventas según el texto ingresado
     */
    private void buscar(ActionEvent e) {
        String textoBusqueda = vista.inputBusqueda.getText().trim();

        if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar venta...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<VentaType> ventas = modelo.getAll();
            int index = 1;

            for (VentaType venta : ventas) {
                // Búsqueda en múltiples campos (case-insensitive)
                String numero = venta.getNumeroFacturaVenta() != null ? venta.getNumeroFacturaVenta() : "";
                String cliente = venta.getIdClienteVenta() != null ? venta.getIdClienteVenta() : "";
                String vendedor = venta.getIdUsuarioVendedor() != null ? venta.getIdUsuarioVendedor() : "";
                String tipoPago = venta.getTipoPagoVenta() != null ? venta.getTipoPagoVenta().toString() : "";
                String estado = venta.getEstadoVenta() != null ? venta.getEstadoVenta().toString() : "";

                if (numero.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        cliente.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        vendedor.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        tipoPago.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        estado.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        String.valueOf(venta.getTotalVenta()).contains(textoBusqueda) ||
                        String.valueOf(venta.getSubtotalVenta()).contains(textoBusqueda)) {

                    Object[] fila = {
                            index++, // Mantener índice secuencial
                            numero,
                            venta.getFechaVenta(),
                            cliente.isEmpty() ? "N/A" : cliente,
                            vendedor.isEmpty() ? "N/A" : vendedor,
                            venta.getSubtotalVenta(),
                            venta.getImpuestoVenta(),
                            venta.getDescuentoVenta(),
                            venta.getTotalVenta(),
                            tipoPago.isEmpty() ? "N/A" : tipoPago,
                            venta.getMontoEfectivo(),
                            venta.getMontoTarjeta(),
                            venta.getCambioVenta(),
                            estado.isEmpty() ? "N/A" : estado
                    };
                    modeloTabla.addRow(fila);
                }
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al buscar ventas: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al buscar ventas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra el menú contextual en la tabla
     */
    private void mostrarMenuContextual(MouseEvent e) {
        int fila = vista.tabla.rowAtPoint(e.getPoint());

        if (fila >= 0) {
            vista.tabla.setRowSelectionInterval(fila, fila);

            javax.swing.JPopupMenu menu = new javax.swing.JPopupMenu();

            javax.swing.JMenuItem menuItemEditar = new javax.swing.JMenuItem("Editar");
            menuItemEditar.addActionListener(evt -> abrirFormularioEditar());

            javax.swing.JMenuItem menuItemEliminar = new javax.swing.JMenuItem("Eliminar");
            menuItemEliminar.addActionListener(evt -> eliminar());

            menu.add(menuItemEditar);
            menu.add(menuItemEliminar);

            menu.show(vista.tabla, e.getX(), e.getY());
        }
    }

    /**
     * Abre el formulario para agregar una nueva venta
     */
    private void abrirFormularioAgregar(ActionEvent e) {
        new FormularioAgregarVentaController(formularioAgregar);
        formularioAgregar.setVisible(true);

        // Recargar la tabla después de agregar
        cargarTabla();
    }

    /**
     * Abre el formulario para editar la venta seleccionada
     */
    private void abrirFormularioEditar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una venta para editar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real de la venta desde el modelo usando el índice de la fila
        try {
            ArrayList<VentaType> ventas = modelo.getAll();
            if (fila < ventas.size()) {
                String idVenta = ventas.get(fila).getIdVenta();

                new FormularioEditarVentaController(formularioEditar, idVenta);
                formularioEditar.setVisible(true);

                // Recargar la tabla después de editar
                cargarTabla();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al obtener la venta: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al obtener la venta: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina la venta seleccionada usando el Modelo
     */
    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una venta para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real y número de factura de la venta desde el modelo usando el
        // índice de la fila
        try {
            ArrayList<VentaType> ventas = modelo.getAll();
            if (fila < ventas.size()) {
                VentaType ventaSeleccionada = ventas.get(fila);
                String idVenta = ventaSeleccionada.getIdVenta();
                String numeroFactura = ventaSeleccionada.getNumeroFacturaVenta();

                int confirmacion = JOptionPane.showConfirmDialog(
                        vista,
                        "¿Está seguro de que desea eliminar la venta con factura \"" + numeroFactura + "\"?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (modelo.delete(idVenta)) {
                        JOptionPane.showMessageDialog(vista, "Venta eliminada correctamente",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarTabla();
                    } else {
                        JOptionPane.showMessageDialog(vista, "No se pudo eliminar la venta",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al eliminar venta: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al eliminar venta: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
