package Controlador.decoraciones;

import Vista.decoraciones.FormularioAgregarDecoracion;
import Modelo.decoraciones.DecoracionModel;
import Modelo.colecciones.ColeccionModel;
import Type.decoraciones.DecoracionType;
import Type.colecciones.ColeccionType;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Controlador para el formulario de agregar decoración
 * 
 * @author ossca
 */
public class FormularioAgregarDecoracionController {

    private static final Logger logger = Logger.getLogger(FormularioAgregarDecoracionController.class.getName());
    private FormularioAgregarDecoracion vista;
    private DecoracionModel decoracionModel;
    private ColeccionModel coleccionModel;
    private String rutaImagenActual;

    public FormularioAgregarDecoracionController(FormularioAgregarDecoracion vista) {
        this.vista = vista;
        this.decoracionModel = new DecoracionModel();
        this.coleccionModel = new ColeccionModel();
        inicializarEventos();
        cargarCombos();
    }

    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::guardar);

        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);

        // Botón Agregar Imagen
        vista.botonBuscarImagen.addActionListener(this::agregarImagen);
    }

    /**
     * Carga los ComboBox con datos de proveedores y colecciones
     */
    private void cargarCombos() {
        try {
            // Cargar proveedores
            if (vista.comboBoxProveedor != null) {
                vista.comboBoxProveedor.removeAllItems();
                vista.comboBoxProveedor.addItem("Seleccionar...");

                // Aquí deberías cargar los proveedores desde su modelo
                // Por ahora, dejamos el placeholder
            }

            // Cargar colecciones
            if (vista.comboBoxColeccion != null) {
                vista.comboBoxColeccion.removeAllItems();
                vista.comboBoxColeccion.addItem("Seleccionar...");

                try {
                    // Cargar colecciones activas desde el modelo
                    java.util.List<ColeccionType> colecciones = coleccionModel.getAll();
                    if (colecciones != null && !colecciones.isEmpty()) {
                        for (ColeccionType coleccion : colecciones) {
                            if (coleccion.isEstadoColeccion()) {
                                // Agregar formato: ID - Nombre
                                vista.comboBoxColeccion.addItem(
                                        coleccion.getIdColeccion() + " - " + coleccion.getNombreColeccion());
                            }
                        }
                        logger.log(Level.INFO, "Cargadas " + colecciones.size() + " colecciones");
                    } else {
                        logger.log(Level.WARNING, "No se encontraron colecciones activas");
                    }
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Error al cargar colecciones: " + ex.getMessage(), ex);
                    JOptionPane.showMessageDialog(vista,
                            "Error al cargar las colecciones. Por favor, intente nuevamente.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar combos: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar los combos. Por favor, intente nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Agrega una imagen para la decoración con manejo completo
     */
    private void agregarImagen(java.awt.event.ActionEvent e) {
        try {
            // Crear selector de archivos
            JFileChooser fileChooser = new JFileChooser();

            // Configurar filtro para archivos de imagen
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Archivos de Imagen (*.jpg, *.jpeg, *.png, *.gif)",
                    "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);

            // Establecer directorio inicial
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

            // Mostrar diálogo de selección
            int resultado = fileChooser.showOpenDialog(vista);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivoSeleccionado = fileChooser.getSelectedFile();

                // Validar archivo
                if (!validarArchivoImagen(archivoSeleccionado)) {
                    return;
                }

                // Crear directorio de imágenes si no existe
                String directorioImagenes = "src/img/decoraciones/";
                Path directorioPath = Paths.get(directorioImagenes);

                if (!Files.exists(directorioPath)) {
                    Files.createDirectories(directorioPath);
                    logger.log(Level.INFO, "Directorio de imágenes creado: " + directorioImagenes);
                }

                // Generar nombre único para la imagen
                String extension = getFileExtension(archivoSeleccionado.getName());
                String nombreArchivo = UUID.randomUUID().toString() + extension.toLowerCase();
                String rutaCompleta = directorioImagenes + nombreArchivo;

                // Copiar archivo al directorio de la aplicación
                Path destino = Paths.get(rutaCompleta);
                Files.copy(archivoSeleccionado.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

                // Guardar ruta de la imagen
                rutaImagenActual = rutaCompleta;

                // Actualizar interfaz con la ruta de la imagen
                if (vista.inputImagenDecoracion != null) {
                    vista.inputImagenDecoracion.setText(rutaCompleta);
                }

                JOptionPane.showMessageDialog(vista,
                        "Imagen cargada exitosamente:\n" + nombreArchivo,
                        "Imagen Agregada", JOptionPane.INFORMATION_MESSAGE);

                logger.log(Level.INFO, "Imagen guardada: " + rutaCompleta);
            }

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error de E/S al procesar imagen: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista,
                    "Error al guardar la imagen: " + ex.getMessage(),
                    "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al agregar imagen: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista,
                    "Error al procesar la imagen: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Valida que el archivo sea una imagen válida
     */
    private boolean validarArchivoImagen(File archivo) {
        // Validar que el archivo exista
        if (archivo == null || !archivo.exists()) {
            JOptionPane.showMessageDialog(vista, "El archivo seleccionado no existe",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar extensión
        String nombreArchivo = archivo.getName().toLowerCase();
        if (!nombreArchivo.endsWith(".jpg") && !nombreArchivo.endsWith(".jpeg") &&
                !nombreArchivo.endsWith(".png") && !nombreArchivo.endsWith(".gif")) {
            JOptionPane.showMessageDialog(vista,
                    "El archivo debe ser una imagen válida (JPG, PNG, GIF)",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar tamaño (máximo 5MB)
        long tamanoBytes = archivo.length();
        long tamanoMB = tamanoBytes / (1024 * 1024);

        if (tamanoMB > 5) {
            JOptionPane.showMessageDialog(vista,
                    "El archivo es demasiado grande. Máximo permitido: 5MB\n" +
                            "Tamaño actual: " + tamanoMB + "MB",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Obtiene la extensión de un archivo
     */
    private String getFileExtension(String nombreArchivo) {
        int ultimoPunto = nombreArchivo.lastIndexOf('.');
        if (ultimoPunto == -1) {
            return "";
        }
        return nombreArchivo.substring(ultimoPunto);
    }

    /**
     * Elimina una imagen del sistema de archivos
     */
    private void eliminarImagen(String rutaImagen) {
        try {
            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                Path archivoImagen = Paths.get(rutaImagen);
                if (Files.exists(archivoImagen)) {
                    Files.delete(archivoImagen);
                    logger.log(Level.INFO, "Imagen eliminada: " + rutaImagen);
                }
            }
        } catch (IOException ex) {
            logger.log(Level.WARNING, "No se pudo eliminar la imagen: " + rutaImagen + " - " + ex.getMessage());
        }
    }

    /**
     * Guarda una nueva decoración en la base de datos
     */
    private void guardar(java.awt.event.ActionEvent e) {
        if (!validarCampos()) {
            return;
        }

        try {
            // Crear objeto DecoracionType con los datos del formulario
            DecoracionType nuevaDecoracion = new DecoracionType();
            nuevaDecoracion.setIdDecoracion(UUID.randomUUID().toString());
            nuevaDecoracion.setNombreDecoracion(vista.inputNombreDecoracion.getText().trim());
            nuevaDecoracion.setDescripcionDecoracion(vista.inputDescripcionDecoracion.getText().trim());
            nuevaDecoracion.setImagenDecoracion(rutaImagenActual);
            nuevaDecoracion.setEstadoDecoracion(true);

            // Validar y convertir stock
            String stockStr = vista.inputStockDecoracion.getText().trim();
            try {
                int stock = Integer.parseInt(stockStr);
                if (stock < 0) {
                    JOptionPane.showMessageDialog(vista, "El stock no puede ser negativo",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    vista.inputStockDecoracion.requestFocus();
                    return;
                }
                nuevaDecoracion.setStockDecoracion(stock);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El stock debe ser un número válido",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputStockDecoracion.requestFocus();
                return;
            }

            // Validar y convertir stock mínimo
            String stockMinStr = vista.inputStockMinimoDecoracion.getText().trim();
            try {
                int stockMin = Integer.parseInt(stockMinStr);
                if (stockMin < 0) {
                    JOptionPane.showMessageDialog(vista, "El stock mínimo no puede ser negativo",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    vista.inputStockMinimoDecoracion.requestFocus();
                    return;
                }
                nuevaDecoracion.setStockMinimoDecoracion(stockMin);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El stock mínimo debe ser un número válido",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputStockMinimoDecoracion.requestFocus();
                return;
            }

            // Validar y convertir stock máximo
            String stockMaxStr = vista.inputStockMaximoDecoracion.getText().trim();
            try {
                int stockMax = Integer.parseInt(stockMaxStr);
                if (stockMax < 0) {
                    JOptionPane.showMessageDialog(vista, "El stock máximo no puede ser negativo",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    vista.inputStockMaximoDecoracion.requestFocus();
                    return;
                }
                nuevaDecoracion.setStockMaximoDecoracion(stockMax);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El stock máximo debe ser un número válido",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputStockMaximoDecoracion.requestFocus();
                return;
            }

            // Obtener colección del ComboBox
            String coleccionSeleccionada = (String) vista.comboBoxColeccion.getSelectedItem();
            if (coleccionSeleccionada != null && !coleccionSeleccionada.equals("Seleccionar...")) {
                // Extraer ID de la colección (formato: ID - Nombre)
                String idColeccion = coleccionSeleccionada.split(" - ")[0];
                nuevaDecoracion.setIdColeccionDecoracion(idColeccion);
                
                // Validar que la colección exista y esté activa
                ColeccionType coleccionValida = coleccionModel.getById(idColeccion);
                if (coleccionValida == null || !coleccionValida.isEstadoColeccion()) {
                    JOptionPane.showMessageDialog(vista, "La colección seleccionada no es válida",
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Guardar en base de datos usando el modelo
            if (decoracionModel.create(nuevaDecoracion)) {
                JOptionPane.showMessageDialog(vista, "Decoración guardada correctamente" + 
                        (nuevaDecoracion.getIdColeccionDecoracion() != null ? 
                        "\nAsociada a la colección: " + coleccionSeleccionada : ""),
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar la decoración",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al guardar decoración: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al guardar decoración. Por favor, intente nuevamente.",
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
        String nombre = vista.inputNombreDecoracion.getText().trim();
        if (nombre.isEmpty()) {
            errores.append("- El nombre es obligatorio\n");
        } else if (nombre.length() < 3) {
            errores.append("- El nombre debe tener al menos 3 caracteres\n");
        }

        // Validar stock
        String stock = vista.inputStockDecoracion.getText().trim();
        if (stock.isEmpty()) {
            errores.append("- El stock es obligatorio\n");
        }

        // Validar stock mínimo
        String stockMin = vista.inputStockMinimoDecoracion.getText().trim();
        if (stockMin.isEmpty()) {
            errores.append("- El stock mínimo es obligatorio\n");
        }

        // Validar stock máximo
        String stockMax = vista.inputStockMaximoDecoracion.getText().trim();
        if (stockMax.isEmpty()) {
            errores.append("- El stock máximo es obligatorio\n");
        }

        // Validar que stock máximo sea mayor o igual al stock mínimo
        try {
            int stockMinInt = Integer.parseInt(stockMin);
            int stockMaxInt = Integer.parseInt(stockMax);
            if (stockMaxInt < stockMinInt) {
                errores.append("- El stock máximo debe ser mayor o igual al stock mínimo\n");
            }
        } catch (NumberFormatException ex) {
            // Ya se validó arriba
        }

        // Validar ComboBox de proveedor
        String proveedorSeleccionado = (String) vista.comboBoxProveedor.getSelectedItem();
        if (proveedorSeleccionado == null || proveedorSeleccionado.equals("Seleccionar...")) {
            errores.append("- Debe seleccionar un proveedor\n");
        }

        // Validar ComboBox de colección
        String coleccionSeleccionada = (String) vista.comboBoxColeccion.getSelectedItem();
        if (coleccionSeleccionada == null || coleccionSeleccionada.equals("Seleccionar...")) {
            errores.append("- Debe seleccionar una colección\n");
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
        vista.inputNombreDecoracion.setText("");
        vista.inputDescripcionDecoracion.setText("");
        vista.inputStockDecoracion.setText("");
        vista.inputStockMinimoDecoracion.setText("");
        vista.inputStockMaximoDecoracion.setText("");

        if (vista.comboBoxProveedor != null) {
            vista.comboBoxProveedor.setSelectedIndex(0);
        }

        if (vista.comboBoxColeccion != null) {
            vista.comboBoxColeccion.setSelectedIndex(0);
        }

        if (vista.inputImagenDecoracion != null) {
            vista.inputImagenDecoracion.setText("");
        }

        rutaImagenActual = null;
    }
}
