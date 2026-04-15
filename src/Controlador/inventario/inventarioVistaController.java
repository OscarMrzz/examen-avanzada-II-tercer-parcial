package Controlador.inventario;

import Vista.inventario.inventarioVista;
import Vista.inventario.FormularioAgregarInventario;
import Vista.inventario.reportesInventario;
import Modelo.reportes.JasperService;
import Modelo.inventario.InventarioModel;
import Type.decoraciones.DecoracionType;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador principal para la vista de inventario
 * 
 * @author ossca
 */
public class inventarioVistaController {

    private static final Logger logger = Logger.getLogger(inventarioVistaController.class.getName());
    private inventarioVista vista;
    private InventarioModel modelo;
    private FormularioAgregarInventario formularioAgregar;
    private reportesInventario reportes;
    private JasperService jasper;

    public inventarioVistaController(inventarioVista vista, FormularioAgregarInventario formularioAgregar,
            reportesInventario reportes) {
        this.vista = vista;
        this.formularioAgregar = formularioAgregar;
        this.reportes = reportes;
        this.modelo = new InventarioModel();
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
                if (vista.inputBusqueda.getText().equals("Buscar en inventario...")) {
                    vista.inputBusqueda.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().isEmpty()) {
                    vista.inputBusqueda.setText("Buscar en inventario...");
                }
            }
        });

        // Botones del diálogo de reportes
        reportes.botonGenerar.addActionListener(e -> generarReporte());
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
                case "Inventario General": {
                    Map<String, Object> p = JasperService.params("titulo", "Inventario general");
                    jasper.verReporte("/reportes/inventario_general.jrxml", p);
                    break;
                }
                default: {
                    if (fi != null && ff != null) {
                        Map<String, Object> p = JasperService.params(
                                "titulo", "Inventario (referencia por fechas: compras en rango)",
                                "fechaInicio", fi,
                                "fechaFin", ff);
                        jasper.verReporte("/reportes/compras_por_fechas.jrxml", p);
                    } else {
                        JOptionPane.showMessageDialog(reportes,
                                "Use 'Inventario General' o ingrese fechas AAAA-MM-DD para ver compras del rango.",
                                "Reporte", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(reportes,
                    "No se pudo generar el reporte.\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga todos los items del inventario en la tabla usando el Modelo
     */
    public void cargarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<DecoracionType> productos = modelo.getAll();
            int index = 1;

            for (DecoracionType producto : productos) {
                // Determinar estado del stock
                String estadoStock = producto.getStockDecoracion() <= producto.getStockMinimoDecoracion() ? "BAJO"
                        : "NORMAL";

                Object[] fila = {
                        index++, // Número de registro/índice
                        producto.getNombreDecoracion(),
                        producto.getStockDecoracion(),
                        producto.getStockMinimoDecoracion(),
                        producto.getStockMaximoDecoracion(),
                        producto.getIdProveedorDecoracion() != null ? producto.getIdProveedorDecoracion() : "N/A",
                        producto.getIdColeccionDecoracion() != null ? producto.getIdColeccionDecoracion() : "N/A",
                        producto.getDescripcionDecoracion() != null ? producto.getDescripcionDecoracion() : "N/A",
                        estadoStock,
                        producto.isEstadoDecoracion() ? "ACTIVO" : "INACTIVO"
                };
                modeloTabla.addRow(fila);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar el inventario: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar el inventario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca items en el inventario según el texto ingresado
     */
    private void buscar(ActionEvent e) {
        String textoBusqueda = vista.inputBusqueda.getText().trim();

        if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar en inventario...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<DecoracionType> productos = modelo.buscar(textoBusqueda);
            int index = 1;

            for (DecoracionType producto : productos) {
                // Determinar estado del stock
                String estadoStock = producto.getStockDecoracion() <= producto.getStockMinimoDecoracion() ? "BAJO"
                        : "NORMAL";

                Object[] fila = {
                        index++, // Mantener índice secuencial
                        producto.getNombreDecoracion(),
                        producto.getStockDecoracion(),
                        producto.getStockMinimoDecoracion(),
                        producto.getStockMaximoDecoracion(),
                        producto.getIdProveedorDecoracion() != null ? producto.getIdProveedorDecoracion() : "N/A",
                        producto.getIdColeccionDecoracion() != null ? producto.getIdColeccionDecoracion() : "N/A",
                        producto.getDescripcionDecoracion() != null ? producto.getDescripcionDecoracion() : "N/A",
                        estadoStock,
                        producto.isEstadoDecoracion() ? "ACTIVO" : "INACTIVO"
                };
                modeloTabla.addRow(fila);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al buscar en el inventario: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al buscar en el inventario: " + ex.getMessage(),
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

            javax.swing.JMenuItem menuItemAjustarStock = new javax.swing.JMenuItem("Ajustar Stock");
            menuItemAjustarStock.addActionListener(evt -> ajustarStock());

            javax.swing.JMenuItem menuItemVerStockBajo = new javax.swing.JMenuItem("Ver Stock Bajo");
            menuItemVerStockBajo.addActionListener(evt -> verStockBajo());

            menu.add(menuItemAjustarStock);
            menu.add(menuItemVerStockBajo);

            menu.show(vista.tabla, e.getX(), e.getY());
        }
    }

    /**
     * Abre el formulario para agregar un nuevo item al inventario
     */
    private void abrirFormularioAgregar(ActionEvent e) {
        new FormularioAgregarInventarioController(formularioAgregar);
        formularioAgregar.setVisible(true);

        cargarTabla();
    }

    /**
     * Ajusta el stock de un producto seleccionado
     */
    private void ajustarStock() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un producto para ajustar stock",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real del producto desde el modelo usando el índice de la fila
        try {
            ArrayList<DecoracionType> productos = modelo.getAll();
            if (fila < productos.size()) {
                DecoracionType productoSeleccionado = productos.get(fila);
                String idProducto = productoSeleccionado.getIdDecoracion();
                String nombreProducto = productoSeleccionado.getNombreDecoracion();
                int stockActual = productoSeleccionado.getStockDecoracion();

                // Pedir al usuario el tipo de ajuste
                String[] opciones = { "ENTRADA", "SALIDA" };
                String tipoAjuste = (String) JOptionPane.showInputDialog(
                        vista,
                        "Producto: " + nombreProducto + "\nStock actual: " + stockActual
                                + "\n\nSeleccione tipo de ajuste:",
                        "Ajustar Stock",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]);

                if (tipoAjuste == null) {
                    return; // Usuario canceló
                }

                // Pedir la cantidad
                String cantidadStr = JOptionPane.showInputDialog(
                        vista,
                        "Ingrese la cantidad para " + tipoAjuste + ":",
                        "Cantidad",
                        JOptionPane.QUESTION_MESSAGE);

                if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
                    return; // Usuario canceló o no ingresó cantidad
                }

                try {
                    int cantidad = Integer.parseInt(cantidadStr.trim());
                    if (cantidad <= 0) {
                        JOptionPane.showMessageDialog(vista, "La cantidad debe ser mayor que cero",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Aplicar el signo según el tipo de ajuste
                    int cantidadAjuste = "SALIDA".equals(tipoAjuste) ? -cantidad : cantidad;

                    // Realizar el ajuste usando el modelo
                    if (modelo.ajustarStock(idProducto, cantidadAjuste, tipoAjuste)) {
                        cargarTabla(); // Recargar la tabla
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vista, "La cantidad debe ser un número válido",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al ajustar stock: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al ajustar stock: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra productos con stock bajo
     */
    private void verStockBajo() {
        try {
            ArrayList<DecoracionType> productosStockBajo = modelo.getStockBajo();

            if (productosStockBajo.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "No hay productos con stock bajo",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Crear mensaje con los productos de stock bajo
            StringBuilder mensaje = new StringBuilder("Productos con stock bajo:\n\n");
            for (DecoracionType producto : productosStockBajo) {
                mensaje.append("Producto: ").append(producto.getNombreDecoracion())
                        .append("\nStock actual: ").append(producto.getStockDecoracion())
                        .append("\nStock mínimo: ").append(producto.getStockMinimoDecoracion())
                        .append("\nProveedor: ")
                        .append(producto.getIdProveedorDecoracion() != null ? producto.getIdProveedorDecoracion()
                                : "N/A")
                        .append("\n\n");
            }

            JOptionPane.showMessageDialog(vista, mensaje.toString(),
                    "Stock Bajo", JOptionPane.WARNING_MESSAGE);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al obtener stock bajo: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al obtener stock bajo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
