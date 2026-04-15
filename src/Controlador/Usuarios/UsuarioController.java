package Controlador.Usuarios;

import Vista.usuarios.UsuariosVista;
import Vista.usuarios.FormularioAgregarUsuario;
import Vista.usuarios.FormularioEditarUsuario;
import Modelo.usuarios.UsuarioModel;
import Type.usuarios.UsuarioType;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador principal para la vista de usuarios
 * 
 * @author ossca
 */
public class UsuarioController {

    private UsuariosVista vista;
    private UsuarioModel modelo;
    private FormularioAgregarUsuario formularioAgregar;
    private FormularioEditarUsuario formularioEditar;

    public UsuarioController(UsuariosVista vista, FormularioAgregarUsuario formularioAgregar,
            FormularioEditarUsuario formularioEditar) {
        this.vista = vista;
        this.formularioAgregar = formularioAgregar;
        this.formularioEditar = formularioEditar;
        this.modelo = new UsuarioModel();
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
                if (vista.inputBusqueda.getText().equals("Buscar usuario...")) {
                    vista.inputBusqueda.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (vista.inputBusqueda.getText().isEmpty()) {
                    vista.inputBusqueda.setText("Buscar usuario...");
                }
            }
        });

        // Inicializar el campo de búsqueda
        vista.inputBusqueda.setText("Buscar usuario...");
    }

    /**
     * Carga todos los usuarios en la tabla usando el Modelo
     */
    public void cargarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<UsuarioType> usuarios = modelo.getAll();
            int index = 1;

            for (UsuarioType usuario : usuarios) {
                Object[] fila = {
                        index++, // Número de registro/índice
                        usuario.getNombreUsuario(),
                        usuario.getPrivilegioUsuario().toString(),
                        usuario.isEstadoUsuario() ? "ACTIVO" : "INACTIVO"
                };
                modeloTabla.addRow(fila);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar los usuarios: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca usuarios según el texto ingresado
     */
    private void buscar(ActionEvent e) {
        String textoBusqueda = vista.inputBusqueda.getText().trim();

        if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar usuario...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<UsuarioType> usuarios = modelo.getAll();
            int index = 1;

            for (UsuarioType usuario : usuarios) {
                if (usuario.getNombreUsuario().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        usuario.getPrivilegioUsuario().toString().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        (usuario.isEstadoUsuario() ? "ACTIVO" : "INACTIVO").toLowerCase()
                                .contains(textoBusqueda.toLowerCase())) {

                    Object[] fila = {
                            index++, // Número de registro/índice
                            usuario.getNombreUsuario(),
                            usuario.getPrivilegioUsuario().toString(),
                            usuario.isEstadoUsuario() ? "ACTIVO" : "INACTIVO"
                    };
                    modeloTabla.addRow(fila);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar usuarios: " + ex.getMessage(),
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
     * Abre el formulario para agregar un nuevo usuario
     */
    private void abrirFormularioAgregar(ActionEvent e) {
        new FormularioAgregarUsuarioController(formularioAgregar);
        formularioAgregar.setVisible(true);

        // Recargar la tabla después de agregar
        cargarTabla();
    }

    /**
     * Abre el formulario para editar el usuario seleccionado
     */
    private void abrirFormularioEditar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un usuario para editar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real del usuario desde el modelo usando el índice de la fila
        try {
            ArrayList<UsuarioType> usuarios = modelo.getAll();
            if (fila < usuarios.size()) {
                String idUsuario = usuarios.get(fila).getIdUsuario();

                new FormularioEditarUsuarioController(formularioEditar, idUsuario);
                formularioEditar.setVisible(true);

                // Recargar la tabla después de editar
                cargarTabla();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al obtener el usuario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina el usuario seleccionado usando el Modelo
     */
    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor seleccione un usuario para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID real y nombre del usuario desde el modelo usando el índice de
        // la fila
        try {
            ArrayList<UsuarioType> usuarios = modelo.getAll();
            if (fila < usuarios.size()) {
                UsuarioType usuarioSeleccionado = usuarios.get(fila);
                String idUsuario = usuarioSeleccionado.getIdUsuario();
                String nombreUsuario = usuarioSeleccionado.getNombreUsuario();

                int confirmacion = JOptionPane.showConfirmDialog(
                        vista,
                        "¿Está seguro de que desea eliminar al usuario \"" + nombreUsuario + "\"?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (modelo.delete(idUsuario)) {
                        JOptionPane.showMessageDialog(vista, "Usuario eliminado correctamente",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarTabla();
                    } else {
                        JOptionPane.showMessageDialog(vista, "No se pudo eliminar el usuario",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al obtener el usuario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
