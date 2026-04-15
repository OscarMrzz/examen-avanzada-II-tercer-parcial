# Estructura de Tablas - Guía de Desarrollo

## 1. Estructura Básica de la Tabla

### Columnas Obligatoratorias

- **NO**: Columna numérica que sirve como índice/identificador de la fila
- **[Datos específicos de la entidad]**: Columnas según la tabla (nombre, email, teléfono, etc.)

### Configuración Visual

```java
// Configuración del modelo de tabla
DefaultTableModel model = new DefaultTableModel() {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Las celdas no son editables directamente
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) { // Columna NO
            return Integer.class;
        }
        return super.getColumnClass(columnIndex);
    }
};

// Configuración de la tabla
JTable tabla = new JTable(model);
tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
tabla.getTableHeader().setReorderingAllowed(false);
```

## 2. Resaltado de Fila Completa

### Selección y Resaltado

```java
// Configurar resaltado de fila completa
tabla.setRowSelectionAllowed(true);
tabla.setColumnSelectionAllowed(false);

// Personalizar colores de selección
tabla.setSelectionBackground(new Color(51, 153, 255));
tabla.setSelectionForeground(Color.WHITE);

// Listener para resaltar fila completa
tabla.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {
        int selectedRow = tabla.getSelectedRow();
        if (selectedRow >= 0) {
            // Resaltar fila completa automáticamente con la configuración anterior
            habilitarBotonesEdicionEliminacion(true);
        } else {
            habilitarBotonesEdicionEliminacion(false);
        }
    }
});
```

## 3. Menú Contextual (Click Derecho)

### Implementación del Menú

```java
// Crear menú contextual
JPopupMenu popupMenu = new JPopupMenu();
JMenuItem menuItemEditar = new JMenuItem("Editar");
JMenuItem menuItemEliminar = new JMenuItem("Eliminar");

// Agregar íconos (opcional)
menuItemEditar.setIcon(new ImageIcon("src/img/edit.png"));
menuItemEliminar.setIcon(new ImageIcon("src/img/delete.png"));

popupMenu.add(menuItemEditar);
popupMenu.add(menuItemEliminar);

// Agregar listener a la tabla
tabla.setComponentPopupMenu(popupMenu);

// Mouse listener para mostrar menú solo en filas con datos
tabla.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            int row = tabla.rowAtPoint(e.getPoint());
            if (row >= 0 && tabla.getRowCount() > 0) {
                tabla.setRowSelectionInterval(row, row);
                menuItemEditar.setEnabled(true);
                menuItemEliminar.setEnabled(true);
            } else {
                menuItemEditar.setEnabled(false);
                menuItemEliminar.setEnabled(false);
            }
        }
    }
});

// Action listeners
menuItemEditar.addActionListener(e -> editarRegistro());
menuItemEliminar.addActionListener(e -> eliminarRegistro());
```

## 4. Métodos de Refresco Obligatorios

### En el Controlador

```java
public class TablaController {
    private JTable tabla;
    private DefaultTableModel model;

    // Método principal de refresco
    public void refrescarTabla() {
        try {
            // Limpiar tabla
            model.setRowCount(0);

            // Obtener datos actualizados de la base de datos
            List<Entidad> lista = entidadDAO.obtenerTodos();

            // Llenar tabla con datos actualizados
            for (Entidad entidad : lista) {
                Object[] fila = {
                    entidad.getNo(),
                    entidad.getNombre(),
                    entidad.getEmail(),
                    // ... otros campos
                };
                model.addRow(fila);
            }

            // Ajustar ancho de columnas
            ajustarAnchoColumnas();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                "Error al refrescar la tabla: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(TablaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Refresco después de agregar
    public void refrescarDespuesDeAgregar() {
        refrescarTabla();
        // Seleccionar la última fila agregada
        if (model.getRowCount() > 0) {
            tabla.setRowSelectionInterval(model.getRowCount() - 1, model.getRowCount() - 1);
            tabla.scrollRectToVisible(tabla.getCellRect(model.getRowCount() - 1, 0, true));
        }
    }

    // Refresco después de editar
    public void refrescarDespuesDeEditar(int filaSeleccionada) {
        refrescarTabla();
        // Mantener selección en la misma fila si existe
        if (filaSeleccionada >= 0 && filaSeleccionada < model.getRowCount()) {
            tabla.setRowSelectionInterval(filaSeleccionada, filaSeleccionada);
        }
    }

    // Refresco después de eliminar
    public void refrescarDespuesDeEliminar(int filaEliminada) {
        refrescarTabla();
        // Seleccionar la fila siguiente o la anterior si no hay siguiente
        if (model.getRowCount() > 0) {
            int nuevaSeleccion = Math.min(filaEliminada, model.getRowCount() - 1);
            tabla.setRowSelectionInterval(nuevaSeleccion, nuevaSeleccion);
        }
    }

    // Refresco al iniciar la vista
    public void refrescarAlIniciar() {
        refrescarTabla();
        // Limpiar selección inicial
        tabla.clearSelection();
        habilitarBotonesEdicionEliminacion(false);
    }

    private void ajustarAnchoColumnas() {
        // Ajustar automáticamente
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // O ajustar manualmente columnas específicas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);  // NO
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200); // Nombre
        // ... otras columnas
    }

    private void habilitarBotonesEdicionEliminacion(boolean habilitar) {
        // Habilitar/deshabilitar botones de editar y eliminar
        // btnEditar.setEnabled(habilitar);
        // btnEliminar.setEnabled(habilitar);
    }
}
```

## 5. Integración con Operaciones CRUD

### Al Agregar

```java
public void agregarRegistro() {
    try {
        // Lógica para agregar registro
        entidadDAO.agregar(nuevaEntidad);

        // Refrescar tabla después de agregar
        refrescarDespuesDeAgregar();

        JOptionPane.showMessageDialog(null,
            "Registro agregado exitosamente",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null,
            "Error al agregar registro: " + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
```

### Al Editar

```java
public void editarRegistro() {
    int filaSeleccionada = tabla.getSelectedRow();
    if (filaSeleccionada == -1) return;

    try {
        // Obtener datos de la fila seleccionada
        int no = (Integer) model.getValueAt(filaSeleccionada, 0);

        // Lógica para editar registro
        entidadDAO.actualizar(entidadModificada);

        // Refrescar tabla después de editar
        refrescarDespuesDeEditar(filaSeleccionada);

        JOptionPane.showMessageDialog(null,
            "Registro actualizado exitosamente",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null,
            "Error al actualizar registro: " + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
```

### Al Eliminar

```java
public void eliminarRegistro() {
    int filaSeleccionada = tabla.getSelectedRow();
    if (filaSeleccionada == -1) return;

    // Confirmación
    int opcion = JOptionPane.showConfirmDialog(null,
        "¿Está seguro de eliminar este registro?",
        "Confirmar Eliminación",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE);

    if (opcion == JOptionPane.YES_OPTION) {
        try {
            // Obtener ID de la fila seleccionada
            int no = (Integer) model.getValueAt(filaSeleccionada, 0);

            // Lógica para eliminar registro
            entidadDAO.eliminar(no);

            // Refrescar tabla después de eliminar
            refrescarDespuesDeEliminar(filaSeleccionada);

            JOptionPane.showMessageDialog(null,
                "Registro eliminado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                "Error al eliminar registro: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
```

## 6. En el Constructor de la Vista

```java
public class TablaView extends JFrame {
    // ... componentes

    public TablaView() {
        initComponents();

        // Configurar tabla
        configurarTabla();

        // Crear menú contextual
        crearMenuContextual();

        // Refrescar al iniciar
        controller.refrescarAlIniciar();
    }

    private void configurarTabla() {
        // Configuración inicial de la tabla
        controller.configurarTabla();
    }

    private void crearMenuContextual() {
        // Crear y configurar menú contextual
        controller.crearMenuContextual();
    }
}
```

## 7. Buenas Prácticas

1. **Siempre incluir columna NO** como primera columna
2. **Resaltar fila completa** al seleccionar
3. **Implementar menú contextual** con opciones editar y eliminar
4. **Refrescar después de cada operación** CRUD
5. **Mantener selección** después de refrescar cuando sea posible
6. **Manejo de errores** con mensajes amigables
7. **Logging** para depuración
8. **Validaciones** antes de realizar operaciones
9. **Confirmación** para operaciones destructivas (eliminar)
10. **Feedback visual** para el usuario (mensajes de éxito/error)

## 8. Estructura de Archivos Recomendada

```
src/
  Controlador/
    [Entidad]/
      [Entidad]Controller.java
      [Entidad]TablaController.java
  Modelo/
    [Entidad]/
      [Entidad].java
      [Entidad]DAO.java
  Vista/
    [Entidad]/
      [Entidad]View.java
      [Entidad]FormularioAgregar.java
      [Entidad]FormularioEditar.java
```

Esta estructura asegura consistencia en todas las tablas del proyecto y facilita el mantenimiento del código.
