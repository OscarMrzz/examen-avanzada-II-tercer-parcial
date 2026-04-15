# Estructura del Punto de Entrada

## Archivo Principal: Examen_tercer_parcial.java

### Propósito

Este es el punto de entrada principal de la aplicación donde se inicializan todos los controladores y vistas.

### Principios de Diseño

#### 1. **Inicialización Centralizada**

- El punto de entrada crea todas las instancias necesarias
- No se crean vistas nuevas dentro de los controladores
- Todas las vistas se reciben como parámetros

#### 2. **Flujo de Creación**

1. Crear todas las vistas necesarias
2. Crear el HomeController con las vistas principales
3. Crear controladores específicos con sus vistas correspondientes
4. Iniciar la aplicación

#### 3. **Estructura de Instanciación**

```java
// Vistas principales
LoginVista loginVista = new LoginVista(null, true);
Home home = new Home();
UsuariosVista usuariosVista = new UsuariosVista(null, true);

// HomeController - recibe todas las vistas principales
HomeController homeController = new HomeController(home, loginVista, usuariosVista);

// Controladores específicos - solo reciben sus vistas relacionadas
UsuariosController usuariosController = new UsuariosController(usuariosVista, home);
```

#### 4. **Responsabilidades del Punto de Entrada**

- Crear instancias de todas las vistas
- Crear instancias de todos los controladores
- Pasar las vistas correctas a cada controlador
- Iniciar la aplicación a través del HomeController

#### 5. **Regla Importante: No Crear Vistas en Controladores**

- Los controladores NUNCA deben crear vistas con `new`
- Todas las vistas se crean en el punto de entrada
- Los controladores solo reciben y manipulan vistas existentes

#### 6. **Jerarquía de Controladores**

- **HomeController**: Gestiona vistas principales y navegación
- **Controladores Específicos**: Gestionan solo sus vistas relacionadas
  - UsuariosController: UsuariosVista, formularios de usuario
  - ClientesController: ClientesVista, formularios de cliente
  - VentasController: VentasVista, formularios de venta
  - etc.

#### 7. **Ejemplo de Implementación**

```java
public static void main(String[] args) {
    // Crear todas las vistas
    LoginVista loginVista = new LoginVista(null, true);
    Home home = new Home();
    UsuariosVista usuariosVista = new UsuariosVista(null, true);
    clientesVista clientesVista = new clientesVista(null, true);
    coleccionesVista coleccionesVista = new coleccionesVista(null, true);
    comprasVista comprasVista = new comprasVista(null, true);
    decoracionesVista decoracionesVista = new decoracionesVista(null, true);
    inventarioVista inventarioVista = new inventarioVista(null, true);
    proveedoresVista proveedoresVista = new proveedoresVista(null, true);
    ventasVista ventasVista = new ventasVista(null, true);

    // Crear HomeController con todas las vistas principales
    HomeController homeController = new HomeController(home, loginVista, usuariosVista,
            clientesVista, coleccionesVista, comprasVista, decoracionesVista,
            inventarioVista, proveedoresVista, ventasVista);

    // Crear controladores específicos con sus vistas (SOLO EN PUNTO DE ENTRADA)
    UsuariosController usuariosController = new UsuariosController(usuariosVista);
    clientesController clientesController = new clientesController(clientesVista);
    coleccionesController coleccionesController = new coleccionesController(coleccionesVista);
    comprasController comprasController = new comprasController(comprasVista);
    decoracionesController decoracionesController = new decoracionesController(decoracionesVista);
    proveedoresController proveedoresController = new proveedoresController(proveedoresVista);
    ventasController ventasController = new ventasController(ventasVista);

    // Iniciar aplicación
    homeController.iniciar();
    homeController.cargarBotones();
}
```

#### 8. **Responsabilidades Claras**

**HomeController:**

- Controla solo la vista Home y sus botones de navegación
- Muestra/oculta vistas directamente
- NO crea controladores específicos
- NO maneja lógica de negocio de otras vistas

**Controladores Específicos (creados en punto de entrada):**

- Manejan la lógica de sus vistas correspondientes
- Controlan botones Agregar, Editar, Eliminar, Buscar
- NO gestionan navegación entre vistas
- Solo reciben su vista asignada

#### 9. **Ventajas de este Enfoque**

- Control centralizado de la creación de objetos
- Inyección de dependencias clara
- Los controladores se enfocan solo en su lógica
- Fácil mantenimiento y testing
- Sin acoplamiento entre controladores para creación de vistas
- Sin duplicación de listeners (un solo controlador por vista)
