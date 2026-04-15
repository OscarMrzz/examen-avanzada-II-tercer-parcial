package Controlador.Usuarios;

import Vista.usuarios.FormularioAgregarUsuario;
import Modelo.usuarios.UsuarioModel;
import Type.usuarios.UsuarioType;
import Type.usuarios.PrivilegioUsuario;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Controlador para el formulario de agregar usuario
 * 
 * @author ossca
 */
public class FormularioAgregarUsuarioController {

    private static final Logger logger = Logger.getLogger(FormularioAgregarUsuarioController.class.getName());
    private FormularioAgregarUsuario vista;
    private UsuarioModel modelo;
    private String rutaImagen = "";

    public FormularioAgregarUsuarioController(FormularioAgregarUsuario vista) {
        this.vista = vista;
        this.modelo = new UsuarioModel();
        inicializarEventos();
        cargarCombos();
    }

    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::guardar);

        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);

        // Botón Cargar Imagen
        vista.botonAgregarImagen.addActionListener(this::cargarImagen);
    }

    /**
     * Carga los ComboBox con los valores del enum PrivilegioUsuario
     */
    private void cargarCombos() {
        try {
            vista.comboBoxRol.removeAllItems();
            vista.comboBoxRol.addItem("Seleccionar...");

            for (PrivilegioUsuario privilegio : PrivilegioUsuario.values()) {
                vista.comboBoxRol.addItem(privilegio.toString());
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar roles: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar los roles. Por favor, intente nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Guarda un nuevo usuario en la base de datos
     */
    private void guardar(ActionEvent e) {
        if (!validarCampos()) {
            return;
        }

        try {
            // Crear objeto UsuarioType con los datos del formulario
            UsuarioType nuevoUsuario = new UsuarioType();
            nuevoUsuario.setIdUsuario(UUID.randomUUID().toString());
            nuevoUsuario.setNombreUsuario(vista.inputNombre.getText().trim());
            nuevoUsuario.setUserUsuario(vista.inputNombre.getText().trim()); // Usar nombre como usuario
            nuevoUsuario.setPassUsuario(vista.inputPassword.getText().trim());
            nuevoUsuario.setFotoUsuario(rutaImagen);
            nuevoUsuario.setEstadoUsuario(true);

            // Obtener privilegio del ComboBox
            String seleccion = (String) vista.comboBoxRol.getSelectedItem();
            if (seleccion != null && !seleccion.equals("Seleccionar...")) {
                nuevoUsuario.setPrivilegioUsuario(PrivilegioUsuario.valueOf(seleccion));
            }

            // Guardar en base de datos usando el modelo
            if (modelo.create(nuevoUsuario)) {
                JOptionPane.showMessageDialog(vista, "Usuario guardado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar el usuario",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al guardar usuario: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al guardar usuario. Por favor, intente nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cierra el formulario sin guardar cambios
     */
    private void cancelar(ActionEvent e) {
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
     * Carga una imagen desde el sistema de archivos
     * - La guarda en src/img/
     * - Actualiza el label de vista previa
     * - Almacena la ruta para guardar en BD
     */
    private void cargarImagen(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Archivos de imagen", "jpg", "jpeg", "png", "gif"));

        int resultado = fileChooser.showOpenDialog(vista);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();

            // Validar que el archivo exista y no sea muy grande
            if (!archivoSeleccionado.exists() || archivoSeleccionado.length() > 5 * 1024 * 1024) {
                JOptionPane.showMessageDialog(vista,
                        "El archivo seleccionado no es válido o es demasiado grande (máximo 5MB)",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Crear directorio img si no existe
                File directorioImg = new File("src/img");
                if (!directorioImg.exists()) {
                    boolean creado = directorioImg.mkdirs();
                    if (!creado) {
                        throw new Exception("No se pudo crear el directorio de imágenes");
                    }
                    logger.log(Level.INFO, "Directorio img creado exitosamente");
                }

                // Generar nombre único para la imagen
                String extension = getFileExtension(archivoSeleccionado.getName());
                if (extension.isEmpty()) {
                    throw new Exception("El archivo no tiene una extensión válida");
                }

                String nombreImagen = "img_usuario_" + System.currentTimeMillis() + "." + extension;
                File destinoImagen = new File(directorioImg, nombreImagen);

                // Copiar archivo al directorio img
                Files.copy(archivoSeleccionado.toPath(), destinoImagen.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

                logger.log(Level.INFO, "Imagen copiada exitosamente a: {0}", destinoImagen.getPath());

                // Actualizar ruta y vista previa
                this.rutaImagen = "src/img/" + nombreImagen;

                if (vista.labelImagem != null) {
                    // Escalar imagen para vista previa
                    ImageIcon icono = new ImageIcon(destinoImagen.getPath());
                    if (icono.getIconWidth() > 0 && icono.getIconHeight() > 0) {
                        Image imagenEscalada = icono.getImage().getScaledInstance(
                                vista.labelImagem.getWidth() > 0 ? vista.labelImagem.getWidth() : 136,
                                vista.labelImagem.getHeight() > 0 ? vista.labelImagem.getHeight() : 127,
                                Image.SCALE_SMOOTH);
                        vista.labelImagem.setIcon(new ImageIcon(imagenEscalada));
                    } else {
                        logger.log(Level.WARNING, "La imagen cargada no es válida");
                        vista.labelImagem.setIcon(null);
                    }
                }

                JOptionPane.showMessageDialog(vista, "Imagen cargada correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error al cargar imagen: " + ex.getMessage(), ex);
                this.rutaImagen = ""; // Limpiar ruta en caso de error
                JOptionPane.showMessageDialog(vista, "Error al cargar la imagen. Por favor, intente nuevamente.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Obtiene la extensión de un archivo
     */
    private String getFileExtension(String nombreArchivo) {
        int puntoIndex = nombreArchivo.lastIndexOf('.');
        if (puntoIndex == -1) {
            return "";
        }
        return nombreArchivo.substring(puntoIndex + 1).toLowerCase();
    }

    /**
     * Valida los campos del formulario antes de guardar
     */
    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        // Validar nombre
        String nombre = vista.inputNombre.getText().trim();
        if (nombre.isEmpty()) {
            errores.append("- El nombre es obligatorio\n");
        } else if (nombre.length() < 3) {
            errores.append("- El nombre debe tener al menos 3 caracteres\n");
        } else if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            errores.append("- El nombre solo puede contener letras y espacios\n");
        }

        // Validar contraseña
        String password = vista.inputPassword.getText().trim();
        if (password.isEmpty()) {
            errores.append("- La contraseña es obligatoria\n");
        } else if (password.length() < 6) {
            errores.append("- La contraseña debe tener al menos 6 caracteres\n");
        } else if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d).+$")) {
            errores.append("- La contraseña debe contener al menos una letra y un número\n");
        }

        // Validar ComboBox de rol
        String seleccion = (String) vista.comboBoxRol.getSelectedItem();
        if (seleccion == null || seleccion.equals("Seleccionar...")) {
            errores.append("- Debe seleccionar un rol\n");
        }

        if (errores.length() > 0) {
            logger.log(Level.WARNING, "Errores de validación: {0}", errores.toString());
            JOptionPane.showMessageDialog(vista, "Corrija los siguientes errores:\n\n" + errores.toString(),
                    "Errores de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Limpia todos los campos del formulario
     */
    private void limpiarCampos() {
        vista.inputNombre.setText("");
        vista.inputPassword.setText("");
        vista.comboBoxRol.setSelectedIndex(0);

        if (vista.labelImagem != null) {
            vista.labelImagem.setIcon(null);
        }

        rutaImagen = "";
    }
}
