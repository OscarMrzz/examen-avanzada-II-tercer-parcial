package Controlador.clientes;

import Vista.clientes.clientesVista;
import Vista.clientes.FormularioAgregarCliente;
import Vista.clientes.FormularioEditarCliente;
import Vista.clientes.reportesClientes;
import Vista.home.Home;

public class clientesController {
    private clientesVista vista;
    private Home home;
    private FormularioAgregarCliente formularioAgregar;
    private FormularioEditarCliente formularioEditar;
    private reportesClientes reportes;

    public clientesController(clientesVista vista, Home home) {
        this.vista = vista;
        this.home = home;
        this.formularioAgregar = new FormularioAgregarCliente(null, true);
        this.formularioEditar = new FormularioEditarCliente(null, true);
        this.reportes = new reportesClientes(null, true);
        
        initController();
    }

    private void initController() {
        // Botones de navegación
        vista.botonAgregar.addActionListener(e -> abrirFormularioAgregar());
        vista.botonInforme.addActionListener(e -> abrirReportes());
        
        // Botones de búsqueda
        vista.botonBuscar.addActionListener(e -> buscarClientes());
        
        // Botones de los formularios
        formularioAgregar.botonGuardar.addActionListener(e -> guardarCliente());
        formularioAgregar.botonCancelar.addActionListener(e -> formularioAgregar.setVisible(false));
        
        formularioEditar.botonGuardar.addActionListener(e -> actualizarCliente());
        formularioEditar.botonCancelar.addActionListener(e -> formularioEditar.setVisible(false));
        
        // Botones de reportes
        reportes.botonGenerarReporte.addActionListener(e -> generarReporte());
        reportes.botonCancelar.addActionListener(e -> reportes.setVisible(false));
    }

    public void iniciar() {
        vista.setVisible(true);
        if (home != null) {
            home.setVisible(false);
        }
        cargarClientes();
    }

    private void abrirFormularioAgregar() {
        limpiarFormularioAgregar();
        formularioAgregar.setVisible(true);
    }

    private void abrirFormularioEditar() {
        // Obtener el cliente seleccionado de la tabla
        int filaSeleccionada = vista.tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            // Cargar datos del cliente en el formulario
            cargarDatosClienteEnFormulario(filaSeleccionada);
            formularioEditar.setVisible(true);
        } else {
            javax.swing.JOptionPane.showMessageDialog(vista, 
                "Por favor seleccione un cliente para editar", 
                "Seleccione Cliente", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    private void abrirReportes() {
        reportes.setVisible(true);
    }

    private void buscarClientes() {
        String textoBusqueda = vista.inputBusqueda.getText().toLowerCase();
        // Implementar lógica de búsqueda
        System.out.println("Buscando clientes: " + textoBusqueda);
    }

    private void guardarCliente() {
        // Validar campos
        if (validarFormularioAgregar()) {
            // Implementar lógica de guardado
            System.out.println("Guardando cliente...");
            formularioAgregar.setVisible(false);
            cargarClientes();
        }
    }

    private void actualizarCliente() {
        // Validar campos
        if (validarFormularioEditar()) {
            // Implementar lógica de actualización
            System.out.println("Actualizando cliente...");
            formularioEditar.setVisible(false);
            cargarClientes();
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

    private void cargarClientes() {
        // Implementar lógica para cargar clientes en la tabla
        System.out.println("Cargando lista de clientes...");
    }

    private void limpiarFormularioAgregar() {
        formularioAgregar.inputNombreCliente.setText("");
        formularioAgregar.inputRTNCliente.setText("");
        formularioAgregar.comboBoxTipoCliente.setSelectedIndex(0);
        formularioAgregar.inputTelefonoCliente.setText("");
        formularioAgregar.inputEmailCliente.setText("");
        formularioAgregar.inputDireccionCliente.setText("");
    }

    private void cargarDatosClienteEnFormulario(int fila) {
        // Implementar lógica para cargar datos del cliente seleccionado
        // en el formulario de edición
        System.out.println("Cargando datos del cliente de la fila: " + fila);
    }

    private boolean validarFormularioAgregar() {
        String nombre = formularioAgregar.inputNombreCliente.getText().trim();
        String rtn = formularioAgregar.inputRTNCliente.getText().trim();
        String telefono = formularioAgregar.inputTelefonoCliente.getText().trim();
        String tipoCliente = (String) formularioAgregar.comboBoxTipoCliente.getSelectedItem();
        
        if (nombre.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar, 
                "El nombre del cliente es obligatorio", 
                "Campo Requerido", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (telefono.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar, 
                "El teléfono del cliente es obligatorio", 
                "Campo Requerido", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (tipoCliente == null || tipoCliente.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar, 
                "Debe seleccionar un tipo de cliente", 
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
        if (home != null) {
            home.setVisible(true);
        }
    }
}
