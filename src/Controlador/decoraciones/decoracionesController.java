package Controlador.decoraciones;

import Vista.decoraciones.decoracionesVista;
import Vista.decoraciones.FormularioAgregarDecoracion;
import Vista.decoraciones.FormularioEditarDecoracion;
import Vista.decoraciones.reportesDecoraciones;
import Modelo.reportes.JasperService;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.Map;

public class decoracionesController {
    private decoracionesVista vista;
    private FormularioAgregarDecoracion formularioAgregar;
    private FormularioEditarDecoracion formularioEditar;
    private reportesDecoraciones reportes;
    private JasperService jasper;

    public decoracionesController(decoracionesVista vista, FormularioAgregarDecoracion formularioAgregar,
            FormularioEditarDecoracion formularioEditar, reportesDecoraciones reportes) {
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
        vista.botonBuscar.addActionListener(e -> buscarDecoraciones());

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
        formularioAgregar.botonGuardar.addActionListener(e -> guardarDecoracion());
        formularioAgregar.botonCancelar.addActionListener(e -> formularioAgregar.setVisible(false));

        formularioEditar.botonGuardar.addActionListener(e -> actualizarDecoracion());
        formularioEditar.botonCancelar.addActionListener(e -> formularioEditar.setVisible(false));

        // Botones de reportes
        reportes.botonGenerarReporte.addActionListener(e -> generarReporte());
        reportes.botonCancelar.addActionListener(e -> reportes.setVisible(false));
    }

    public void iniciar() {
        vista.setVisible(true);
        cargarDecoraciones();
    }

    private void abrirFormularioAgregar() {
        limpiarFormularioAgregar();
        formularioAgregar.setVisible(true);
    }

    private void abrirFormularioEditar() {
        // Obtener la decoración seleccionada de la tabla
        int filaSeleccionada = vista.tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            // Cargar datos de la decoración en el formulario
            cargarDatosDecoracionEnFormulario(filaSeleccionada);
            formularioEditar.setVisible(true);
        } else {
            javax.swing.JOptionPane.showMessageDialog(vista,
                    "Por favor seleccione una decoración para editar",
                    "Seleccione Decoración",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    private void abrirReportes() {
        reportes.setVisible(true);
    }

    private void buscarDecoraciones() {
        String textoBusqueda = vista.inputBusqueda.getText().toLowerCase();
        // Implementar lógica de búsqueda
        System.out.println("Buscando decoraciones: " + textoBusqueda);
    }

    private void guardarDecoracion() {
        // Validar campos
        if (validarFormularioAgregar()) {
            // Implementar lógica de guardado
            System.out.println("Guardando decoración...");
            formularioAgregar.setVisible(false);
            cargarDecoraciones();
        }
    }

    private void actualizarDecoracion() {
        // Validar campos
        if (validarFormularioEditar()) {
            // Implementar lógica de actualización
            System.out.println("Actualizando decoración...");
            formularioEditar.setVisible(false);
            cargarDecoraciones();
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
                case "Inventario Actual": {
                    Map<String, Object> p = JasperService.params(
                            "titulo", "Decoraciones con stock (existencia)");
                    jasper.verReporte("/reportes/decoraciones_existencia.jrxml", p);
                    break;
                }
                case "Reporte de Ventas": {
                    Map<String, Object> p = JasperService.params(
                            "titulo", "Decoraciones más vendidas (incluye gráfica)");
                    jasper.verReporte("/reportes/decoraciones_mas_vendidas.jrxml", p);
                    break;
                }
                case "Listado General":
                case "Decoraciones por Proveedor":
                case "Decoraciones por Colección":
                case "Stock Bajo": {
                    javax.swing.JOptionPane.showMessageDialog(reportes,
                            "Este tipo de reporte aún no tiene plantilla Jasper específica.\nUse 'Inventario Actual' o 'Reporte de Ventas'.",
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

    private void cargarDecoraciones() {
        // Implementar lógica para cargar decoraciones en la tabla
        System.out.println("Cargando lista de decoraciones...");
    }

    private void limpiarFormularioAgregar() {
        formularioAgregar.inputNombreDecoracion.setText("");
        formularioAgregar.inputStockDecoracion.setText("");
        formularioAgregar.inputStockMinimoDecoracion.setText("");
        formularioAgregar.inputStockMaximoDecoracion.setText("");
        // No establecer índice en ComboBox vacío - se cargará con datos reales
        if (formularioAgregar.comboBoxProveedor.getItemCount() > 0) {
            formularioAgregar.comboBoxProveedor.setSelectedIndex(0);
        }
        formularioAgregar.inputImagenDecoracion.setText("");
        // No establecer índice en ComboBox de colecciones vacío - se cargará con datos
        // reales
        if (formularioAgregar.comboBoxColeccion.getItemCount() > 0) {
            formularioAgregar.comboBoxColeccion.setSelectedIndex(0);
        }
        formularioAgregar.inputDescripcionDecoracion.setText("");
    }

    private void cargarDatosDecoracionEnFormulario(int fila) {
        // Implementar lógica para cargar datos de la decoración seleccionada
        // en el formulario de edición
        System.out.println("Cargando datos de la decoración de la fila: " + fila);
    }

    private boolean validarFormularioAgregar() {
        String nombre = formularioAgregar.inputNombreDecoracion.getText().trim();
        String stock = formularioAgregar.inputStockDecoracion.getText().trim();
        String stockMinimo = formularioAgregar.inputStockMinimoDecoracion.getText().trim();
        String stockMaximo = formularioAgregar.inputStockMaximoDecoracion.getText().trim();
        String proveedor = (String) formularioAgregar.comboBoxProveedor.getSelectedItem();

        if (nombre.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El nombre de la decoración es obligatorio",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (stock.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El stock es obligatorio",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (proveedor == null || proveedor.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "Debe seleccionar un proveedor",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que stock máximo sea mayor que stock mínimo
        try {
            int stockActual = Integer.parseInt(stock);
            int min = Integer.parseInt(stockMinimo);
            int max = Integer.parseInt(stockMaximo);
            if (max <= min) {
                javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                        "El stock máximo debe ser mayor que el stock mínimo",
                        "Validación de Stock",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (stockActual < 0) {
                javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                        "El stock actual no puede ser negativo",
                        "Validación de Stock",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "Los valores de stock deben ser números válidos",
                    "Error de Formato",
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
                    "¿Está seguro de que desea eliminar esta decoración?",
                    "Confirmar Eliminación",
                    javax.swing.JOptionPane.YES_NO_OPTION);
            if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
                System.out.println("Eliminando decoración en la fila: " + filaSeleccionada);
                cargarDecoraciones();
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(vista,
                    "Por favor seleccione una decoración para eliminar",
                    "Seleccione Decoración",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    public void volverAlHome() {
        vista.setVisible(false);
    }
}
