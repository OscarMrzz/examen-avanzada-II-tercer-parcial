package Controlador.clientes;

import Vista.clientes.clientesVista;
import Vista.clientes.FormularioAgregarCliente;
import Vista.clientes.FormularioEditarCliente;
import Modelo.clientes.ClienteModel;
import Type.clientes.ClienteType;
import Type.clientes.TipoCliente;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador principal para la vista de clientes
 * 
 * @author ossca
 */
public class clientesVistaController {

    private static final Logger logger = Logger.getLogger(clientesVistaController.class.getName());
    private clientesVista vista;
    private ClienteModel modelo;
    private FormularioAgregarCliente formularioAgregar;
    private FormularioEditarCliente formularioEditar;

    public clientesVistaController(clientesVista vista, FormularioAgregarCliente formularioAgregar, FormularioEditarCliente formularioEditar) {
        this.vista = vista;
        this.formularioAgregar = formularioAgregar;
        this.formularioEditar = formularioEditar;
        this.modelo = new ClienteModel();
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
                if (vista.inputBusqueda.getText().equals("Buscar cliente...")) {
                    vista.inputBusqueda.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().isEmpty()) {
                    vista.inputBusqueda.setText("Buscar cliente...");
                }
            }
        });

        // Inicializar el campo de búsqueda
        vista.inputBusqueda.setText("Buscar cliente...");
    }

    /**
     * Carga todos los clientes en la tabla usando el Modelo
     */
    public void cargarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<ClienteType> clientes = modelo.getAll();
            int index = 1;

            for (ClienteType cliente : clientes) {
                Object[] fila = {
                        index++, // Número de registro/índice
                        cliente.getNombreCliente(),
                        cliente.getRtnCliente() != null ? cliente.getRtnCliente() : "N/A",
                        cliente.getTipoCliente().toString(),
                        cliente.getTelefonoCliente(),
                        cliente.getEmailCliente() != null ? cliente.getEmailCliente() : "N/A",
                        cliente.isEstadoCliente() ? "ACTIVO" : "INACTIVO"
                };
                modeloTabla.addRow(fila);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar los clientes: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar los clientes: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca clientes según el texto ingresado
     */
    private void buscar(ActionEvent e) {
        String textoBusqueda = vista.inputBusqueda.getText().trim();

        if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar cliente...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<ClienteType> clientes = modelo.getAll();
            int index = 1;

            for (ClienteType cliente : clientes) {
                // Búsqueda en múltiples campos (case-insensitive)
                String rtn = cliente.getRtnCliente() != null ? cliente.getRtnCliente() : "";
                String email = cliente.getEmailCliente() != null ? cliente.getEmailCliente() : "";
                String estado = cliente.isEstadoCliente() ? "ACTIVO" : "INACTIVO";

                if (cliente.getNombreCliente().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        rtn.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        cliente.getTipoCliente().toString().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        cliente.getTelefonoCliente().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        email.toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        estado.toLowerCase().contains(textoBusqueda.toLowerCase())) {

                    Object[] fila = {
                            index++, // Mantener índice secuencial
                            cliente.getNombreCliente(),
                            rtn.isEmpty() ? "N/A" : rtn,
                            cliente.getTipoCliente().toString(),
                            cliente.getTelefonoCliente(),
                            email.isEmpty() ? "N/A" : email,
                            estado
                    };
                    modeloTabla.addRow(fila);
                }
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al buscar clientes: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al buscar clientes: " + ex.getMessage(),
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
     * Abre el formulario para agregar un nuevo cliente
     */
    private void abrirFormularioAgregar(ActionEvent e) {
        new FormularioAgregarClienteController(formularioAgregar);
        formularioAgregar.setVisible(true);

        cargarTabla();
    }

    /**
     * Abre el formulario para editar el cliente seleccionado
     */
    private void abrirFormularioEditar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un cliente para editar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real del cliente desde el modelo usando el índice de la fila
        try {
            ArrayList<ClienteType> clientes = modelo.getAll();
            if (fila < clientes.size()) {
                String idCliente = clientes.get(fila).getIdCliente();

                new FormularioEditarClienteController(formularioEditar, idCliente);
                formularioEditar.setVisible(true);

                cargarTabla();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al obtener el cliente: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al obtener el cliente: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina el cliente seleccionado usando el Modelo
     */
    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un cliente para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real y nombre del cliente desde el modelo usando el índice de
        // la fila
        try {
            ArrayList<ClienteType> clientes = modelo.getAll();
            if (fila < clientes.size()) {
                ClienteType clienteSeleccionado = clientes.get(fila);
                String idCliente = clienteSeleccionado.getIdCliente();
                String nombreCliente = clienteSeleccionado.getNombreCliente();

                int confirmacion = JOptionPane.showConfirmDialog(
                        vista,
                        "¿Está seguro de que desea eliminar al cliente \"" + nombreCliente + "\"?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (modelo.delete(idCliente)) {
                        JOptionPane.showMessageDialog(vista, "Cliente eliminado correctamente",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarTabla();
                    } else {
                        JOptionPane.showMessageDialog(vista, "No se pudo eliminar el cliente",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al eliminar cliente: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al eliminar cliente: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
