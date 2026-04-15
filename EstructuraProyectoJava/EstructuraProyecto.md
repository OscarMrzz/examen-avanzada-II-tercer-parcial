# Estructura del Proyecto - Sistema de Gestión de Decoraciones

## 📁 1. Estructura de Paquetes (NetBeans)

Para mantener la organización solicitada, el proyecto se dividirá en:

- **conexion**: Conexión a MySQL y constantes de diseño
- **modelo**: Clases Entidad (POJOs) y Clases DAO (Acceso a Datos)
- **vista**: Formularios JFrame y JDialog
- **controlador**: Lógica de eventos y mediación entre vista y modelo
- **img**: Imágenes, logos e iconos

---

## 🗄️ 2. Arquitectura de Datos (MySQL)

Esquema relacional necesario para soportar la lógica de privilegios y stocks:

### Tablas Principales

**Usuarios**: id_usuario, nombre_usuario, user_usuario, pass_usuario, privilegio_usuario (ADMIN, VENTAS, INVENTARIO), foto_usuario

**Clientes**: id_cliente, nombre_cliente, rtn_cliente, tipo_cliente (Consumidor Final/Empresa)

**Decoraciones**: id_decoracion, nombre_decoracion, stock_decoracion, precio_costo_decoracion, precio_venta_decoracion, id_proveedor_decoracion, imagen_decoracion, es_coleccion_decoracion (booleano), disenador_decoracion, num_coleccion_decoracion, anio_decoracion

**Proveedores**: id_proveedor, nombre_proveedor, rtn_proveedor, telefono_proveedor, direccion_proveedor

**Facturas_Compra**: id_factura_compra, id_proveedor_factura_compra, total_factura_compra, tipo_pago_factura_compra (Contado/Crédito), estado_factura_compra (Pagada/Pendiente), fecha_factura_compra

**Ventas**: id_venta, id_cliente_venta, subtotal_venta, impuesto_venta, descuento_venta, total_venta, tipo_pago_venta, fecha_venta

**Detalle_Venta**: id_detalle_venta, id_venta_detalle, id_decoracion_detalle, cantidad_detalle, precio_unitario_detalle

---

## � 2.1 Tipos de Datos (Type Classes)

Cada tabla tendrá su correspondiente clase Type con los siguientes atributos:

### UsuarioType

```java
public class UsuarioType {
    private int id_usuario;
    private String nombre_usuario;
    private String user_usuario;
    private String pass_usuario;
    private String privilegio_usuario; // ADMIN, VENTAS, INVENTARIO
    private String foto_usuario;
}
```

### ClienteType

```java
public class ClienteType {
    private int id_cliente;
    private String nombre_cliente;
    private String rtn_cliente;
    private String tipo_cliente; // "Consumidor Final" o "Empresa"
}
```

### DecoracionType

```java
public class DecoracionType {
    private int id_decoracion;
    private String nombre_decoracion;
    private int stock_decoracion;
    private double precio_costo_decoracion;
    private double precio_venta_decoracion;
    private int id_proveedor_decoracion;
    private String imagen_decoracion;
    private boolean es_coleccion_decoracion;
    private String disenador_decoracion;
    private String num_coleccion_decoracion;
    private int anio_decoracion;
}
```

### ProveedorType

```java
public class ProveedorType {
    private int id_proveedor;
    private String nombre_proveedor;
    private String rtn_proveedor;
    private String telefono_proveedor;
    private String direccion_proveedor;
}
```

### FacturaCompraType

```java
public class FacturaCompraType {
    private int id_factura_compra;
    private int id_proveedor_factura_compra;
    private double total_factura_compra;
    private String tipo_pago_factura_compra; // "Contado" o "Crédito"
    private String estado_factura_compra; // "Pagada" o "Pendiente"
    private String fecha_factura_compra;
}
```

### VentaType

```java
public class VentaType {
    private int id_venta;
    private int id_cliente_venta;
    private double subtotal_venta;
    private double impuesto_venta;
    private double descuento_venta;
    private double total_venta;
    private String tipo_pago_venta;
    private String fecha_venta;
}
```

### DetalleVentaType

```java
public class DetalleVentaType {
    private int id_detalle_venta;
    private int id_venta_detalle;
    private int id_decoracion_detalle;
    private int cantidad_detalle;
    private double precio_unitario_detalle;
}
```

---

## �🔧 3.1 Métodos CRUD para cada Modelo

Cada modelo tendrá una clase DAO con los siguientes métodos estándar:

### Métodos CRUD Generales (para todos los modelos)

- **getAll()**: Devuelve una `List<TipoModelo>` con todos los registros
- **get(id)**: Devuelve un objeto `TipoModelo` específico por su ID
- **update(objeto)**: Devuelve `boolean` (true si actualizó correctamente, false si hubo error)
- **delete(id)**: Devuelve `boolean` (true si eliminó correctamente, false si hubo error)

### Modelo Usuario - Métodos Adicionales

- **auth(usuario, password)**: Devuelve un objeto `Usuario` si las credenciales son correctas, `null` si son incorrectas

#### Ejemplo de Implementación - UsuarioDAO

```java
public class UsuarioDAO {
    // Devuelve List<UsuarioType> con todos los usuarios
    public List<UsuarioType> getAll() { ... }

    // Devuelve un solo UsuarioType por ID
    public UsuarioType get(int id_usuario) { ... }

    // Devuelve true si actualizó correctamente
    public boolean update(UsuarioType usuario) { ... }

    // Devuelve true si eliminó correctamente
    public boolean delete(int id_usuario) { ... }

    // Autenticación - devuelve el usuario si credenciales son correctas
    public UsuarioType auth(String user_usuario, String pass_usuario) { ... }
}
```

### Tipos de Retorno Esperados

- **getAll()**: `List<UsuarioType>`, `List<ClienteType>`, `List<DecoracionType>`, etc.
- **get()**: `UsuarioType`, `ClienteType`, `DecoracionType`, etc. (objeto individual)
- **update()**: `boolean` (true/false)
- **delete()**: `boolean` (true/false)
- **auth()**: `UsuarioType` (si autentica) o `null` (si falla)

---

## 🎨 3. Guía de Estilo (UI/UX)

La aplicación debe implementar un Look and Feel personalizado (se recomienda FlatLaf o configuración manual de propiedades):

### Paleta de Colores

- **Primario**: Rojo (#CC0000) para botones de acción y resaltados
- **Fondo**: Gris Oscuro (#333333) o Gris Claro (#E1E1E1)
- **Texto/Contraste**: Negro (#000000)

### Componentes

- **JTable**: Encabezados negros con texto gris, filas alternas
- **JButton**: Fondo rojo, texto blanco, sin bordes toscos
- **Pantalla de Inicio**: JWindow con el logo del negocio y un JProgressBar antes del Login

---

## 🔐 4. Lógica de los Módulos

### Módulo Seguridad (Login)

Verificación en BD mediante PreparedStatement.

**Permisos de Usuario**:

- **ADMIN (ADMIN2025)**: Acceso total
- **Ventas/Clientes**: Solo accede a ClientesVista y VentasVista
- **Inventario/Proveedores**: Solo accede a DecoracionesVista, ProveedoresVista y ComprasVista

### Módulo Decoraciones e Inventario

- **Cálculo de Precio**: `$precio_venta = precio_costo × 1.48`
- **Regla de Colección**: Si es_coleccion es verdadero, los campos de diseñador son obligatorios
- **Validación de Borrado**: Solo ejecutar DELETE si stock == 0

### Módulo Ventas y Facturación

- **Impuesto**: 18%
- **Descuento**: Si cantidad >= 2 y es_coleccion == true, aplicar 10% de descuento al subtotal de esos productos

**Validación de Stock**:

- Si cantidad_venta > stock: Bloquear venta
- Si stock <= stock_minimo: Mostrar JOptionPane de advertencia, pero permitir facturar

---

## 🚀 5. Plan de Implementación para la IA

### Paso 1: Configuración

- Crear el proyecto en NetBeans (Java 8)
- Crear clase Conexion.java en el paquete config
- Importar librerías: mysql-connector-java, JasperReports (iReport), JFreeChart

### Paso 2: Desarrollo del Modelo

- Crear entidades (atributos, getters, setters)
- Crear DAOs con métodos CRUD (Insertar, Consultar, Modificar, Eliminar)

### Paso 3: Desarrollo de la Vista

- Diseñar Splash.java (Logo)
- Diseñar Login.java
- Diseñar MenuPrincipal.java con un JDesktopPane o paneles intercambiables
- Aplicar los colores Rojo, Gris y Negro a los componentes

### Paso 4: Desarrollo del Controlador

- Crear controladores que implementen ActionListener y MouseListener
- Vincular los botones de las Vistas con los métodos de los DAOs
- Programar la lógica de cálculo de impuestos (18%) y descuentos (10%)

### Paso 5: Reportes y Gráficas

- Diseñar archivos .jrxml en iReport para Facturas y Ventas
- Integrar DefaultCategoryDataset para las gráficas de las decoraciones más vendidas

---

## 📊 6. Variables Matemáticas Críticas

- **Impuesto (ISV)**: `$0.18`
- **Margen de Utilidad**: `$0.48`
- **Descuento Colección**: `$0.10`
- **Stock de seguridad**: `$stock_actual ≤ stock_minimo`
