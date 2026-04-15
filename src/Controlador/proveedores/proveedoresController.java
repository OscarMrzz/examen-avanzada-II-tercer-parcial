package Controlador.proveedores;

import Vista.proveedores.proveedoresVista;
import Vista.proveedores.FormularioAgregarProveedor;
import Vista.proveedores.FormularioEditarProveedor;
import Vista.proveedores.reportesProveedores;
import Modelo.reportes.JasperService;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class proveedoresController {
    private proveedoresVista vista;
    private FormularioAgregarProveedor formularioAgregar;
    private FormularioEditarProveedor formularioEditar;
    private reportesProveedores reportes;
    private JasperService jasper;

    public proveedoresController(proveedoresVista vista, FormularioAgregarProveedor formularioAgregar,
            FormularioEditarProveedor formularioEditar, reportesProveedores reportes) {
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
        vista.botonBuscar.addActionListener(e -> buscarProveedores());

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
        formularioAgregar.botonGuardar.addActionListener(e -> guardarProveedor());
        formularioAgregar.botonCancelar.addActionListener(e -> formularioAgregar.setVisible(false));

        formularioEditar.botonGuardar.addActionListener(e -> actualizarProveedor());
        formularioEditar.botonCancelar.addActionListener(e -> formularioEditar.setVisible(false));

        // Botones de reportes
        reportes.botonGenerarReporte.addActionListener(e -> generarReporte());
        reportes.botonCancelar.addActionListener(e -> reportes.setVisible(false));
    }

    public void iniciar() {
        vista.setVisible(true);
        cargarProveedores();
    }

    private void abrirFormularioAgregar() {
        limpiarFormularioAgregar();
        formularioAgregar.setVisible(true);
    }

    private void abrirFormularioEditar() {
        // Obtener el proveedor seleccionado de la tabla
        int filaSeleccionada = vista.tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            // Cargar datos del proveedor en el formulario
            cargarDatosProveedorEnFormulario(filaSeleccionada);
            formularioEditar.setVisible(true);
        } else {
            javax.swing.JOptionPane.showMessageDialog(vista,
                    "Por favor seleccione un proveedor para editar",
                    "Seleccione Proveedor",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    private void abrirReportes() {
        reportes.setVisible(true);
    }

    private void buscarProveedores() {
        String textoBusqueda = vista.inputBusqueda.getText().toLowerCase();
        // Implementar lógica de búsqueda
        System.out.println("Buscando proveedores: " + textoBusqueda);
    }

    private void guardarProveedor() {
        // Validar campos
        if (validarFormularioAgregar()) {
            // Implementar lógica de guardado
            System.out.println("Guardando proveedor...");
            formularioAgregar.setVisible(false);
            cargarProveedores();
        }
    }

    private void actualizarProveedor() {
        // Validar campos
        if (validarFormularioEditar()) {
            // Implementar lógica de actualización
            System.out.println("Actualizando proveedor...");
            formularioEditar.setVisible(false);
            cargarProveedores();
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

        try {
            switch (tipoReporte) {
                case "Listado General":
                case "Proveedores Activos":
                case "Proveedores por RTN":
                case "Reporte de Contactos": {
                    Map<String, Object> p = JasperService.params(
                            "titulo", "Módulo Proveedores: reportes Jasper pendientes (pagos/recibos).");
                    jasper.verReporte("/reportes/compras_por_fechas.jrxml", p);
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

    private void cargarProveedores() {
        // Implementar lógica para cargar proveedores en la tabla
        System.out.println("Cargando lista de proveedores...");
    }

    private void limpiarFormularioAgregar() {
        formularioAgregar.inputNombreProveedor.setText("");
        formularioAgregar.inputRTNProveedor.setText("");
        formularioAgregar.inputTelefonoProveedor.setText("");
        formularioAgregar.inputEmailProveedor.setText("");
        formularioAgregar.inputDireccionProveedor.setText("");
        formularioAgregar.inputContactoProveedor.setText("");
    }

    private void cargarDatosProveedorEnFormulario(int fila) {
        // Implementar lógica para cargar datos del proveedor seleccionado
        // en el formulario de edición
        System.out.println("Cargando datos del proveedor de la fila: " + fila);
    }

    private boolean validarFormularioAgregar() {
        String nombre = formularioAgregar.inputNombreProveedor.getText().trim();
        String rtn = formularioAgregar.inputRTNProveedor.getText().trim();
        String telefono = formularioAgregar.inputTelefonoProveedor.getText().trim();

        if (nombre.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El nombre del proveedor es obligatorio",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (telefono.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El teléfono del proveedor es obligatorio",
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
                    "¿Está seguro de que desea eliminar este proveedor?",
                    "Confirmar Eliminación",
                    javax.swing.JOptionPane.YES_NO_OPTION);
            if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
                System.out.println("Eliminando proveedor en la fila: " + filaSeleccionada);
                cargarProveedores();
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(vista,
                    "Por favor seleccione un proveedor para eliminar",
                    "Seleccione Proveedor",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    public void volverAlHome() {
        vista.setVisible(false);
    }
}
