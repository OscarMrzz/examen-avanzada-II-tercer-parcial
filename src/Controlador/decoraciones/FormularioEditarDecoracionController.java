package Controlador.decoraciones;

import Vista.decoraciones.FormularioEditarDecoracion;
import Modelo.decoraciones.DecoracionModel;
import Type.decoraciones.DecoracionType;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Controlador para el formulario de editar decoración
 * 
 * @author ossca
 */
public class FormularioEditarDecoracionController {

    private static final Logger logger = Logger.getLogger(FormularioEditarDecoracionController.class.getName());
    private FormularioEditarDecoracion vista;
    private DecoracionModel modelo;
    private String idDecoracion;
    private String rutaImagenActual;

    public FormularioEditarDecoracionController(FormularioEditarDecoracion vista, String idDecoracion) {
        this.vista = vista;
        this.idDecoracion = idDecoracion;
        this.modelo = new DecoracionModel();
        inicializarEventos();
        cargarDatosDecoracion();
    }

    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::actualizar);

        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);

        // Botón Agregar Imagen
        vista.botonBuscarImagen.addActionListener(this::agregarImagen);
    }

    /**
     * Carga los datos de la decoración en el formulario usando el Modelo
     */
    private void cargarDatosDecoracion() {
        try {
            DecoracionType decoracion = modelo.getById(idDecoracion);
            if (decoracion != null) {
                vista.inputNombreDecoracion.setText(decoracion.getNombreDecoracion());
                vista.inputDescripcionDecoracion.setText(
                        decoracion.getDescripcionDecoracion() != null ? decoracion.getDescripcionDecoracion() : "");
                vista.inputStockDecoracion.setText(String.valueOf(decoracion.getStockDecoracion()));
                vista.inputStockMinimoDecoracion.setText(String.valueOf(decoracion.getStockMinimoDecoracion()));
                vista.inputStockMaximoDecoracion.setText(String.valueOf(decoracion.getStockMaximoDecoracion()));

                // Cargar imagen si existe
                rutaImagenActual = decoracion.getImagenDecoracion();
                if (vista.inputImagenDecoracion != null && rutaImagenActual != null) {
                    vista.inputImagenDecoracion.setText(rutaImagenActual);
                }

                // Cargar proveedor y colección en los ComboBox
                // Aquí deberías cargar los datos correspondientes en los ComboBox
            } else {
                JOptionPane.showMessageDialog(vista, "Decoración no encontrada",
                        "Error", JOptionPane.ERROR_MESSAGE);
                vista.dispose();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al cargar datos de la decoración: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar datos de la decoración: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            vista.dispose();
        }
    }

    /**
     * Agrega una nueva imagen para la decoración con manejo completo
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

                // Confirmar si se desea reemplazar la imagen actual
                if (rutaImagenActual != null && !rutaImagenActual.isEmpty()) {
                    int confirmacion = JOptionPane.showConfirmDialog(
                            vista,
                            "¿Desea reemplazar la imagen actual?",
                            "Confirmar Reemplazo",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (confirmacion != JOptionPane.YES_OPTION) {
                        return;
                    }

                    // Eliminar imagen anterior
                    eliminarImagen(rutaImagenActual);
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
                String nombreArchivo = java.util.UUID.randomUUID().toString() + extension.toLowerCase();
                String rutaCompleta = directorioImagenes + nombreArchivo;

                // Copiar archivo al directorio de la aplicación
                Path destino = Paths.get(rutaCompleta);
                Files.copy(archivoSeleccionado.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

                // Guardar ruta de la nueva imagen
                rutaImagenActual = rutaCompleta;

                // Actualizar interfaz con la ruta de la imagen
                if (vista.inputImagenDecoracion != null) {
                    vista.inputImagenDecoracion.setText(rutaCompleta);
                }

                JOptionPane.showMessageDialog(vista,
                        "Imagen actualizada exitosamente:\n" + nombreArchivo,
                        "Imagen Actualizada", JOptionPane.INFORMATION_MESSAGE);

                logger.log(Level.INFO, "Imagen actualizada: " + rutaCompleta);
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
     * Actualiza los datos de la decoración en la base de datos
     */
    private void actualizar(java.awt.event.ActionEvent e) {
        if (!validarCampos()) {
            return;
        }

        try {
            // Crear objeto DecoracionType con los datos actualizados
            DecoracionType decoracionActualizada = new DecoracionType();
            decoracionActualizada.setIdDecoracion(idDecoracion);
            decoracionActualizada.setNombreDecoracion(vista.inputNombreDecoracion.getText().trim());
            decoracionActualizada.setDescripcionDecoracion(vista.inputDescripcionDecoracion.getText().trim());
            decoracionActualizada.setImagenDecoracion(rutaImagenActual);
            decoracionActualizada.setEstadoDecoracion(true);

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
                decoracionActualizada.setStockDecoracion(stock);
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
                decoracionActualizada.setStockMinimoDecoracion(stockMin);
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
                decoracionActualizada.setStockMaximoDecoracion(stockMax);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El stock máximo debe ser un número válido",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputStockMaximoDecoracion.requestFocus();
                return;
            }

            // Validar que stock máximo sea mayor o igual al stock mínimo
            if (decoracionActualizada.getStockMaximoDecoracion() < decoracionActualizada.getStockMinimoDecoracion()) {
                JOptionPane.showMessageDialog(vista, "El stock máximo debe ser mayor o igual al stock mínimo",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                vista.inputStockMaximoDecoracion.requestFocus();
                return;
            }

            // Obtener proveedor y colección del ComboBox
            String proveedorSeleccionado = (String) vista.comboBoxProveedor.getSelectedItem();
            if (proveedorSeleccionado != null && !proveedorSeleccionado.equals("Seleccionar...")) {
                // Aquí deberías obtener el ID real del proveedor
                decoracionActualizada.setIdProveedorDecoracion(proveedorSeleccionado);
            }

            // Nota: comboBoxColeccion no existe en FormularioEditarDecoracion
            // Si se necesita agregar colección, primero agregar el componente a la vista

            // Actualizar en base de datos usando el modelo
            if (modelo.update(decoracionActualizada)) {
                JOptionPane.showMessageDialog(vista, "Decoración actualizada correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo actualizar la decoración",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al actualizar decoración: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(vista, "Error al actualizar decoración. Por favor, intente nuevamente.",
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

        // Validar ComboBox de proveedor
        String proveedorSeleccionado = (String) vista.comboBoxProveedor.getSelectedItem();
        if (proveedorSeleccionado == null || proveedorSeleccionado.equals("Seleccionar...")) {
            errores.append("- Debe seleccionar un proveedor\n");
        }

        // Validar ComboBox de colección (si existe)
        // Nota: comboBoxColeccion no existe en FormularioEditarDecoracion

        if (errores.length() > 0) {
            logger.log(Level.WARNING, "Errores de validación: {0}", errores.toString());
            JOptionPane.showMessageDialog(vista, "Corrija los siguientes errores:\n\n" + errores.toString(),
                    "Errores de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
