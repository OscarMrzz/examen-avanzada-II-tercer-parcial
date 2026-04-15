package Controlador.clientes;

import Vista.clientes.FormularioEditarCliente;
import Modelo.clientes.ClienteModel;
import Type.clientes.ClienteType;
import Type.clientes.TipoCliente;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;

/**
 * Controlador para el formulario de editar cliente
 * 
 * @author ossca
 */
public class FormularioEditarClienteController {

    private static final Logger logger = Logger.getLogger(FormularioEditarClienteController.class.getName());
    private FormularioEditarCliente vista;
    private ClienteModel modelo;
    private String idCliente;

    public FormularioEditarClienteController(FormularioEditarCliente vista, String idCliente) {
        this.vista = vista;
        this.idCliente = idCliente;
        this.modelo = new ClienteModel();
        inicializarEventos();
        cargarCombos();
        cargarDatosCliente();
    }

    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::actualizar);

        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);
    }

    /**
     * Carga los ComboBox con los valores del enum TipoCliente
     */
    private void cargarCombos() {
        try {
            if (vista.comboBoxTipoCliente != null) {
                vista.comboBoxTipoCliente.removeAllItems();
                vista.comboBoxTipoCliente.addItem("Seleccionar...");

                for (TipoCliente tipo : TipoCliente.values()) {
                    vista.comboBoxTipoCliente.addItem(tipo.toString());
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar tipos de cliente: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar los tipos de cliente. Por favor, intente nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga los datos del cliente en el formulario usando el Modelo
     */
    private void cargarDatosCliente() {
        try {
            ClienteType cliente = modelo.getById(idCliente);
            if (cliente != null) {
                vista.inputNombreCliente.setText(cliente.getNombreCliente());
                vista.inputRTNCliente.setText(cliente.getRtnCliente() != null ? cliente.getRtnCliente() : "");
                vista.inputTelefonoCliente.setText(cliente.getTelefonoCliente());
                vista.inputEmailCliente.setText(cliente.getEmailCliente() != null ? cliente.getEmailCliente() : "");
                vista.inputDireccionCliente.setText(cliente.getDireccionCliente() != null ? cliente.getDireccionCliente() : "");
                
                // Cargar tipo de cliente
                if (vista.comboBoxTipoCliente != null && cliente.getTipoCliente() != null) {
                    vista.comboBoxTipoCliente.setSelectedItem(cliente.getTipoCliente().toString());
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Cliente no encontrado",
                        "Error", JOptionPane.ERROR_MESSAGE);
                vista.dispose();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar datos del cliente: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar datos del cliente: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            vista.dispose();
        }
    }

    /**
     * Actualiza los datos del cliente en la base de datos
     */
    private void actualizar(java.awt.event.ActionEvent e) {
        if (!validarCampos()) {
            return;
        }

        try {
            // Crear objeto ClienteType con los datos actualizados
            ClienteType clienteActualizado = new ClienteType();
            clienteActualizado.setIdCliente(idCliente);
            clienteActualizado.setNombreCliente(vista.inputNombreCliente.getText().trim());
            clienteActualizado.setRtnCliente(vista.inputRTNCliente.getText().trim());
            clienteActualizado.setTelefonoCliente(vista.inputTelefonoCliente.getText().trim());
            clienteActualizado.setEmailCliente(vista.inputEmailCliente.getText().trim());
            clienteActualizado.setDireccionCliente(vista.inputDireccionCliente.getText().trim());
            clienteActualizado.setEstadoCliente(true);

            // Obtener tipo de cliente del ComboBox
            String seleccion = (String) vista.comboBoxTipoCliente.getSelectedItem();
            if (seleccion != null && !seleccion.equals("Seleccionar...")) {
                clienteActualizado.setTipoCliente(TipoCliente.valueOf(seleccion));
            }

            // Actualizar en base de datos usando el modelo
            if (modelo.update(clienteActualizado)) {
                JOptionPane.showMessageDialog(vista, "Cliente actualizado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo actualizar el cliente",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al actualizar cliente: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al actualizar cliente. Por favor, intente nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cierra el formulario sin guardar cambios
     */
    private void cancelar(java.awt.event.ActionEvent e) {
        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de que desea cancelar? Los cambios no se guardarán.",
                "Confirmar Cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            vista.dispose();
        }
    }

    /**
     * Valida los campos del formulario antes de guardar
     */
    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        // Validar nombre
        String nombre = vista.inputNombreCliente.getText().trim();
        if (nombre.isEmpty()) {
            errores.append("- El nombre es obligatorio\n");
        } else if (nombre.length() < 3) {
            errores.append("- El nombre debe tener al menos 3 caracteres\n");
        }

        // Validar RTN (opcional para consumidor final)
        String rtn = vista.inputRTNCliente.getText().trim();
        String tipoSeleccionado = (String) vista.comboBoxTipoCliente.getSelectedItem();
        
        if (rtn != null && !rtn.isEmpty()) {
            // Validar formato de RTN hondureño (14 dígitos o formato con guiones)
            if (!Pattern.matches("^\\d{14}$|^\\d{4}-\\d{6}-\\d{4}$", rtn)) {
                errores.append("- El RTN debe tener 14 dígitos o formato XXXXXX-XXXXXX\n");
            }
        } else if (!"Consumidor Final".equals(tipoSeleccionado)) {
            errores.append("- El RTN es obligatorio para clientes que no sean consumidor final\n");
        }

        // Validar teléfono
        String telefono = vista.inputTelefonoCliente.getText().trim();
        if (telefono.isEmpty()) {
            errores.append("- El teléfono es obligatorio\n");
        } else if (!Pattern.matches("^\\d{8}$|^\\d{4}-\\d{4}$", telefono)) {
            errores.append("- El teléfono debe tener 8 dígitos o formato XXXX-XXXX\n");
        }

        // Validar email (opcional)
        String email = vista.inputEmailCliente.getText().trim();
        if (email != null && !email.isEmpty()) {
            if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", email)) {
                errores.append("- El formato del email no es válido\n");
            }
        }

        // Validar ComboBox de tipo
        if (tipoSeleccionado == null || tipoSeleccionado.equals("Seleccionar...")) {
            errores.append("- Debe seleccionar un tipo de cliente\n");
        }

        if (errores.length() > 0) {
            logger.log(Level.WARNING, "Errores de validación: {0}", errores.toString());
            JOptionPane.showMessageDialog(vista, "Corrija los siguientes errores:\n\n" + errores.toString(),
                    "Errores de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
