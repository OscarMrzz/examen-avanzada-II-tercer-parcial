# Estructura de Controladores de Formulario

## Arquitectura de Controladores de Formulario

### Principio Fundamental

Los controladores de formulario **NO crean vistas**, solo las reciben y manipulan. Se encargan de gestionar la lógica de CRUD (Crear, Leer, Actualizar, Eliminar) y la interacción con los componentes del formulario.

## 1. Estructura Base de Controlador de Formulario

### Controlador de Formulario Agregar

```java
public class FormularioAgregarEntidadController {
    private FormularioAgregarEntidad vista;
    private EntidadModel modelo;
    private String rutaImagen = "";
    
    public FormularioAgregarEntidadController(FormularioAgregarEntidad vista) {
        this.vista = vista;
        this.modelo = new EntidadModel();
        inicializarEventos();
        cargarCombos();
    }
    
    private void inicializarEventos() {
        // Botón Guardar
        vista.botonGuardar.addActionListener(this::guardar);
        
        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);
        
        // Botón Cargar Imagen (si existe)
        if (vista.botonCargarImagen != null) {
            vista.botonCargarImagen.addActionListener(this::cargarImagen);
        }
    }
}
```

### Controlador de Formulario Editar

```java
public class FormularioEditarEntidadController {
    private FormularioEditarEntidad vista;
    private EntidadModel modelo;
    private String idEntidad;
    private String rutaImagen = "";
    
    public FormularioEditarEntidadController(FormularioEditarEntidad vista, String idEntidad) {
        this.vista = vista;
        this.modelo = new EntidadModel();
        this.idEntidad = idEntidad;
        inicializarEventos();
        cargarCombos();
        cargarDatosEntidad();
    }
    
    private void inicializarEventos() {
        // Botón Actualizar
        vista.botonGuardar.addActionListener(this::actualizar);
        
        // Botón Cancelar
        vista.botonCancelar.addActionListener(this::cancelar);
        
        // Botón Cargar Imagen (si existe)
        if (vista.botonCargarImagen != null) {
            vista.botonCargarImagen.addActionListener(this::cargarImagen);
        }
    }
}
```

## 2. Carga de ComboBox con Modelos Relacionados

### Ejemplo: Formulario de Compras con ComboBox de Productos

```java
/**
 * Carga los ComboBox usando modelos relacionados
 * - Ejemplo: FormularioCompra necesita productos y proveedores
 */
private void cargarCombos() {
    // Cargar ComboBox de Productos
    if (vista.comboBoxProductos != null) {
        try {
            ProductoModel productoModel = new ProductoModel();
            ArrayList<ProductoType> productos = productoModel.getAll();
            
            vista.comboBoxProductos.removeAllItems();
            vista.comboBoxProductos.addItem("Seleccionar...");
            
            for (ProductoType producto : productos) {
                // Formato: "ID - Nombre - Precio"
                String item = producto.getIdProducto() + " - " + 
                             producto.getNombreProducto() + " - $" + 
                             producto.getPrecioProducto();
                vista.comboBoxProductos.addItem(item);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar productos: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Cargar ComboBox de Proveedores
    if (vista.comboBoxProveedores != null) {
        try {
            ProveedorModel proveedorModel = new ProveedorModel();
            ArrayList<ProveedorType> proveedores = proveedorModel.getAll();
            
            vista.comboBoxProveedores.removeAllItems();
            vista.comboBoxProveedores.addItem("Seleccionar...");
            
            for (ProveedorType proveedor : proveedores) {
                String item = proveedor.getIdProveedor() + " - " + proveedor.getNombreProveedor();
                vista.comboBoxProveedores.addItem(item);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar proveedores: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
```

### Obtener ID desde ComboBox

```java
/**
 * Extrae el ID del elemento seleccionado del ComboBox
 * Formato: "ID - Nombre - OtrosCampos"
 */
private String obtenerIdSeleccionado(JComboBox<String> comboBox) {
    String seleccion = (String) comboBox.getSelectedItem();
    
    if (seleccion == null || seleccion.equals("Seleccionar...")) {
        return null;
    }
    
    // Extraer el ID (primera parte antes del " - ")
    return seleccion.split(" - ")[0];
}

private String obtenerNombreSeleccionado(JComboBox<String> comboBox) {
    String seleccion = (String) comboBox.getSelectedItem();
    
    if (seleccion == null || seleccion.equals("Seleccionar...")) {
        return null;
    }
    
    // Extraer el nombre (segunda parte entre " - ")
    String[] partes = seleccion.split(" - ");
    return partes.length > 1 ? partes[1] : "";
}
```

## 3. Manejo de Imágenes

### Funcionalidad de Cargar Imagen

```java
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
        
        try {
            // Crear directorio img si no existe
            File directorioImg = new File("src/img");
            if (!directorioImg.exists()) {
                directorioImg.mkdirs();
            }
            
            // Generar nombre único para la imagen
            String extension = getFileExtension(archivoSeleccionado.getName());
            String nombreImagen = "img_" + System.currentTimeMillis() + "." + extension;
            File destinoImagen = new File(directorioImg, nombreImagen);
            
            // Copiar archivo al directorio img
            Files.copy(archivoSeleccionado.toPath(), destinoImagen.toPath(), 
                      StandardCopyOption.REPLACE_EXISTING);
            
            // Actualizar ruta y vista previa
            this.rutaImagen = "src/img/" + nombreImagen;
            
            if (vista.labelImagen != null) {
                // Escalar imagen para vista previa
                ImageIcon icono = new ImageIcon(destinoImagen.getPath());
                Image imagenEscalada = icono.getImage().getScaledInstance(
                    vista.labelImagen.getWidth(), 
                    vista.labelImagen.getHeight(), 
                    Image.SCALE_SMOOTH);
                vista.labelImagen.setIcon(new ImageIcon(imagenEscalada));
            }
            
            JOptionPane.showMessageDialog(vista, "Imagen cargada correctamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar la imagen: " + ex.getMessage(), 
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
```

### Cargar Imagen Existente (Modo Edición)

```java
/**
 * Carga una imagen existente en el formulario de edición
 */
private void cargarImagenExistente(String rutaImagen) {
    if (rutaImagen != null && !rutaImagen.isEmpty()) {
        this.rutaImagen = rutaImagen;
        
        try {
            File archivoImagen = new File(rutaImagen);
            if (archivoImagen.exists() && vista.labelImagen != null) {
                ImageIcon icono = new ImageIcon(archivoImagen.getPath());
                Image imagenEscalada = icono.getImage().getScaledInstance(
                    vista.labelImagen.getWidth(), 
                    vista.labelImagen.getHeight(), 
                    Image.SCALE_SMOOTH);
                vista.labelImagen.setIcon(new ImageIcon(imagenEscalada));
            }
        } catch (Exception ex) {
            System.err.println("Error al cargar imagen existente: " + ex.getMessage());
        }
    }
}
```

## 4. Validación de Campos

### Validación Genérica

```java
/**
 * Valida los campos del formulario antes de guardar
 */
private boolean validarCampos() {
    StringBuilder errores = new StringBuilder();
    
    // Validar campos de texto obligatorios
    if (vista.inputNombre.getText().trim().isEmpty()) {
        errores.append("- El nombre es obligatorio\n");
    }
    
    if (vista.inputDescripcion.getText().trim().isEmpty()) {
        errores.append("- La descripción es obligatoria\n");
    }
    
    // Validar ComboBox
    if (vista.comboBoxCategoria != null) {
        String seleccion = (String) vista.comboBoxCategoria.getSelectedItem();
        if (seleccion == null || seleccion.equals("Seleccionar...")) {
            errores.append("- Debe seleccionar una categoría\n");
        }
    }
    
    // Validar campos numéricos
    if (vista.inputPrecio != null) {
        try {
            double precio = Double.parseDouble(vista.inputPrecio.getText().trim());
            if (precio <= 0) {
                errores.append("- El precio debe ser mayor a 0\n");
            }
        } catch (NumberFormatException e) {
            errores.append("- El precio debe ser un número válido\n");
        }
    }
    
    // Validar email
    if (vista.inputEmail != null && !vista.inputEmail.getText().trim().isEmpty()) {
        String email = vista.inputEmail.getText().trim();
        if (!email.contains("@") || !email.contains(".")) {
            errores.append("- El email no es válido\n");
        }
    }
    
    if (errores.length() > 0) {
        JOptionPane.showMessageDialog(vista, "Corrija los siguientes errores:\n\n" + errores.toString(), 
                "Errores de Validación", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    return true;
}
```

## 5. Operaciones CRUD

### Guardar (Formulario Agregar)

```java
/**
 * Guarda un nuevo registro en la base de datos
 */
private void guardar(ActionEvent e) {
    if (!validarCampos()) {
        return;
    }
    
    try {
        // Crear objeto con los datos del formulario
        EntidadType nuevaEntidad = new EntidadType();
        nuevaEntidad.setId(java.util.UUID.randomUUID().toString());
        nuevaEntidad.setNombre(vista.inputNombre.getText().trim());
        nuevaEntidad.setDescripcion(vista.inputDescripcion.getText().trim());
        nuevaEntidad.setImagen(rutaImagen);
        
        // Setear campos de ComboBox
        if (vista.comboBoxCategoria != null) {
            String idCategoria = obtenerIdSeleccionado(vista.comboBoxCategoria);
            nuevaEntidad.setIdCategoria(idCategoria);
        }
        
        // Setear campos numéricos
        if (vista.inputPrecio != null) {
            nuevaEntidad.setPrecio(Double.parseDouble(vista.inputPrecio.getText().trim()));
        }
        
        // Guardar en base de datos
        if (modelo.create(nuevaEntidad)) {
            JOptionPane.showMessageDialog(vista, "Registro guardado correctamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            vista.dispose(); // Cerrar formulario
        } else {
            JOptionPane.showMessageDialog(vista, "No se pudo guardar el registro", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(vista, "Error al guardar: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
```

### Actualizar (Formulario Editar)

```java
/**
 * Actualiza un registro existente en la base de datos
 */
private void actualizar(ActionEvent e) {
    if (!validarCampos()) {
        return;
    }
    
    try {
        // Obtener entidad existente
        EntidadType entidad = modelo.getById(idEntidad);
        if (entidad == null) {
            JOptionPane.showMessageDialog(vista, "No se encontró el registro", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Actualizar datos
        entidad.setNombre(vista.inputNombre.getText().trim());
        entidad.setDescripcion(vista.inputDescripcion.getText().trim());
        
        // Actualizar imagen solo si se cargó una nueva
        if (!rutaImagen.isEmpty()) {
            entidad.setImagen(rutaImagen);
        }
        
        // Actualizar otros campos
        if (vista.comboBoxCategoria != null) {
            String idCategoria = obtenerIdSeleccionado(vista.comboBoxCategoria);
            entidad.setIdCategoria(idCategoria);
        }
        
        if (vista.inputPrecio != null) {
            entidad.setPrecio(Double.parseDouble(vista.inputPrecio.getText().trim()));
        }
        
        // Guardar cambios
        if (modelo.update(entidad)) {
            JOptionPane.showMessageDialog(vista, "Registro actualizado correctamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.dispose(); // Cerrar formulario
        } else {
            JOptionPane.showMessageDialog(vista, "No se pudo actualizar el registro", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(vista, "Error al actualizar: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
```

### Cargar Datos (Modo Edición)

```java
/**
 * Carga los datos de una entidad existente en el formulario
 */
private void cargarDatosEntidad() {
    try {
        EntidadType entidad = modelo.getById(idEntidad);
        if (entidad == null) {
            JOptionPane.showMessageDialog(vista, "No se encontró el registro", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            vista.dispose();
            return;
        }
        
        // Cargar campos de texto
        vista.inputNombre.setText(entidad.getNombre());
        vista.inputDescripcion.setText(entidad.getDescripcion());
        
        // Cargar ComboBox
        if (vista.comboBoxCategoria != null && entidad.getIdCategoria() != null) {
            // Buscar y seleccionar el item correcto en el ComboBox
            for (int i = 0; i < vista.comboBoxCategoria.getItemCount(); i++) {
                String item = vista.comboBoxCategoria.getItemAt(i);
                if (item.startsWith(entidad.getIdCategoria() + " - ")) {
                    vista.comboBoxCategoria.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        // Cargar campos numéricos
        if (vista.inputPrecio != null) {
            vista.inputPrecio.setText(String.valueOf(entidad.getPrecio()));
        }
        
        // Cargar imagen
        cargarImagenExistente(entidad.getImagen());
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(vista, "Error al cargar datos: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
```

## 6. Utilidades Adicionales

### Limpiar Campos

```java
/**
 * Limpia todos los campos del formulario
 */
private void limpiarCampos() {
    vista.inputNombre.setText("");
    vista.inputDescripcion.setText("");
    
    if (vista.comboBoxCategoria != null) {
        vista.comboBoxCategoria.setSelectedIndex(0);
    }
    
    if (vista.inputPrecio != null) {
        vista.inputPrecio.setText("");
    }
    
    if (vista.labelImagen != null) {
        vista.labelImagen.setIcon(null);
    }
    
    rutaImagen = "";
}
```

### Cancelar

```java
/**
 * Cierra el formulario sin guardar cambios
 */
private void cancelar(ActionEvent e) {
    int confirmacion = JOptionPane.showConfirmDialog(
        vista,
        "¿Está seguro de que desea cancelar? Los cambios no se guardarán.",
        "Confirmar Cancelación",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );
    
    if (confirmacion == JOptionPane.YES_OPTION) {
        vista.dispose();
    }
}
```

## 7. Puntos Clave para Controladores de Formulario

1. **Separación MVC**: Usa modelos para todas las operaciones CRUD
2. **ComboBox**: Carga usando modelos relacionados con formato "ID - Nombre"
3. **Imágenes**: Guarda en `src/img/` con nombres únicos
4. **Validación**: Valida todos los campos antes de guardar
5. **ID desde ComboBox**: Extrae ID usando `split(" - ")[0]`
6. **Modo Edición**: Carga datos existentes con `modelo.getById()`
7. **Manejo de Errores**: Captura y muestra excepciones amigablemente
8. **Limpieza**: Limpia campos después de guardar correctamente

## 8. Imports Necesarios

```java
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
```