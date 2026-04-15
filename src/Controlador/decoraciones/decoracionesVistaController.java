package Controlador.decoraciones;

import Vista.decoraciones.decoracionesVista;
import Vista.decoraciones.FormularioAgregarDecoracion;
import Vista.decoraciones.FormularioEditarDecoracion;
import Vista.decoraciones.reportesDecoraciones;
import Modelo.reportes.JasperService;
import Modelo.decoraciones.DecoracionModel;
import Type.decoraciones.DecoracionType;
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
 * Controlador principal para la vista de decoraciones
 * 
 * @author ossca
 */
public class decoracionesVistaController {

    private static final Logger logger = Logger.getLogger(decoracionesVistaController.class.getName());
    private decoracionesVista vista;
    private DecoracionModel modelo;
    private FormularioAgregarDecoracion formularioAgregar;
    private FormularioEditarDecoracion formularioEditar;
    private reportesDecoraciones reportes;
    private JasperService jasper;

    public decoracionesVistaController(decoracionesVista vista, FormularioAgregarDecoracion formularioAgregar,
            FormularioEditarDecoracion formularioEditar, reportesDecoraciones reportes) {
        this.vista = vista;
        this.formularioAgregar = formularioAgregar;
        this.formularioEditar = formularioEditar;
        this.reportes = reportes;
        this.modelo = new DecoracionModel();
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
                if (vista.inputBusqueda.getText().equals("Buscar decoración...")) {
                    vista.inputBusqueda.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().isEmpty()) {
                    vista.inputBusqueda.setText("Buscar decoración...");
                }
            }
        });

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
            switch (tipoReporte) {
                case "Inventario Actual": {
                    Map<String, Object> p = JasperService.params("titulo", "Decoraciones en existencia (stock > 0)");
                    jasper.verReporte("/reportes/decoraciones_existencia.jrxml", p);
                    break;
                }
                case "Reporte de Ventas": {
                    Map<String, Object> p = JasperService.params("titulo", "Decoraciones más vendidas (incluye gráfica)");
                    jasper.verReporte("/reportes/decoraciones_mas_vendidas.jrxml", p);
                    break;
                }
                default:
                    JOptionPane.showMessageDialog(reportes,
                            "Este tipo de reporte aún no tiene plantilla Jasper específica.\nUse 'Inventario Actual' o 'Reporte de Ventas'.",
                            "Reporte", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(reportes,
                    "No se pudo generar el reporte.\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga todas las decoraciones en la tabla usando el Modelo
     */
    public void cargarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<DecoracionType> decoraciones = modelo.getAll();
            int index = 1;

            for (DecoracionType decoracion : decoraciones) {
                Object[] fila = {
                        index++, // Número de registro/índice
                        decoracion.getNombreDecoracion(),
                        decoracion.getStockDecoracion(),
                        decoracion.getStockMinimoDecoracion(),
                        decoracion.getStockMaximoDecoracion(),
                        decoracion.getIdProveedorDecoracion() != null ? decoracion.getIdProveedorDecoracion() : "N/A",
                        decoracion.getIdColeccionDecoracion() != null ? decoracion.getIdColeccionDecoracion() : "N/A",
                        decoracion.getDescripcionDecoracion() != null ? decoracion.getDescripcionDecoracion() : "N/A",
                        decoracion.isEstadoDecoracion() ? "ACTIVO" : "INACTIVO"
                };
                modeloTabla.addRow(fila);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar las decoraciones: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar las decoraciones: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca decoraciones según el texto ingresado
     */
    private void buscar(ActionEvent e) {
        String textoBusqueda = vista.inputBusqueda.getText().trim();

        if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar decoración...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<DecoracionType> decoraciones = modelo.getAll();
            int index = 1;

            for (DecoracionType decoracion : decoraciones) {
                // Búsqueda en múltiples campos (case-insensitive)
                String descripcion = decoracion.getDescripcionDecoracion() != null
                        ? decoracion.getDescripcionDecoracion()
                        : "";
                String proveedor = decoracion.getIdProveedorDecoracion() != null ? decoracion.getIdProveedorDecoracion()
                        : "";
                String coleccion = decoracion.getIdColeccionDecoracion() != null ? decoracion.getIdColeccionDecoracion()
                        : "";
                String estado = decoracion.isEstadoDecoracion() ? "ACTIVO" : "INACTIVO";

                if (decoracion.getNombreDecoracion().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        descripcion.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        proveedor.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        coleccion.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        String.valueOf(decoracion.getStockDecoracion()).contains(textoBusqueda) ||
                        String.valueOf(decoracion.getStockMinimoDecoracion()).contains(textoBusqueda) ||
                        String.valueOf(decoracion.getStockMaximoDecoracion()).contains(textoBusqueda) ||
                        estado.toLowerCase().contains(textoBusqueda.toLowerCase())) {

                    Object[] fila = {
                            index++, // Mantener índice secuencial
                            decoracion.getNombreDecoracion(),
                            decoracion.getStockDecoracion(),
                            decoracion.getStockMinimoDecoracion(),
                            decoracion.getStockMaximoDecoracion(),
                            proveedor.isEmpty() ? "N/A" : proveedor,
                            coleccion.isEmpty() ? "N/A" : coleccion,
                            descripcion.isEmpty() ? "N/A" : descripcion,
                            estado
                    };
                    modeloTabla.addRow(fila);
                }
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al buscar decoraciones: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al buscar decoraciones: " + ex.getMessage(),
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
     * Abre el formulario para agregar una nueva decoración
     */
    private void abrirFormularioAgregar(ActionEvent e) {
        new FormularioAgregarDecoracionController(formularioAgregar);
        formularioAgregar.setVisible(true);

        cargarTabla();
    }

    /**
     * Abre el formulario para editar la decoración seleccionada
     */
    private void abrirFormularioEditar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una decoración para editar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real de la decoración desde el modelo usando el índice de la
        // fila
        try {
            ArrayList<DecoracionType> decoraciones = modelo.getAll();
            if (fila < decoraciones.size()) {
                String idDecoracion = decoraciones.get(fila).getIdDecoracion();

                new FormularioEditarDecoracionController(formularioEditar, idDecoracion);
                formularioEditar.setVisible(true);

                cargarTabla();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al obtener la decoración: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al obtener la decoración: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina la decoración seleccionada usando el Modelo
     */
    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una decoración para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real y nombre de la decoración desde el modelo usando el índice
        // de la fila
        try {
            ArrayList<DecoracionType> decoraciones = modelo.getAll();
            if (fila < decoraciones.size()) {
                DecoracionType decoracionSeleccionada = decoraciones.get(fila);
                String idDecoracion = decoracionSeleccionada.getIdDecoracion();
                String nombreDecoracion = decoracionSeleccionada.getNombreDecoracion();

                int confirmacion = JOptionPane.showConfirmDialog(
                        vista,
                        "¿Está seguro de que desea eliminar la decoración \"" + nombreDecoracion + "\"?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (modelo.delete(idDecoracion)) {
                        JOptionPane.showMessageDialog(vista, "Decoración eliminada correctamente",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarTabla();
                    } else {
                        JOptionPane.showMessageDialog(vista, "No se pudo eliminar la decoración",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al eliminar decoración: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al eliminar decoración: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
