# Estructura de Type - Tipos de Datos para Vistas de Base de Datos

## Propósito de la Carpeta Type

La carpeta `Type/` contiene clases que representan **tipos de datos específicos para vistas de base de datos**. Estas clases se utilizan para:

- Mapear resultados de consultas SQL complejas
- Representar datos estructurados para reportes
- Facilitar el transporte de datos entre capas
- Proporcionar tipado fuerte para vistas personalizadas

## Estructura de Carpetas Type

```
src/
Type/
    Type/
    ventas/
    usuarios/
    clientes/
    proveedores/
    decoraciones/
    compras/
    inventario/
    reportes/
```

## Convenciones de Nomenclatura

### Nombres de Clases Type

- **Formato**: `[Entidad][Propósito]Type.java`
- **Ejemplos**:
  - `UsuarioType.java` - Datos básicos de usuario
  - `VentaDetalladaType.java` - Vista detallada de ventas
  - `DecoracionMasVendidaType.java` - Vista de decoraciones más vendidas
  - `InventarioDetalladoType.java` - Vista de inventario con detalles

### Carpetas por Entidad

Cada entidad debe tener su propia carpeta en `Type/`:

```
Type/
usuarios/
    UsuarioType.java
    UsuarioListaType.java
    UsuarioReporteType.java

ventas/
    VentaType.java
    VentaDetalladaType.java
    VentaDiariaType.java

clientes/
    ClienteType.java
    ClienteReporteType.java

proveedores/
    ProveedorType.java
    ProveedorReporteType.java

decoraciones/
    DecoracionType.java
    DecoracionMasVendidaType.java
    InventarioDetalladoType.java

compras/
    FacturaCompraType.java
    CompraDetalladaType.java
    DetalleCompraType.java

reportes/
    VentaDiariaType.java
    InventarioDetalladoType.java
    DecoracionMasVendidaType.java
```

## Estructura de una Clase Type

### Elementos Obligatorios

1. **Atributos privados** para cada campo de la vista/tabla
2. **Getters y Setters** para cada atributo
3. **Constructor vacío** (por defecto)
4. **Constructor con parámetros** (opcional, recomendado)
5. **toString()** (opcional, para debugging)

### Ejemplo de Estructura

```java
package Type.ventas;

public class VentaDetalladaType {
    // Atributos privados
    private String id_venta;
    private String numero_factura_venta;
    private Date fecha_venta;
    private String nombre_cliente;
    private String rtn_cliente;
    private String tipo_cliente;
    private String telefono_cliente;
    private String vendedor;
    private BigDecimal subtotal_venta;
    private BigDecimal impuesto_venta;
    private BigDecimal descuento_venta;
    private BigDecimal total_venta;
    private String tipo_pago_venta;
    private BigDecimal monto_efectivo;
    private BigDecimal monto_tarjeta;
    private BigDecimal cambio_venta;
    private String estado_venta;
    private Integer cantidad_productos;

    // Constructor vacío
    public VentaDetalladaType() {}

    // Constructor con parámetros
    public VentaDetalladaType(String id_venta, String numero_factura_venta, ...) {
        this.id_venta = id_venta;
        this.numero_factura_venta = numero_factura_venta;
        // ... inicializar otros atributos
    }

    // Getters y Setters
    public String getId_venta() { return id_venta; }
    public void setId_venta(String id_venta) { this.id_venta = id_venta; }

    public String getNumero_factura_venta() { return numero_factura_venta; }
    public void setNumero_factura_venta(String numero_factura_venta) { this.numero_factura_venta = numero_factura_venta; }

    // ... resto de getters y setters

    @Override
    public String toString() {
        return "VentaDetalladaType{" +
                "id_venta='" + id_venta + '\'' +
                ", numero_factura_venta='" + numero_factura_venta + '\'' +
                ", fecha_venta=" + fecha_venta +
                ", nombre_cliente='" + nombre_cliente + '\'' +
                ", total_venta=" + total_venta +
                '}';
    }
}
```

## Mapeo de Vistas SQL a Types

### Vistas Principales del init.sql

| Vista SQL                         | Type Correspondiente             | Ubicación            |
| --------------------------------- | -------------------------------- | -------------------- |
| `vista_detallada_ventas`          | `VentaDetalladaType.java`        | `Type/ventas/`       |
| `vista_detallada_detalle_ventas`  | `DetalleVentaDetalladaType.java` | `Type/ventas/`       |
| `vista_detallada_compras`         | `CompraDetalladaType.java`       | `Type/compras/`      |
| `vista_detallada_inventario`      | `InventarioDetalladoType.java`   | `Type/inventario/`   |
| `vista_decoraciones_mas_vendidas` | `DecoracionMasVendidaType.java`  | `Type/decoraciones/` |
| `vista_ventas_diarias`            | `VentaDiariaType.java`           | `Type/reportes/`     |

### Tablas Principales

| Tabla SQL         | Type Correspondiente     | Ubicación            |
| ----------------- | ------------------------ | -------------------- |
| `usuarios`        | `UsuarioType.java`       | `Type/usuarios/`     |
| `clientes`        | `ClienteType.java`       | `Type/clientes/`     |
| `proveedores`     | `ProveedorType.java`     | `Type/proveedores/`  |
| `decoraciones`    | `DecoracionType.java`    | `Type/decoraciones/` |
| `ventas`          | `VentaType.java`         | `Type/ventas/`       |
| `detalle_venta`   | `DetalleVentaType.java`  | `Type/ventas/`       |
| `facturas_compra` | `FacturaCompraType.java` | `Type/compras/`      |
| `detalle_compra`  | `DetalleCompraType.java` | `Type/compras/`      |
| `recibos_pago`    | `ReciboPagoType.java`    | `Type/compras/`      |

## Tipos de Datos Especiales (Enums)

### Enums para Campos con Valores Fijos

```java
package Type;

public enum PrivilegioUsuario {
    ADMIN,
    VENTAS,
    INVENTARIO
}

public enum TipoCliente {
    CONSUMIDOR_FINAL,
    EMPRESA
}

public enum TipoPago {
    CONTADO,
    CREDITO
}

public enum TipoPagoVenta {
    EFECTIVO,
    TARJETA,
    MIXTO
}

public enum EstadoVenta {
    ACTIVA,
    CANCELADA
}

public enum EstadoFactura {
    PAGADA,
    PENDIENTE,
    VENCIDA
}

public enum MetodoPago {
    EFECTIVO,
    TRANSFERENCIA,
    CHEQUE
}
```

## Reglas de Creación

### 1. Mapeo Exacto

- Los nombres de atributos deben coincidir exactamente con los nombres de columnas SQL
- Usar tipos de datos Java apropiados:
  - `String` para VARCHAR, TEXT
  - `int` o `Integer` para INT
  - `BigDecimal` para DECIMAL
  - `Date` o `Timestamp` para DATE, TIMESTAMP
  - `boolean` o `Boolean` para BOOLEAN
  - `Enum` para campos ENUM

### 2. Convenciones Java

- Nombres de atributos en camelCase
- Nombres de métodos getter/setter en camelCase
- Paquetes en minúsculas

### 3. Organización por Entidad

- Cada entidad tiene su propia carpeta
- Types de vistas detalladas van en la carpeta de la entidad principal
- Types de reportes van en carpeta `reportes/`

## Ejemplo Completo de Estructura

```
src/
Type/
    ventas/
        VentaType.java
        VentaDetalladaType.java
        DetalleVentaType.java
        DetalleVentaDetalladaType.java

    usuarios/
        UsuarioType.java
        UsuarioListaType.java
        UsuarioReporteType.java

    clientes/
        ClienteType.java
        ClienteReporteType.java

    proveedores/
        ProveedorType.java
        ProveedorReporteType.java

    decoraciones/
        DecoracionType.java
        DecoracionMasVendidaType.java
        InventarioDetalladoType.java

    compras/
        FacturaCompraType.java
        CompraDetalladaType.java
        DetalleCompraType.java
        ReciboPagoType.java

    reportes/
        VentaDiariaType.java
        InventarioDetalladoType.java
        DecoracionMasVendidaType.java

    enums/
        PrivilegioUsuario.java
        TipoCliente.java
        TipoPago.java
        TipoPagoVenta.java
        EstadoVenta.java
        EstadoFactura.java
        MetodoPago.java
```

## Instrucciones para Desarrollo

1. **Analizar la vista/tabla SQL** antes de crear el Type
2. **Crear la carpeta** si no existe siguiendo la estructura
3. **Definir atributos** con tipos correctos y nombres exactos
4. **Generar getters y setters** para todos los atributos
5. **Agregar constructores** (vacío y con parámetros)
6. **Implementar toString()** para debugging
7. **Usar enums** para campos con valores fijos
8. **Seguir convenciones de nomenclatura** consistentemente

## Validación

Cada Type debe ser validado para asegurar:

- [ ] Todos los campos de la vista/tabla están mapeados
- [ ] Tipos de datos correctos
- [ ] Getters y setters completos
- [ ] Nombres de atributos coinciden con columnas SQL
- [ ] Ubicación correcta en estructura de carpetas
- [ ] Convenciones de nomenclatura seguidas
