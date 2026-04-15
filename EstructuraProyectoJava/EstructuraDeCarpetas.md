# Estructura de Carpetas del Proyecto - Patrón MVC

## Estructura General del Proyecto

```
examen_tercer_parcial/
├── misLibrerias/                    # Librerías externas (fuera de src)
│   └── mysql-connector-j-8.0.33.jar
├── src/                            # Código fuente principal
│   ├── Modelo/                     # Capa de Modelo
│   ├── Vista/                      # Capa de Vista
│   ├── Controlador/                # Capa de Controlador
│   ├── Type/                       # Tipos de datos para vistas de BD
│   └── (otros archivos raíz)
└── (otros archivos del proyecto)
```

## Reglas de Organización

### 1. Archivos NO Sueltos

- **Ningún archivo .java o .form debe ir directamente en las carpetas principales**
- Todos los archivos deben estar organizados en subcarpetas temáticas
- Cada entidad/tipo debe tener su propia subcarpeta

### 2. Estructura por Entidades

#### Para cada entidad (ej: usuarios, productos, etc.):

```
src/
├── Modelo/
│   ├── usuarios/
│   │   └── UsuarioModel.java
│   ├── productos/
│   │   └── ProductoModel.java
│   ├── init.sql                    # Archivo suelto en Modelo/
│   └── Conexion.java               # Archivo suelto en Modelo/
│
├── Vista/
│   ├── usuarios/
│   │   ├── usuariosVista.java
│   │   ├── usuariosVista.form
│   │   ├── FormularioAgregarUsuario.java
│   │   ├── FormularioAgregarUsuario.form
│   │   ├── FormularioEditarUsuario.java
│   │   ├── FormularioEditarUsuario.form
│   │   ├── reportesUsuarios.java
│   │   └── reportesUsuarios.form
│   └── productos/
│       ├── productosVista.java
│       ├── productosVista.form
│       ├── FormularioAgregarProducto.java
│       ├── FormularioAgregarProducto.form
│       ├── FormularioEditarProducto.java
│       ├── FormularioEditarProducto.form
│       ├── reportesProductos.java
│       └── reportesProductos.form
│
├── Controlador/
│   ├── usuarios/
│   │   ├── usuariosController.java
│   │   ├── FormularioAgregarUsuarioController.java
│   │   ├── FormularioEditarUsuarioController.java
│   │   └── reportesUsuariosController.java
│   └── productos/
│       ├── productosController.java
│       ├── FormularioAgregarProductoController.java
│       ├── FormularioEditarProductoController.java
│       └── reportesProductosController.java
│
└── Type/
    ├── ventas/
    │   ├── VentaType.java
    │   ├── VentaDetalladaType.java
    │   ├── VentaResumidaType.java
    │   └── VentaReporteType.java
    ├── usuarios/
    │   ├── UsuarioType.java
    │   ├── UsuarioListaType.java
    │   └── UsuarioReporteType.java
    └── productos/
        ├── ProductoType.java
        ├── ProductoInventarioType.java
        └── ProductoReporteType.java
```

## Convenciones de Nomenclatura

### Carpetas

- Usar **minúsculas** y **singular** para nombres de carpetas de entidades
- Ejemplo: `usuarios/`, `producto/`, `cliente/`

### Archivos

- **Modelos**: `[Entidad]Model.java`
- **Vistas principales**: `[entidad]Vista.java` y `[entidad]Vista.form`
- **Formularios**: `Formulario[Acción][Entidad].java` y `.form`
- **Controladores**: `[NombreFormulario]Controller.java`
- **Reportes**: `reportes[Entidad].java` y `.form`
- **Types**: `[Entidad][Propósito]Type.java` (para vistas de base de datos)

## Excepciones Importantes

### Archivos que SÍ van sueltos en Modelo/

- `init.sql` - Script de inicialización de base de datos
- `Conexion.java` - Clase de conexión a base de datos

### Librerías Externas

- Todas las librerías .jar van en `misLibrerias/` (fuera de src)

## Ejemplo Completo para Entidad "usuarios"

```
src/
├── Modelo/
│   ├── usuarios/
│   │   └── UsuarioModel.java
│   ├── init.sql
│   └── Conexion.java
│
├── Vista/
│   └── usuarios/
│       ├── usuariosVista.java
│       ├── usuariosVista.form
│       ├── FormularioAgregarUsuario.java
│       ├── FormularioAgregarUsuario.form
│       ├── FormularioEditarUsuario.java
│       ├── FormularioEditarUsuario.form
│       ├── reportesUsuarios.java
│       └── reportesUsuarios.form
│
├── Controlador/
│   └── usuarios/
│       ├── usuariosController.java
│       ├── FormularioAgregarUsuarioController.java
│       ├── FormularioEditarUsuarioController.java
│       └── reportesUsuariosController.java
│
└── Type/
    └── usuarios/
        ├── UsuarioType.java
        ├── UsuarioListaType.java
        └── UsuarioReporteType.java
```

## Propósito de la Carpeta Type

La carpeta `Type/` contiene clases que representan **tipos de datos específicos para vistas de base de datos**. Estas clases se utilizan para:

- Mapear resultados de consultas SQL complejas
- Representar datos estructurados para reportes
- Facilitar el transporte de datos entre capas
- Proporcionar tipado fuerte para vistas personalizadas

### Ejemplos de uso:

- `VentaDetalladaType` - para vista que muestra ventas con todos sus detalles
- `UsuarioReporteType` - para reportes de usuarios con información agregada
- `ProductoInventarioType` - para vista de productos con datos de inventario

## Instrucciones para IA

Al crear o modificar archivos:

1. **Verificar siempre la estructura de carpetas** antes de crear archivos
2. **Crear carpetas si no existen** siguiendo el patrón establecido
3. **Usar nombres consistentes** según las convenciones
4. **Mantener archivos .java y .form juntos** en la misma carpeta
5. **No colocar archivos sueltos** en las carpetas principales (excepto init.sql y Conexion.java en Modelo/)
6. **Seguir el patrón MVC** estrictamente:
   - Modelo: lógica de datos y negocio
   - Vista: interfaces gráficas
   - Controlador: coordinación entre Modelo y Vista
7. **Para tipos de datos de vistas BD**: crear clases en `Type/[entidad]/` con sufijo `Type.java`
