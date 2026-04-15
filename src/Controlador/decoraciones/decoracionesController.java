package Controlador.decoraciones;

import Vista.decoraciones.decoracionesVista;
import Vista.decoraciones.FormularioAgregarDecoracion;
import Vista.decoraciones.FormularioEditarDecoracion;
import Vista.decoraciones.reportesDecoraciones;

public class decoracionesController {
    private decoracionesVista vista;
    private FormularioAgregarDecoracion formularioAgregar;
    private FormularioEditarDecoracion formularioEditar;
    private reportesDecoraciones reportes;

    public decoracionesController(decoracionesVista vista) {
        this.vista = vista;
        this.formularioAgregar = new FormularioAgregarDecoracion(null, true);
        this.formularioEditar = new FormularioEditarDecoracion(null, true);
        this.reportes = new reportesDecoraciones(null, true);

        initController();
    }

    private void initController() {
        // Botones de navegación
        vista.botonAgregar.addActionListener(e -> abrirFormularioAgregar());
        vista.botonInforme.addActionListener(e -> abrirReportes());

        // Botones de búsqueda
        vista.botonBuscar.addActionListener(e -> buscarDecoraciones());

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

        System.out.println("Generando reporte: " + tipoReporte);
        System.out.println("Fecha inicio: " + fechaInicio);
        System.out.println("Fecha fin: " + fechaFin);

        // Implementar lógica de generación de reportes
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
        formularioAgregar.inputPrecioCostoDecoracion.setText("");
        formularioAgregar.inputPrecioVentaDecoracion.setText("");
        // No establecer índice en ComboBox vacío - se cargará con datos reales
        if (formularioAgregar.comboBoxProveedor.getItemCount() > 0) {
            formularioAgregar.comboBoxProveedor.setSelectedIndex(0);
        }
        formularioAgregar.inputImagenDecoracion.setText("");
        formularioAgregar.checkBoxEsColeccion.setSelected(false);
        formularioAgregar.inputDisenadorDecoracion.setText("");
        formularioAgregar.inputNumColeccionDecoracion.setText("");
        formularioAgregar.inputAnioDecoracion.setText("");
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
        String precioCosto = formularioAgregar.inputPrecioCostoDecoracion.getText().trim();
        String precioVenta = formularioAgregar.inputPrecioVentaDecoracion.getText().trim();
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

        if (proveedor == null || proveedor.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "Debe seleccionar un proveedor",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que precio venta sea mayor que precio costo
        try {
            double pc = Double.parseDouble(precioCosto);
            double pv = Double.parseDouble(precioVenta);
            if (pv <= pc) {
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

        return true;
    }

    private boolean validarFormularioEditar() {
        // Misma validación que el formulario agregar
        return validarFormularioAgregar();
    }

    public void volverAlHome() {
        vista.setVisible(false);
    }
}
