package Controlador.compras;

import Vista.compras.comprasVista;
import Vista.compras.FormularioAgregarCompra;
import Vista.compras.FormularioEditarCompra;
import Vista.compras.reportesCompras;

public class comprasController {
    private comprasVista vista;
    private FormularioAgregarCompra formularioAgregar;
    private FormularioEditarCompra formularioEditar;
    private reportesCompras reportes;

    public comprasController(comprasVista vista) {
        this.vista = vista;
        this.formularioAgregar = new FormularioAgregarCompra(null, true);
        this.formularioEditar = new FormularioEditarCompra(null, true);
        this.reportes = new reportesCompras(null, true);

        initController();
    }

    private void initController() {
        // Botones de navegación
        vista.botonAgregar.addActionListener(e -> abrirFormularioAgregar());
        vista.botonInforme.addActionListener(e -> abrirReportes());

        // Botones de búsqueda
        vista.botonBuscar.addActionListener(e -> buscarCompras());

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

        System.out.println("Generando reporte: " + tipoReporte);
        System.out.println("Fecha inicio: " + fechaInicio);
        System.out.println("Fecha fin: " + fechaFin);

        // Implementar lógica de generación de reportes
    }

    private void cargarCompras() {
        // Implementar lógica para cargar compras en la tabla
        System.out.println("Cargando lista de compras...");
    }

    private void limpiarFormularioAgregar() {
        formularioAgregar.comboBoxProveedor.setSelectedIndex(0);
        formularioAgregar.inputFechaCompra.setText("");
        formularioAgregar.inputTotalCompra.setText("");
        formularioAgregar.comboBoxEstado.setSelectedIndex(0);
        formularioAgregar.inputObservaciones.setText("");
    }

    private void cargarDatosCompraEnFormulario(int fila) {
        // Implementar lógica para cargar datos de la compra seleccionada
        // en el formulario de edición
        System.out.println("Cargando datos de la compra de la fila: " + fila);
    }

    private boolean validarFormularioAgregar() {
        String proveedor = (String) formularioAgregar.comboBoxProveedor.getSelectedItem();
        String fechaCompra = formularioAgregar.inputFechaCompra.getText().trim();
        String total = formularioAgregar.inputTotalCompra.getText().trim();
        String estado = (String) formularioAgregar.comboBoxEstado.getSelectedItem();

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

        if (total.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El total de la compra es obligatorio",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que el total sea un número válido
        try {
            double totalNum = Double.parseDouble(total);
            if (totalNum <= 0) {
                javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                        "El total debe ser mayor que 0",
                        "Valor Inválido",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El total debe ser un número válido",
                    "Error de Formato",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (estado == null) {
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

    public void volverAlHome() {
        vista.setVisible(false);
    }
}
