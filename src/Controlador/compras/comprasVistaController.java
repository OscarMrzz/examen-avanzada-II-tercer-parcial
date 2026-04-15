package Controlador.compras;

import Vista.compras.comprasVista;
import Vista.compras.FormularioAgregarCompra;
import Vista.compras.FormularioEditarCompra;
import Vista.compras.reportesCompras;
import Modelo.reportes.JasperService;
import Modelo.compras.FacturaCompraModel;
import Type.compras.FacturaCompraType;
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
 * Controlador principal para la vista de compras
 * 
 * @author ossca
 */
public class comprasVistaController {

    private static final Logger logger = Logger.getLogger(comprasVistaController.class.getName());
    private comprasVista vista;
    private FacturaCompraModel modelo;
    private FormularioAgregarCompra formularioAgregar;
    private FormularioEditarCompra formularioEditar;
    private reportesCompras reportes;
    private JasperService jasper;

    public comprasVistaController(comprasVista vista, FormularioAgregarCompra formularioAgregar, FormularioEditarCompra formularioEditar,
            reportesCompras reportes) {
        this.vista = vista;
        this.formularioAgregar = formularioAgregar;
        this.formularioEditar = formularioEditar;
        this.reportes = reportes;
        this.modelo = new FacturaCompraModel();
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
        vista.botonInforme.addActionListener(e -> reportes.setVisible(true));

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
                if (vista.inputBusqueda.getText().equals("Buscar compra...")) {
                    vista.inputBusqueda.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().isEmpty()) {
                    vista.inputBusqueda.setText("Buscar compra...");
                }
            }
        });

        // Botones del diálogo de reportes
        reportes.botonGenerarReporte.addActionListener(e -> generarReporte());
        reportes.botonCancelar.addActionListener(e -> reportes.setVisible(false));
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
            switch (tipoReporte) {
                case "Compras por Fecha": {
                    if (fi == null || ff == null) {
                        JOptionPane.showMessageDialog(reportes,
                                "Ingrese Fecha Inicio y Fecha Fin en formato AAAA-MM-DD.",
                                "Fechas requeridas", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    Map<String, Object> p = JasperService.params(
                            "titulo", "Compras por rango de fechas",
                            "fechaInicio", fi,
                            "fechaFin", ff);
                    jasper.verReporte("/reportes/compras_por_fechas.jrxml", p);
                    break;
                }
                default:
                    JOptionPane.showMessageDialog(reportes,
                            "Este tipo de reporte aún no tiene plantilla Jasper específica.\nUse 'Compras por Fecha' por ahora.",
                            "Reporte", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(reportes,
                    "No se pudo generar el reporte.\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga todas las facturas de compra en la tabla usando el Modelo
     */
    public void cargarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<FacturaCompraType> facturas = modelo.getAll();
            int index = 1;

            for (FacturaCompraType factura : facturas) {
                Object[] fila = {
                        index++, // Número de registro/índice
                        factura.getNumeroFactura(),
                        factura.getIdProveedorFacturaCompra() != null ? factura.getIdProveedorFacturaCompra() : "N/A",
                        factura.getFechaFacturaCompra(),
                        factura.getFechaVencimientoFactura(),
                        factura.getTotalFacturaCompra(),
                        factura.getTipoPagoFacturaCompra() != null ? factura.getTipoPagoFacturaCompra().toString()
                                : "N/A",
                        factura.getEstadoFacturaCompra() != null ? factura.getEstadoFacturaCompra().toString() : "N/A",
                        factura.getCondicionFactura() != null ? factura.getCondicionFactura() : "N/A"
                };
                modeloTabla.addRow(fila);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar las facturas de compra: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar las facturas de compra: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca facturas de compra según el texto ingresado
     */
    private void buscar(ActionEvent e) {
        String textoBusqueda = vista.inputBusqueda.getText().trim();

        if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar compra...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<FacturaCompraType> facturas = modelo.getAll();
            int index = 1;

            for (FacturaCompraType factura : facturas) {
                // Búsqueda en múltiples campos (case-insensitive)
                String numero = factura.getNumeroFactura() != null ? factura.getNumeroFactura() : "";
                String proveedor = factura.getIdProveedorFacturaCompra() != null ? factura.getIdProveedorFacturaCompra()
                        : "";
                String condicion = factura.getCondicionFactura() != null ? factura.getCondicionFactura() : "";
                String tipoPago = factura.getTipoPagoFacturaCompra() != null
                        ? factura.getTipoPagoFacturaCompra().toString()
                        : "";
                String estado = factura.getEstadoFacturaCompra() != null ? factura.getEstadoFacturaCompra().toString()
                        : "";

                if (numero.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        proveedor.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        condicion.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        tipoPago.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        estado.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        String.valueOf(factura.getTotalFacturaCompra()).contains(textoBusqueda)) {

                    Object[] fila = {
                            index++, // Mantener índice secuencial
                            numero,
                            proveedor.isEmpty() ? "N/A" : proveedor,
                            factura.getFechaFacturaCompra(),
                            factura.getFechaVencimientoFactura(),
                            factura.getTotalFacturaCompra(),
                            tipoPago.isEmpty() ? "N/A" : tipoPago,
                            estado.isEmpty() ? "N/A" : estado,
                            condicion.isEmpty() ? "N/A" : condicion
                    };
                    modeloTabla.addRow(fila);
                }
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al buscar facturas de compra: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al buscar facturas de compra: " + ex.getMessage(),
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
     * Abre el formulario para agregar una nueva compra
     */
    private void abrirFormularioAgregar(ActionEvent e) {
        new FormularioAgregarCompraController(formularioAgregar);
        formularioAgregar.setVisible(true);

        cargarTabla();
    }

    /**
     * Abre el formulario para editar la compra seleccionada
     */
    private void abrirFormularioEditar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una factura para editar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real de la factura desde el modelo usando el índice de la fila
        try {
            ArrayList<FacturaCompraType> facturas = modelo.getAll();
            if (fila < facturas.size()) {
                String idFactura = facturas.get(fila).getIdFacturaCompra();

                new FormularioEditarCompraController(formularioEditar, idFactura);
                formularioEditar.setVisible(true);

                cargarTabla();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al obtener la factura: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al obtener la factura: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina la factura de compra seleccionada usando el Modelo
     */
    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una factura para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real y número de la factura desde el modelo usando el índice de
        // la fila
        try {
            ArrayList<FacturaCompraType> facturas = modelo.getAll();
            if (fila < facturas.size()) {
                FacturaCompraType facturaSeleccionada = facturas.get(fila);
                String idFactura = facturaSeleccionada.getIdFacturaCompra();
                String numeroFactura = facturaSeleccionada.getNumeroFactura();

                int confirmacion = JOptionPane.showConfirmDialog(
                        vista,
                        "¿Está seguro de que desea eliminar la factura \"" + numeroFactura + "\"?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (modelo.delete(idFactura)) {
                        JOptionPane.showMessageDialog(vista, "Factura eliminada correctamente",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarTabla();
                    } else {
                        JOptionPane.showMessageDialog(vista, "No se pudo eliminar la factura",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al eliminar factura: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al eliminar factura: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
