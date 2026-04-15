# Estructura de Controladores

## Arquitectura de Controladores

### Principio Fundamental

Los controladores **NO crean vistas**, solo las reciben y manipulan.

## 1. HomeController

### Responsabilidades

- Gestionar la vista principal (Home)
- Coordinar la navegación entre módulos
- Recibir todas las vistas principales del sistema
- No crear vistas nuevas, solo gestionar las existentes

### Estructura

```java
public class HomeController {
    // Vistas principales que recibe
    Home home;
    LoginVista login;
    UsuariosVista usuariosVista;
    proveedoresVista proveedoresVista;
    clientesVista clientesVista;
    decoracionesVista decoracionesVista;
    coleccionesVista coleccionesVista;
    comprasVista comprasVista;
    inventarioVista inventarioVista;
    ventasVista ventasVista;


    public HomeController(Home home, LoginVista login, UsuariosVista usuariosVista) {
        this.home = home;
        this.login = login;
        this.usuariosVista = usuariosVista;

        // Inicializar vistas (sin crear nuevas)
        this.proveedoresVista = new proveedoresVista(null, true);
        this.clientesVista = new clientesVista(null, true);
        this.decoracionesVista = new decoracionesVista(null, true);
        this.coleccionesVista = new coleccionesVista(null, true);
        this.comprasVista = new comprasVista(null, true);
        this.inventarioVista = new inventarioVista(null, true);
        this.ventasVista = new ventasVista(null, true);
    }
}
```

## 2. Controladores Específicos

### UsuariosController

```java
public class UsuariosController {
    UsuariosVista usuariosVista;
    Home home;
    UsuarioFormulario usuarioFormulario;

    public UsuariosController(UsuariosVista usuariosVista, Home home) {
        this.usuariosVista = usuariosVista;
        this.home = home;
        // NOTA: No crear vistas, solo recibir las que necesite
    }
}
```

### ClientesController

```java
public class ClientesController {
    ClientesVista clientesVista;
    Home home;
    ClienteFormulario clienteFormulario;

    public ClientesController(ClientesVista clientesVista, Home home) {
        this.clientesVista = clientesVista;
        this.home = home;
    }
}
```

### VentasController

```java
public class VentasController {
    VentasVista ventasVista;
    FormularioAgregarVenta formularioAgregarVenta;
    FormularioEditarVenta formularioEditarVenta;
    ReporteVentas reporteVentas;

    public VentasController(VentasVista ventasVista, FormularioAgregarVenta formularioAgregarVenta,
                           FormularioEditarVenta formularioEditarVenta, ReporteVentas reporteVentas) {
        this.ventasVista = ventasVista;
        this.formularioAgregarVenta = formularioAgregarVenta;
        this.formularioEditarVenta = formularioEditarVenta;
        this.reporteVentas = reporteVentas;
    }
}
```

## 3. Reglas de Oro para Controladores

### Regla 1: NO CREAR VISTAS

```java
// INCORRECTO
public class UsuariosController {
    public void mostrarFormulario() {
        UsuarioFormulario formulario = new UsuarioFormulario(); // PROHIBIDO
    }
}

// CORRECTO
public class UsuariosController {
    UsuarioFormulario formulario;

    public UsuariosController(UsuarioFormulario formulario) {
        this.formulario = formulario; // RECIBIR, NO CREAR
    }
}
```

### Regla 2: Inyección de Dependencias

- Todas las vistas se inyectan por constructor
- Los controladores solo manipulan vistas recibidas
- El punto de entrada es responsable de crear todas las vistas

### Regla 3: Responsabilidad Única

- Cada controlador gestiona solo sus vistas relacionadas
- HomeController gestiona navegación y vistas principales
- Controladores específicos gestionan su módulo únicamente

## 4. Flujo de Datos

### Inicialización

1. **Punto de Entrada**: Crea todas las vistas
2. **Punto de Entrada**: Crea HomeController con vistas principales
3. **HomeController**: Crea controladores específicos con sus vistas
4. **Inicio**: HomeController inicia la aplicación

### Navegación

1. **Usuario interactúa** con vista principal
2. **HomeController** detecta acción
3. **HomeController** delega al controlador específico
4. **Controlador específico** manipula su vista

## 5. Ejemplo Completo de Flujo

### Punto de Entrada

```java
public static void main(String[] args) {
    // Crear todas las vistas
    LoginVista loginVista = new LoginVista(null, true);
    Home home = new Home();
    UsuariosVista usuariosVista = new UsuariosVista(null, true);
    UsuarioFormulario usuarioFormulario = new UsuarioFormulario(null, true);

    // Crear HomeController
    HomeController homeController = new HomeController(home, loginVista, usuariosVista);

    // Crear controladores específicos con sus vistas
    UsuariosController usuariosController = new UsuariosController(usuariosVista, home);
    usuariosController.setFormulario(usuarioFormulario);

    // Iniciar
    homeController.iniciar();
}
```

### HomeController

```java
public class HomeController {
    public void botonUsuariosPresionado() {
        // Delegar al controlador específico
        usuariosController.mostrarVista();
    }
}
```

### UsuariosController

```java
public class UsuariosController {
    public void mostrarVista() {
        usuariosVista.setVisible(true);
        home.setVisible(false);
    }

    public void botonAgregarPresionado() {
        usuarioFormulario.setVisible(true);
    }
}
```

## 6. Guía de Implementación - Controladores Genéricos

### Carga de Tabla con Modelo MVC

```java
/**
 * Carga todos los registros en la tabla usando el Modelo
 * - Usa modelo.getAll() para obtener datos del Modelo
 * - Muestra índice secuencial en primera columna (NO, NO ID)
 * - Adaptable a cualquier entidad (Usuarios, Clientes, Productos, etc.)
 */
public void cargarTabla() {
    DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
    modeloTabla.setRowCount(0);

    try {
        ArrayList<TipoEntidad> entidades = modelo.getAll();
        int index = 1; // Índice secuencial para columna NO

        for (TipoEntidad entidad : entidades) {
            Object[] fila = {
                index++, // Columna NO: número de registro
                entidad.getCampo1(), // Campo principal (nombre, descripción, etc.)
                entidad.getCampo2(), // Campo secundario (rol, tipo, precio, etc.)
                entidad.getCampoN()  // Campos adicionales según la entidad
            };
            modeloTabla.addRow(fila);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(vista, "Error al cargar los datos: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
```

### Implementación del Buscador Genérico

```java
/**
 * Busca registros según el texto ingresado
 * - Filtra sobre los datos obtenidos del modelo.getAll()
 * - Búsqueda case-insensitive en campos relevantes
 * - Mantiene índice secuencial en resultados
 */
private void buscar(ActionEvent e) {
    String textoBusqueda = vista.campoBusqueda.getText().trim();

    if (textoBusqueda.isEmpty() || textoBusqueda.equals("Buscar...")) {
        cargarTabla(); // Mostrar todos si está vacío
        return;
    }

    DefaultTableModel modeloTabla = (DefaultTableModel) vista.tabla.getModel();
    modeloTabla.setRowCount(0);

    try {
        ArrayList<TipoEntidad> entidades = modelo.getAll();
        int index = 1;

        for (TipoEntidad entidad : entidades) {
            // Búsqueda en múltiples campos (case-insensitive)
            if (entidad.getCampo1().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                entidad.getCampo2().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                entidad.getCampoN().toLowerCase().contains(textoBusqueda.toLowerCase())) {

                Object[] fila = {
                    index++, // Mantener índice secuencial
                    entidad.getCampo1(),
                    entidad.getCampo2(),
                    entidad.getCampoN()
                };
                modeloTabla.addRow(fila);
            }
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(vista, "Error al buscar datos: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
```

### Obtener ID Real para Editar/Eliminar

```java
/**
 * Importante: La primera columna es solo índice visual
 * Para obtener el ID real del registro, usar el Modelo
 */
private void abrirFormularioEditar() {
    int fila = vista.tabla.getSelectedRow();

    if (fila == -1) {
        JOptionPane.showMessageDialog(vista, "Por favor seleccione un registro para editar",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        ArrayList<TipoEntidad> entidades = modelo.getAll();
        if (fila < entidades.size()) {
            // Obtener ID real usando el índice de la fila
            String idEntidad = entidades.get(fila).getId();

            // Abrir formulario de edición con el ID real
            Vista.entidad.FormularioEditarEntidad formulario = new Vista.entidad.FormularioEditarEntidad(new javax.swing.JFrame(), true);
            new FormularioEditarEntidadController(formulario, idEntidad);
            formulario.setVisible(true);

            cargarTabla(); // Recargar después de editar
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(vista, "Error al obtener el registro: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
```

### Estructura Base de cualquier Controlador

```java
public class EntidadController {
    private EntidadVista vista;
    private EntidadModel modelo;

    public EntidadController(EntidadVista vista) {
        this.vista = vista;
        this.modelo = new EntidadModel();
        inicializarEventos();
        cargarTabla();
    }

    private void inicializarEventos() {
        // Botón buscar
        vista.botonBuscar.addActionListener(this::buscar);

        // Botón agregar
        vista.botonAgregar.addActionListener(this::abrirFormularioAgregar);

        // Menú contextual en tabla
        vista.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarMenuContextual(e);
                }
            }
        });
    }

    // Métodos obligatorios:
    // - cargarTabla()
    // - buscar(ActionEvent e)
    // - mostrarMenuContextual(MouseEvent e)
    // - abrirFormularioAgregar(ActionEvent e)
    // - abrirFormularioEditar()
    // - eliminar()
}
```

### Puntos Clave para cualquier Controlador

1. **Separación MVC**: Usa `modelo.getAll()` para obtener datos, nunca SQL directo
2. **Índice Visual**: Primera columna es secuencial (1, 2, 3...), NO es ID
3. **ID Real**: Se obtiene del Modelo usando `entidades.get(fila).getId()`
4. **Búsqueda**: Filtra sobre datos del Modelo, no en base de datos
5. **Tipado**: Usa `TipoEntidad` específico para cada controlador
6. **Formularios**: Abrir formularios con `new javax.swing.JFrame(), true`
7. **Recarga**: Llamar `cargarTabla()` después de operaciones CRUD

## 7. Ventajas de esta Arquitectura

- **Desacoplamiento**: Controladores no dependen de creación de vistas
- **Testabilidad**: Fácil inyectar mocks para testing
- **Mantenimiento**: Cambios en vistas no afectan controladores
- **Reutilización**: Controladores pueden reutilizarse con diferentes vistas
- **Control Centralizado**: El punto de entrada controla todo el ciclo de vida
