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

    // Controladores específicos
    proveedoresController proveedoresController;
    clientesController clientesController;
    decoracionesController decoracionesController;
    coleccionesController coleccionesController;
    comprasController comprasController;
    ventasController ventasController;

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

        // Crear controladores específicos con sus vistas
        this.proveedoresController = new proveedoresController(proveedoresVista, home);
        this.clientesController = new clientesController(clientesVista, home);
        this.decoracionesController = new decoracionesController(decoracionesVista, home);
        this.coleccionesController = new coleccionesController(coleccionesVista, home);
        this.comprasController = new comprasController(comprasVista, home);
        this.ventasController = new ventasController(ventasVista, home);
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

## 6. Ventajas de esta Arquitectura

- **Desacoplamiento**: Controladores no dependen de creación de vistas
- **Testabilidad**: Fácil inyectar mocks para testing
- **Mantenimiento**: Cambios en vistas no afectan controladores
- **Reutilización**: Controladores pueden reutilizarse con diferentes vistas
- **Control Centralizado**: El punto de entrada controla todo el ciclo de vida
