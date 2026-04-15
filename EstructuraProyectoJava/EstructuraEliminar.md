# MANUAL DE IMPLEMENTACIÓN - FUNCIONALIDAD ELIMINAR

## Descripción General

Este manual describe el patrón estándar para implementar la funcionalidad de eliminar en cualquier módulo del sistema (usuarios, clientes, productos, ventas, compras, etc.).

## Arquitectura MVC

- **Modelo**: Maneja la lógica de datos y eliminación física/lógica
- **Vista**: Muestra la interfaz y menú contextual
- **Controlador**: Gestiona eventos y coordina entre Modelo y Vista

---

## 1. ESTRUCTURA BASE DE DATOS

### Requisitos del Schema SQL

```sql
-- Cada tabla debe tener estos campos obligatorios:
CREATE TABLE [nombre_tabla] (
    id_[modulo] VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    -- otros campos...
    estado_[modulo] BOOLEAN DEFAULT TRUE,  -- <-- OBLIGATORIO
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- <-- OBLIGATORIO
);
```

### Valores de Estado

- `TRUE` o `1` = Activo (visible en la aplicación)
- `FALSE` o `0` = Inactivo (eliminado lógicamente)

---

## 2. IMPLEMENTACIÓN EN EL MODELO (Model)

### Estructura del Archivo Modelo

```java
public class [Modulo]Model extends Conexion {
    // Variables globales
    PreparedStatement preparedStatement = null;
    Connection connection;
    String sentenciaSQL;
    ResultSet resultSet;

    // Método ELIMINAR (OBLIGATORIO)
    public boolean delete(String id) {
        connection = getConxion();
        sentenciaSQL = "UPDATE [nombre_tabla] SET estado_[modulo] = 0 WHERE id_[modulo] = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger([Modulo]Model.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    }

    // Método OBTENER TODOS (OBLIGATORIO - Filtrar activos)
    public ArrayList<[Modulo]Type> getAll() {
        ArrayList<[Modulo]Type> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM [nombre_tabla] WHERE estado_[modulo] = 1";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Mapear campos del ResultSet al objeto Type
                [Modulo]Type objeto = new [Modulo]Type();
                objeto.setId[Modulo](resultSet.getString(1));
                // ... otros campos
                objeto.setEstado[Modulo](resultSet.getBoolean([indice_estado]));
                lista.add(objeto);
            }
            connection.close();
        } catch (SQLException e) {
            // Manejar error SIN JOptionPane
            Logger.getLogger([Modulo]Model.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
}
```

### REGLAS IMPORTANTES PARA EL MODELO:

1. **NO USAR JOptionPane** en el Modelo para mensajes de éxito
2. **SIEMPRE** usar eliminación lógica (UPDATE estado = 0)
3. **SIEMPRE** filtrar por estado = 1 en getAll()
4. **SIEMPRE** cerrar la conexión en el bloque finally
5. **USAR** Logger para errores, no System.out.println

---

## 3. IMPLEMENTACIÓN EN EL CONTROLADOR (Controller)

### Estructura del Archivo Controlador

```java
public class [Modulo]Controller {
    private [Modulo]Vista vista;
    private [Modulo]Model modelo;

    public [Modulo]Controller([Modulo]Vista vista) {
        this.vista = vista;
        this.modelo = new [Modulo]Model();
        inicializarEventos();
        cargarTabla();
    }

    private void inicializarEventos() {
        // Evento mouse para menú contextual
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
    }

    // Método CARGAR TABLA (OBLIGATORIO)
    public void cargarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
        modeloTabla.setRowCount(0);

        try {
            ArrayList<[Modulo]Type> registros = modelo.getAll();
            int index = 1;

            for ([Modulo]Type registro : registros) {
                Object[] fila = {
                    index++, // Número secuencial
                    registro.get[CampoPrincipal](),
                    registro.get[CampoSecundario](),
                    registro.isEstado[Modulo]() ? "ACTIVO" : "INACTIVO"
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                "Error al cargar los datos: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método MENÚ CONTEXTUAL (OBLIGATORIO)
    private void mostrarMenuContextual(MouseEvent e) {
        int fila = vista.tabla.rowAtPoint(e.getPoint());

        if (fila >= 0) {
            vista.tabla.setRowSelectionInterval(fila, fila);

            JPopupMenu menu = new JPopupMenu();

            JMenuItem menuItemEditar = new JMenuItem("Editar");
            menuItemEditar.addActionListener(evt -> abrirFormularioEditar());

            JMenuItem menuItemEliminar = new JMenuItem("Eliminar");
            menuItemEliminar.addActionListener(evt -> eliminar());

            menu.add(menuItemEditar);
            menu.add(menuItemEliminar);

            menu.show(vista.tabla, e.getX(), e.getY());
        }
    }

    // Método ELIMINAR (OBLIGATORIO)
    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista,
                "Por favor seleccione un registro para eliminar",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ArrayList<[Modulo]Type> registros = modelo.getAll();
            if (fila < registros.size()) {
                [Modulo]Type registroSeleccionado = registros.get(fila);
                String idRegistro = registroSeleccionado.getId[Modulo]();
                String nombreRegistro = registroSeleccionado.get[CampoPrincipal]();

                int confirmacion = JOptionPane.showConfirmDialog(
                    vista,
                    "¿Está seguro de que desea eliminar \"" + nombreRegistro + "\"?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (modelo.delete(idRegistro)) {
                        JOptionPane.showMessageDialog(vista,
                            "Registro eliminado correctamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarTabla(); // Recargar tabla
                    } else {
                        JOptionPane.showMessageDialog(vista,
                            "No se pudo eliminar el registro",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                "Error al eliminar: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
```

### REGLAS IMPORTANTES PARA EL CONTROLADOR:

1. **SIEMPRE** mostrar confirmación antes de eliminar
2. **SIEMPRE** recargar la tabla después de eliminar exitosamente
3. **USAR** el índice de la fila para obtener el ID correcto
4. **MANEJAR** todas las excepciones con mensajes amigables
5. **NO** confiar en el índice de la tabla después de cambios

---

## 4. IMPLEMENTACIÓN EN LA VISTA (View)

### Requisitos de la Vista:

```java
public class [Modulo]Vista extends javax.swing.JFrame {
    // Componentes obligatorios
    public JTable tabla;  // <-- OBLIGATORIO

    // Configuración de la tabla
    private void configurarTabla() {
        // Establecer modelo de tabla
        tabla.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {
                "#", "Campo Principal", "Campo Secundario", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };
        });

        // Configurar selección
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);
    }
}
```

---

## 5. CHEATLIST DE VERIFICACIÓN

### Antes de Implementar:

- [ ] ¿La tabla tiene campo `estado_[modulo] BOOLEAN`?
- [ ] ¿El campo tiene `DEFAULT TRUE`?
- [ ] ¿Existe el Type correspondiente?
- [ ] ¿La vista tiene una JTable?

### Durante la Implementación:

- [ ] ¿El método delete() usa `UPDATE SET estado = 0`?
- [ ] ¿El método getAll() filtra por `WHERE estado = 1`?
- [ ] ¿No hay JOptionPane en el Modelo?
- [ ] ¿El controlador muestra confirmación?
- [ ] ¿Se recarga la tabla después de eliminar?

### Después de Implementar:

- [ ] ¿Los registros eliminados desaparecen de la vista?
- [ ] ¿Los datos permanecen en la base de datos?
- [ ] ¿No hay mensajes duplicados?
- [ ] ¿Funciona el menú contextual con clic derecho?
- [ ] ¿La tabla se actualiza correctamente?

---

## 6. ERRORES COMUNES Y SOLUCIONES

### Error: "No se elimina visualmente"

**Causa**: getAll() no filtra por estado
**Solución**: Agregar `WHERE estado_[modulo] = 1` en getAll()

### Error: "Mensajes duplicados"

**Causa**: JOptionPane en el Modelo y Controlador
**Solución**: Eliminar JOptionPane del Modelo

### Error: "Índice incorrecto al eliminar"

**Causa**: Confianza en el índice de la tabla después de cambios
**Solución**: Obtener lista fresca y usar índice actual

### Error: "Eliminación física accidental"

**Causa**: Usar DELETE en lugar de UPDATE
**Solución**: Siempre usar eliminación lógica

---

## 7. EJEMPLOS POR MÓDULO

### Usuarios:

```sql
UPDATE usuarios SET estado_usuario = 0 WHERE id_usuario = ?
SELECT * FROM usuarios WHERE estado_usuario = 1
```

### Clientes:

```sql
UPDATE clientes SET estado_cliente = 0 WHERE id_cliente = ?
SELECT * FROM clientes WHERE estado_cliente = 1
```

### Productos/Decoraciones:

```sql
UPDATE decoraciones SET estado_decoracion = 0 WHERE id_decoracion = ?
SELECT * FROM decoraciones WHERE estado_decoracion = 1
```

---

## 8. BUENAS PRÁCTICAS

1. **Consistencia**: Usar siempre el mismo patrón
2. **Seguridad**: Siempre eliminar lógicamente
3. **Experiencia**: Mostrar confirmación y feedback
4. **Mantenibilidad**: Separar responsabilidades MVC
5. **Logging**: Registrar errores para depuración

---

## 9. TESTING MANUAL

### Pasos para probar la funcionalidad:

1. Abrir el módulo
2. Verificar que todos los registros activos se muestran
3. Clic derecho en un registro
4. Seleccionar "Eliminar" del menú contextual
5. Confirmar en el diálogo
6. Verificar mensaje de éxito
7. Verificar que el registro desaparece de la tabla
8. Verificar en BD que el estado cambió a 0

---

## 10. CONCLUSIONES

Este patrón garantiza:

- **Integridad de datos**: No se pierde información
- **Consistencia**: Comportamiento uniforme en todos los módulos
- **Auditoría**: Posibilidad de recuperar datos eliminados
- **Experiencia de usuario**: Flujo intuitivo y seguro
- **Mantenibilidad**: Código limpio y estructurado

**SEGUIR ESTE MANUAL ES OBLIGATORIO PARA CUALQUIER NUEVA IMPLEMENTACIÓN DE ELIMINAR.**
