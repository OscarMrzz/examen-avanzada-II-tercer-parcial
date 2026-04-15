package Controlador.colecciones;

import Vista.colecciones.coleccionesVista;
import Vista.colecciones.FormularioAgregarColeccion;
import Vista.colecciones.FormularioEditarColeccion;
import Modelo.colecciones.ColeccionModel;
import Type.colecciones.ColeccionType;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador principal para la vista de colecciones
 * 
 * @author ossca
 */
public class coleccionesVistaController {

    private static final Logger logger = Logger.getLogger(coleccionesVistaController.class.getName());
    private coleccionesVista vista;
    private ColeccionModel modelo;
    private FormularioAgregarColeccion formularioAgregar;
    private FormularioEditarColeccion formularioEditar;

    public coleccionesVistaController(coleccionesVista vista, FormularioAgregarColeccion formularioAgregar, FormularioEditarColeccion formularioEditar) {
        this.vista = vista;
        this.formularioAgregar = formularioAgregar;
        this.formularioEditar = formularioEditar;
        this.modelo = new ColeccionModel();
        inicializarEventos();
        cargarTabla();
    }

    private void inicializarEventos() {
        // Evento del botón buscar
        vista.botonBuscar.addActionListener(this::buscar);

        // Evento del botón agregar
        vista.botonAgregar.addActionListener(this::abrirFormularioAgregar);

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
                if (vista.inputBusqueda.getText().equals("Buscar colección...")) {
                    vista.inputBusqueda.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().isEmpty()) {
                    vista.inputBusqueda.setText("Buscar colección...");
                }
            }
        });

        // Inicializar el campo de búsqueda
        vista.inputBusqueda.setText("Buscar colección...");
    }

    /**
     * Carga todas las colecciones en la tabla usando el Modelo
     */
    public void cargarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<ColeccionType> colecciones = modelo.getAll();
            int index = 1;

            for (ColeccionType coleccion : colecciones) {
                Object[] fila = {
                        index++, // Número de registro/índice
                        coleccion.getNombreColeccion(),
                        coleccion.getDisenadorColeccion(),
                        coleccion.getNumColeccionColeccion(),
                        coleccion.getAnioColeccion(),
                        coleccion.getDescripcionColeccion() != null ? coleccion.getDescripcionColeccion() : "N/A",
                        coleccion.isEstadoColeccion() ? "ACTIVO" : "INACTIVO"
                };
                modeloTabla.addRow(fila);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar las colecciones: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar las colecciones: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca colecciones según el texto ingresado
     */
    private void buscar(ActionEvent e) {
        String textoBusqueda = vista.inputBusqueda.getText().trim();

        if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar colección...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<ColeccionType> colecciones = modelo.getAll();
            int index = 1;

            for (ColeccionType coleccion : colecciones) {
                // Búsqueda en múltiples campos (case-insensitive)
                String descripcion = coleccion.getDescripcionColeccion() != null ? coleccion.getDescripcionColeccion()
                        : "";
                String disenador = coleccion.getDisenadorColeccion() != null ? coleccion.getDisenadorColeccion() : "";
                String estado = coleccion.isEstadoColeccion() ? "ACTIVO" : "INACTIVO";

                if (coleccion.getNombreColeccion().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        disenador.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        coleccion.getNumColeccionColeccion().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        descripcion.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        String.valueOf(coleccion.getAnioColeccion()).contains(textoBusqueda) ||
                        estado.toLowerCase().contains(textoBusqueda.toLowerCase())) {

                    Object[] fila = {
                            index++, // Mantener índice secuencial
                            coleccion.getNombreColeccion(),
                            disenador.isEmpty() ? "N/A" : disenador,
                            coleccion.getNumColeccionColeccion(),
                            coleccion.getAnioColeccion(),
                            descripcion.isEmpty() ? "N/A" : descripcion,
                            estado
                    };
                    modeloTabla.addRow(fila);
                }
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al buscar colecciones: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al buscar colecciones: " + ex.getMessage(),
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
     * Abre el formulario para agregar una nueva colección
     */
    private void abrirFormularioAgregar(ActionEvent e) {
        new FormularioAgregarColeccionController(formularioAgregar);
        formularioAgregar.setVisible(true);

        cargarTabla();
    }

    /**
     * Abre el formulario para editar la colección seleccionada
     */
    private void abrirFormularioEditar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una colección para editar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real de la colección desde el modelo usando el índice de la
        // fila
        try {
            ArrayList<ColeccionType> colecciones = modelo.getAll();
            if (fila < colecciones.size()) {
                String idColeccion = colecciones.get(fila).getIdColeccion();

                new FormularioEditarColeccionController(formularioEditar, idColeccion);
                formularioEditar.setVisible(true);

                cargarTabla();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al obtener la colección: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al obtener la colección: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina la colección seleccionada usando el Modelo
     */
    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una colección para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real y nombre de la colección desde el modelo usando el índice
        // de la fila
        try {
            ArrayList<ColeccionType> colecciones = modelo.getAll();
            if (fila < colecciones.size()) {
                ColeccionType coleccionSeleccionada = colecciones.get(fila);
                String idColeccion = coleccionSeleccionada.getIdColeccion();
                String nombreColeccion = coleccionSeleccionada.getNombreColeccion();

                int confirmacion = JOptionPane.showConfirmDialog(
                        vista,
                        "¿Está seguro de que desea eliminar la colección \"" + nombreColeccion + "\"?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (modelo.delete(idColeccion)) {
                        JOptionPane.showMessageDialog(vista, "Colección eliminada correctamente",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarTabla();
                    } else {
                        JOptionPane.showMessageDialog(vista, "No se pudo eliminar la colección",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al eliminar colección: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al eliminar colección: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
