package Controlador.compras;

import Vista.compras.comprasVista;
import Vista.compras.FormularioAgregarCompra;
import Vista.compras.FormularioEditarCompra;
import Vista.compras.reportesCompras;
import Modelo.reportes.JasperService;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.Map;

public class comprasController {
    private comprasVista vista;
    private FormularioAgregarCompra formularioAgregar;
    private FormularioEditarCompra formularioEditar;
    private reportesCompras reportes;
    private JasperService jasper;

    public comprasController(comprasVista vista, FormularioAgregarCompra formularioAgregar,
            FormularioEditarCompra formularioEditar, reportesCompras reportes) {
        this.vista = vista;
        this.formularioAgregar = formularioAgregar;
        this.formularioEditar = formularioEditar;
        this.reportes = reportes;
        this.jasper = new JasperService();

        initController();
    }

    private void initController() {
        // Botones de navegación
        vista.botonAgregar.addActionListener(e -> abrirFormularioAgregar());
        vista.botonInforme.addActionListener(e -> abrirReportes());

        // Botones de búsqueda
        vista.botonBuscar.addActionListener(e -> buscarCompras());

        // Menú contextual
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

        // Botones de los formularios
        formularioAgregar.botonGuardar.addActionListener(e -> guardarCompra());
        formularioAgregar.botonCancelar.addActionListener(e -> formularioAgregar.setVisible(false));

        formularioEditar.botonGuardar.addActionListener(e -> actualizarCompra());
        formularioEditar.botonCancelar.addActionListener(e -> formularioEditar.setVisible(false));

        // Botones de reportes
        reportes.botonGenerarReporte.addActionListener(e -> generarReporte());
        reportes.botonCancelar.addActionListener(e -> reportes.setVisible(false));
    }

    public void iniciar() {
        vista.setVisible(true);
        cargarCompras();
    }

    private void abrirFormularioAgregar() {
        limpiarFormularioAgregar();
        formularioAgregar.setVisible(true);
    }

    private void abrirFormularioEditar() {
        // Obtener la compra seleccionada de la tabla
        int filaSeleccionada = vista.tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            // Cargar datos de la compra en el formulario
            cargarDatosCompraEnFormulario(filaSeleccionada);
            formularioEditar.setVisible(true);
        } else {
            javax.swing.JOptionPane.showMessageDialog(vista,
                    "Por favor seleccione una compra para editar",
                    "Seleccione Compra",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    private void abrirReportes() {
        reportes.setVisible(true);
    }

    private void buscarCompras() {
        String textoBusqueda = vista.inputBusqueda.getText().toLowerCase();
        // Implementar lógica de búsqueda
        System.out.println("Buscando compras: " + textoBusqueda);
    }

    private void guardarCompra() {
        // Validar campos
        if (validarFormularioAgregar()) {
            // Implementar lógica de guardado
            System.out.println("Guardando compra...");
            formularioAgregar.setVisible(false);
            cargarCompras();
        }
    }

    private void actualizarCompra() {
        // Validar campos
        if (validarFormularioEditar()) {
            // Implementar lógica de actualización
            System.out.println("Actualizando compra...");
            formularioEditar.setVisible(false);
            cargarCompras();
        }
    }

    private void generarReporte() {
        String tipoReporte = (String) reportes.comboBoxTipoReporte.getSelectedItem();
        String fechaInicio = reportes.inputFechaInicio.getText();
        String fechaFin = reportes.inputFechaFin.getText();
        if (tipoReporte == null) {
            javax.swing.JOptionPane.showMessageDialog(reportes, "Seleccione un tipo de reporte.", "Reporte",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date fi = JasperService.parseSqlDateOrNull(fechaInicio);
        Date ff = JasperService.parseSqlDateOrNull(fechaFin);

        try {
            switch (tipoReporte) {
                case "Compras por Fecha": {
                    if (fi == null || ff == null) {
                        javax.swing.JOptionPane.showMessageDialog(reportes,
                                "Ingrese Fecha Inicio y Fecha Fin en formato AAAA-MM-DD.",
                                "Fechas requeridas", javax.swing.JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    Map<String, Object> p = JasperService.params(
                            "titulo", "Compras por rango de fechas",
                            "fechaInicio", fi,
                            "fechaFin", ff);
                    jasper.verReporte("/reportes/compras_por_fechas.jrxml", p);
                    break;
                }
                case "Listado General":
                case "Compras por Proveedor":
                case "Compras por Estado":
                case "Resumen Mensual": {
                    javax.swing.JOptionPane.showMessageDialog(reportes,
                            "Este tipo de reporte aún no tiene plantilla Jasper específica.\nUse 'Compras por Fecha' por ahora.",
                            "Reporte", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
                default:
                    javax.swing.JOptionPane.showMessageDialog(reportes,
                            "Tipo de reporte no soportado: " + tipoReporte,
                            "Reporte", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(reportes,
                    "No se pudo generar el reporte.\n" + ex.getMessage(),
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarCompras() {
        // Implementar lógica para cargar compras en la tabla
        System.out.println("Cargando lista de compras...");
    }

    private void limpiarFormularioAgregar() {
        formularioAgregar.comboBoxProveedor.setSelectedIndex(0);
        formularioAgregar.inputFechaCompra.setText("");
        formularioAgregar.inputCantidad.setText("");
        formularioAgregar.comboBoxEstado.setSelectedIndex(0);
        formularioAgregar.inputObservaciones.setText("");
        formularioAgregar.comboBoxDecoracion.setSelectedIndex(0);
        formularioAgregar.inputPrecioCosto.setText("");
        formularioAgregar.inputPrecioVenta.setText("");
    }

    private void cargarDatosCompraEnFormulario(int fila) {
        // Implementar lógica para cargar datos de la compra seleccionada
        // en el formulario de edición
        System.out.println("Cargando datos de la compra de la fila: " + fila);
    }

    private boolean validarFormularioAgregar() {
        String proveedor = (String) formularioAgregar.comboBoxProveedor.getSelectedItem();
        String fechaCompra = formularioAgregar.inputFechaCompra.getText().trim();
        String cantidad = formularioAgregar.inputCantidad.getText().trim();
        String estado = (String) formularioAgregar.comboBoxEstado.getSelectedItem();
        String decoracion = (String) formularioAgregar.comboBoxDecoracion.getSelectedItem();
        String precioCosto = formularioAgregar.inputPrecioCosto.getText().trim();
        String precioVenta = formularioAgregar.inputPrecioVenta.getText().trim();

        if (proveedor == null || proveedor.equals("Seleccione un proveedor")) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "Debe seleccionar un proveedor",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (fechaCompra.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "La fecha de compra es obligatoria",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (cantidad.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "La cantidad es obligatoria",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (decoracion == null || decoracion.equals("Seleccione una decoración")) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "Debe seleccionar una decoración",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (precioCosto.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El precio de costo es obligatorio",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (precioVenta.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El precio de venta es obligatorio",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que la cantidad sea un número válido
        try {
            int cantidadNum = Integer.parseInt(cantidad);
            if (cantidadNum <= 0) {
                javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                        "La cantidad debe ser mayor que 0",
                        "Valor Inválido",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "La cantidad debe ser un número válido",
                    "Error de Formato",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que los precios sean números válidos
        try {
            double precioCostoNum = Double.parseDouble(precioCosto);
            double precioVentaNum = Double.parseDouble(precioVenta);

            if (precioCostoNum <= 0) {
                javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                        "El precio de costo debe ser mayor que 0",
                        "Valor Inválido",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (precioVentaNum <= 0) {
                javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                        "El precio de venta debe ser mayor que 0",
                        "Valor Inválido",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (precioVentaNum <= precioCostoNum) {
                javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                        "El precio de venta debe ser mayor que el precio de costo",
                        "Validación de Precio",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "Los precios deben ser números válidos",
                    "Error de Formato",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (estado == null || estado.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "Debe seleccionar un estado",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean validarFormularioEditar() {
        // Misma validación que el formulario agregar
        return validarFormularioAgregar();
    }

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

    private void eliminar() {
        int filaSeleccionada = vista.tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int confirmacion = javax.swing.JOptionPane.showConfirmDialog(vista,
                    "¿Está seguro de que desea eliminar esta compra?",
                    "Confirmar Eliminación",
                    javax.swing.JOptionPane.YES_NO_OPTION);
            if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
                System.out.println("Eliminando compra en la fila: " + filaSeleccionada);
                cargarCompras();
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(vista,
                    "Por favor seleccione una compra para eliminar",
                    "Seleccione Compra",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    public void volverAlHome() {
        vista.setVisible(false);
    }
}
