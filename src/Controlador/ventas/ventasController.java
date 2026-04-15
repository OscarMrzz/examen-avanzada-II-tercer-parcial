package Controlador.ventas;

import Vista.ventas.ventasVista;
import Vista.ventas.FormularioAgregarVenta;
import Vista.ventas.FormularioEditarVenta;
import Vista.ventas.reportesVentas;
import Modelo.ventas.VentaModel;
import Type.ventas.VentaType;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador para el módulo de ventas
 * 
 * @author ossca
 */
public class ventasController {

    private ventasVista vista;
    private FormularioAgregarVenta formularioAgregar;
    private FormularioEditarVenta formularioEditar;
    private reportesVentas reportes;
    private VentaModel modelo;

    public ventasController(ventasVista vista, FormularioAgregarVenta formularioAgregar,
            FormularioEditarVenta formularioEditar, reportesVentas reportes) {
        this.vista = vista;
        this.formularioAgregar = formularioAgregar;
        this.formularioEditar = formularioEditar;
        this.reportes = reportes;
        this.modelo = new VentaModel();

        initController();
    }

    private void initController() {
        // Botón agregar
        vista.botonAgregar.addActionListener(e -> abrirFormularioAgregar());

        // Botón buscar
        vista.botonBuscar.addActionListener(e -> buscarVentas());

        // Botón informe
        vista.botonInforme.addActionListener(e -> abrirReportes());

        // Menú contextual en la tabla
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

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    abrirFormularioEditar();
                }
            }
        });

        // Botones de los formularios
        formularioAgregar.botonGuardar.addActionListener(e -> guardarVenta());
        formularioAgregar.botonCancelar.addActionListener(e -> formularioAgregar.setVisible(false));

        formularioEditar.botonGuardar.addActionListener(e -> actualizarVenta());
        formularioEditar.botonCancelar.addActionListener(e -> formularioEditar.setVisible(false));

        // Botones de reportes
        reportes.botonGenerar.addActionListener(e -> generarReporte());
        reportes.botonCancelar.addActionListener(e -> reportes.setVisible(false));
    }

    public void iniciar() {
        vista.setVisible(true);
        cargarTabla();
    }

    /**
     * Carga todas las ventas en la tabla
     */
    public void cargarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<VentaType> ventas = modelo.getAll();
            int index = 1;

            for (VentaType venta : ventas) {
                Object[] fila = {
                        index++,
                        venta.getNumeroFacturaVenta(),
                        venta.getFechaVenta(),
                        venta.getTotalVenta(),
                        venta.getTipoPagoVenta() != null ? venta.getTipoPagoVenta().toString() : "N/A",
                        venta.getEstadoVenta() != null ? venta.getEstadoVenta().toString() : "N/A"
                };
                modeloTabla.addRow(fila);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar las ventas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca ventas según el texto ingresado
     */
    private void buscarVentas() {
        String textoBusqueda = vista.inputBusqueda.getText().trim();

        if (textoBusqueda.isEmpty()) {
            cargarTabla();
            return;
        }

        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<VentaType> ventas = modelo.getAll();
            int index = 1;

            for (VentaType venta : ventas) {
                String numeroFactura = venta.getNumeroFacturaVenta() != null ? venta.getNumeroFacturaVenta() : "";
                
                if (numeroFactura.toLowerCase().contains(textoBusqueda.toLowerCase())) {
                    Object[] fila = {
                            index++,
                            venta.getNumeroFacturaVenta(),
                            venta.getFechaVenta(),
                            venta.getTotalVenta(),
                            venta.getTipoPagoVenta() != null ? venta.getTipoPagoVenta().toString() : "N/A",
                            venta.getEstadoVenta() != null ? venta.getEstadoVenta().toString() : "N/A"
                    };
                    modeloTabla.addRow(fila);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar ventas: " + ex.getMessage(),
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
            menuItemEditar.setIcon(new javax.swing.ImageIcon("src/img/edit.png"));

            javax.swing.JMenuItem menuItemEliminar = new javax.swing.JMenuItem("Eliminar");
            menuItemEliminar.addActionListener(evt -> eliminarVenta());
            menuItemEliminar.setIcon(new javax.swing.ImageIcon("src/img/delete.png"));

            menu.add(menuItemEditar);
            menu.add(menuItemEliminar);

            menu.show(vista.tabla, e.getX(), e.getY());
        }
    }

    /**
     * Abre el formulario para agregar una nueva venta
     */
    private void abrirFormularioAgregar() {
        formularioAgregar.setVisible(true);
        cargarTabla();
    }

    /**
     * Abre el formulario para editar la venta seleccionada
     */
    private void abrirFormularioEditar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una venta para editar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ArrayList<VentaType> ventas = modelo.getAll();
            if (fila < ventas.size()) {
                String idVenta = ventas.get(fila).getIdVenta();
                
                // Aquí se cargarian los datos en el formulario de editar
                formularioEditar.setVisible(true);
                cargarTabla();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al obtener la venta: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Guarda una nueva venta
     */
    private void guardarVenta() {
        // Validar campos del formulario
        if (validarFormularioAgregar()) {
            // Aquí iría la lógica de guardado
            System.out.println("Guardando venta...");
            formularioAgregar.setVisible(false);
            cargarTabla();
        }
    }

    /**
     * Actualiza una venta existente
     */
    private void actualizarVenta() {
        // Validar campos del formulario
        if (validarFormularioEditar()) {
            // Aquí iría la lógica de actualización
            System.out.println("Actualizando venta...");
            formularioEditar.setVisible(false);
            cargarTabla();
        }
    }

    /**
     * Elimina la venta seleccionada
     */
    private void eliminarVenta() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione una venta para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de que desea eliminar esta venta?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                ArrayList<VentaType> ventas = modelo.getAll();
                if (fila < ventas.size()) {
                    String idVenta = ventas.get(fila).getIdVenta();
                    
                    // Aquí iría la lógica de eliminación
                    System.out.println("Eliminando venta: " + idVenta);
                    cargarTabla();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error al eliminar la venta: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Abre la ventana de reportes
     */
    private void abrirReportes() {
        reportes.setVisible(true);
    }

    /**
     * Genera un reporte
     */
    private void generarReporte() {
        String tipoReporte = (String) reportes.comboBoxTipoReporte.getSelectedItem();
        System.out.println("Generando reporte: " + tipoReporte);
    }

    /**
     * Valida el formulario de agregar
     */
    private boolean validarFormularioAgregar() {
        return true; // Placeholder - implementar validaciones según necesidad
    }

    /**
     * Valida el formulario de editar
     */
    private boolean validarFormularioEditar() {
        return true; // Placeholder - implementar validaciones según necesidad
    }

    public void volverAlHome() {
        vista.setVisible(false);
    }
}
