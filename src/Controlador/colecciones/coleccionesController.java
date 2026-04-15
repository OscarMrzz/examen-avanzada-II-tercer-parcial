package Controlador.colecciones;

import Vista.colecciones.coleccionesVista;
import Vista.colecciones.FormularioAgregarColeccion;
import Vista.colecciones.FormularioEditarColeccion;
import Vista.colecciones.reportesColecciones;

public class coleccionesController {
    private coleccionesVista vista;
    private FormularioAgregarColeccion formularioAgregar;
    private FormularioEditarColeccion formularioEditar;
    private reportesColecciones reportes;

    public coleccionesController(coleccionesVista vista) {
        this.vista = vista;
        this.formularioAgregar = new FormularioAgregarColeccion(null, true);
        this.formularioEditar = new FormularioEditarColeccion(null, true);
        this.reportes = new reportesColecciones(null, true);

        initController();
    }

    private void initController() {
        // Botones de navegación
        vista.botonAgregar.addActionListener(e -> abrirFormularioAgregar());
        vista.botonInforme.addActionListener(e -> abrirReportes());

        // Botones de búsqueda
        vista.botonBuscar.addActionListener(e -> buscarColecciones());

        // Botones de los formularios
        formularioAgregar.botonGuardar.addActionListener(e -> guardarColeccion());
        formularioAgregar.botonCancelar.addActionListener(e -> formularioAgregar.setVisible(false));

        formularioEditar.botonGuardar.addActionListener(e -> actualizarColeccion());
        formularioEditar.botonCancelar.addActionListener(e -> formularioEditar.setVisible(false));

        // Botones de reportes
        reportes.botonGenerarReporte.addActionListener(e -> generarReporte());
        reportes.botonCancelar.addActionListener(e -> reportes.setVisible(false));
    }

    public void iniciar() {
        vista.setVisible(true);
        cargarColecciones();
    }

    private void abrirFormularioAgregar() {
        limpiarFormularioAgregar();
        formularioAgregar.setVisible(true);
    }

    private void abrirFormularioEditar() {
        // Obtener la colección seleccionada de la tabla
        int filaSeleccionada = vista.tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            // Cargar datos de la colección en el formulario
            cargarDatosColeccionEnFormulario(filaSeleccionada);
            formularioEditar.setVisible(true);
        } else {
            javax.swing.JOptionPane.showMessageDialog(vista,
                    "Por favor seleccione una colección para editar",
                    "Seleccione Colección",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    private void abrirReportes() {
        reportes.setVisible(true);
    }

    private void buscarColecciones() {
        String textoBusqueda = vista.inputBusqueda.getText().toLowerCase();
        // Implementar lógica de búsqueda
        System.out.println("Buscando colecciones: " + textoBusqueda);
    }

    private void guardarColeccion() {
        // Validar campos
        if (validarFormularioAgregar()) {
            // Implementar lógica de guardado
            System.out.println("Guardando colección...");
            formularioAgregar.setVisible(false);
            cargarColecciones();
        }
    }

    private void actualizarColeccion() {
        // Validar campos
        if (validarFormularioEditar()) {
            // Implementar lógica de actualización
            System.out.println("Actualizando colección...");
            formularioEditar.setVisible(false);
            cargarColecciones();
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

    private void cargarColecciones() {
        // Implementar lógica para cargar colecciones en la tabla
        System.out.println("Cargando lista de colecciones...");
    }

    private void limpiarFormularioAgregar() {
        formularioAgregar.inputNombreColeccion.setText("");
        formularioAgregar.inputDisenadorColeccion.setText("");
        formularioAgregar.inputNumColeccionColeccion.setText("");
        formularioAgregar.inputAnioColeccion.setText("");
        formularioAgregar.inputDescripcionColeccion.setText("");
    }

    private void cargarDatosColeccionEnFormulario(int fila) {
        // Implementar lógica para cargar datos de la colección seleccionada
        // en el formulario de edición
        System.out.println("Cargando datos de la colección de la fila: " + fila);
    }

    private boolean validarFormularioAgregar() {
        String nombre = formularioAgregar.inputNombreColeccion.getText().trim();
        String disenador = formularioAgregar.inputDisenadorColeccion.getText().trim();
        String numColeccion = formularioAgregar.inputNumColeccionColeccion.getText().trim();
        String anio = formularioAgregar.inputAnioColeccion.getText().trim();

        if (nombre.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El nombre de la colección es obligatorio",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (disenador.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El diseñador es obligatorio",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (numColeccion.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El número de colección es obligatorio",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (anio.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El año es obligatorio",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que el año sea un número válido
        try {
            int anioNum = Integer.parseInt(anio);
            if (anioNum < 1900 || anioNum > 2100) {
                javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                        "El año debe estar entre 1900 y 2100",
                        "Año Inválido",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El año debe ser un número válido",
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
