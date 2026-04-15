package Controlador.proveedores;

import Vista.proveedores.proveedoresVista;
import Vista.proveedores.FormularioAgregarProveedor;
import Vista.proveedores.FormularioEditarProveedor;
import Vista.proveedores.reportesProveedores;
import Modelo.reportes.JasperService;
import Modelo.proveedores.ProveedorModel;
import Type.proveedores.ProveedorType;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador principal para la vista de proveedores
 * 
 * @author ossca
 */
public class proveedoresVistaController {

    private static final Logger logger = Logger.getLogger(proveedoresVistaController.class.getName());
    private proveedoresVista vista;
    private ProveedorModel modelo;
    private FormularioAgregarProveedor formularioAgregar;
    private FormularioEditarProveedor formularioEditar;
    private reportesProveedores reportes;
    private JasperService jasper;

    public proveedoresVistaController(proveedoresVista vista, FormularioAgregarProveedor formularioAgregar,
            FormularioEditarProveedor formularioEditar, reportesProveedores reportes) {
        this.vista = vista;
        this.formularioAgregar = formularioAgregar;
        this.formularioEditar = formularioEditar;
        this.reportes = reportes;
        this.modelo = new ProveedorModel();
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
                if (vista.inputBusqueda.getText().equals("Buscar proveedor...")) {
                    vista.inputBusqueda.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().isEmpty()) {
                    vista.inputBusqueda.setText("Buscar proveedor...");
                }
            }
        });

        // Inicializar el campo de búsqueda
        vista.inputBusqueda.setText("Buscar proveedor...");

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

        try {
            Map<String, Object> p = JasperService.params("titulo", "Listado de proveedores");
            jasper.verReporte("/reportes/proveedores_listado.jrxml", p);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(reportes,
                    "No se pudo generar el reporte.\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga todos los proveedores en la tabla usando el Modelo
     */
    public void cargarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<ProveedorType> proveedores = modelo.getAll();
            int index = 1;

            for (ProveedorType proveedor : proveedores) {
                Object[] fila = {
                        index++, // Número de registro/índice
                        proveedor.getNombreProveedor(),
                        proveedor.getRtnProveedor() != null ? proveedor.getRtnProveedor() : "N/A",
                        proveedor.getTelefonoProveedor(),
                        proveedor.getEmailProveedor() != null ? proveedor.getEmailProveedor() : "N/A",
                        proveedor.getContactoProveedor() != null ? proveedor.getContactoProveedor() : "N/A",
                        proveedor.isEstadoProveedor() ? "ACTIVO" : "INACTIVO"
                };
                modeloTabla.addRow(fila);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar los proveedores: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar los proveedores: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca proveedores según el texto ingresado
     */
    private void buscar(ActionEvent e) {
        String textoBusqueda = vista.inputBusqueda.getText().trim();

        if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar proveedor...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<ProveedorType> proveedores = modelo.getAll();
            int index = 1;

            for (ProveedorType proveedor : proveedores) {
                // Búsqueda en múltiples campos (case-insensitive)
                String rtn = proveedor.getRtnProveedor() != null ? proveedor.getRtnProveedor() : "";
                String email = proveedor.getEmailProveedor() != null ? proveedor.getEmailProveedor() : "";
                String contacto = proveedor.getContactoProveedor() != null ? proveedor.getContactoProveedor() : "";
                String estado = proveedor.isEstadoProveedor() ? "ACTIVO" : "INACTIVO";

                if (proveedor.getNombreProveedor().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        rtn.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        proveedor.getTelefonoProveedor().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        email.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        contacto.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        estado.toLowerCase().contains(textoBusqueda.toLowerCase())) {

                    Object[] fila = {
                            index++, // Mantener índice secuencial
                            proveedor.getNombreProveedor(),
                            rtn.isEmpty() ? "N/A" : rtn,
                            proveedor.getTelefonoProveedor(),
                            email.isEmpty() ? "N/A" : email,
                            contacto.isEmpty() ? "N/A" : contacto,
                            estado
                    };
                    modeloTabla.addRow(fila);
                }
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al buscar proveedores: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al buscar proveedores: " + ex.getMessage(),
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
     * Abre el formulario para agregar un nuevo proveedor
     */
    private void abrirFormularioAgregar(ActionEvent e) {
        new FormularioAgregarProveedorController(formularioAgregar);
        formularioAgregar.setVisible(true);

        cargarTabla();
    }

    /**
     * Abre el formulario para editar el proveedor seleccionado
     */
    private void abrirFormularioEditar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un proveedor para editar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real del proveedor desde el modelo usando el índice de la fila
        try {
            ArrayList<ProveedorType> proveedores = modelo.getAll();
            if (fila < proveedores.size()) {
                String idProveedor = proveedores.get(fila).getIdProveedor();

                new FormularioEditarProveedorController(formularioEditar, idProveedor);
                formularioEditar.setVisible(true);

                cargarTabla();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al obtener el proveedor: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al obtener el proveedor: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina el proveedor seleccionado usando el Modelo
     */
    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un proveedor para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real y nombre del proveedor desde el modelo usando el índice de
        // la fila
        try {
            ArrayList<ProveedorType> proveedores = modelo.getAll();
            if (fila < proveedores.size()) {
                ProveedorType proveedorSeleccionado = proveedores.get(fila);
                String idProveedor = proveedorSeleccionado.getIdProveedor();
                String nombreProveedor = proveedorSeleccionado.getNombreProveedor();

                int confirmacion = JOptionPane.showConfirmDialog(
                        vista,
                        "¿Está seguro de que desea eliminar al proveedor \"" + nombreProveedor + "\"?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (modelo.delete(idProveedor)) {
                        JOptionPane.showMessageDialog(vista, "Proveedor eliminado correctamente",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarTabla();
                    } else {
                        JOptionPane.showMessageDialog(vista, "No se pudo eliminar el proveedor",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al eliminar proveedor: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al eliminar proveedor: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
