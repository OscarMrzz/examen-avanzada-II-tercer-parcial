## Requisitos (Java 8 + NetBeans)

- **Java**: 8
- **MySQL Connector**: ya tienes `mysql-connector-j-8.0.33.jar`
- **JasperReports**: debes agregar la librería al proyecto (NetBeans).

Este proyecto ejecuta los reportes **compilando el `.jrxml` en runtime**, por eso **no necesitas** `.jasper` en el repo.

## Dónde están los reportes

- Los `.jrxml` están en `src/reportes/`
- El código que los ejecuta está en `src/Modelo/reportes/JasperService.java`

## Reportes implementados

- `decoraciones_existencia.jrxml`
- `decoraciones_mas_vendidas.jrxml` (incluye gráfica)
- `inventario_general.jrxml`
- `compras_por_fechas.jrxml`
- `ventas_diarias.jrxml` (incluye gráfica)
- `ventas_por_fechas.jrxml` (incluye gráfica)
- `factura_venta.jrxml` (maestro-detalle por `numeroFacturaVenta`)
- `proveedores_listado.jrxml`

## Agregar JasperReports en NetBeans (sin subir JARs al repo)

### Opción A: usar un “JasperReports Library” local

1. Descarga una versión **compatible con Java 8** (ejemplo: **6.20.x**) y extrae el ZIP en tu PC.
2. En NetBeans:
   - **Tools → Libraries**
   - **New Library…** (nombre sugerido: `JasperReports_6_20_Java8`)
   - **Add JAR/Folder…**
   - Selecciona los JARs (mínimo):
     - `jasperreports-6.20.x.jar`
     - sus dependencias que vienen en el mismo ZIP (carpeta `lib/` del paquete)
3. Luego en tu proyecto:
   - **Project Properties → Libraries**
   - **Add Library…** → selecciona `JasperReports_6_20_Java8`

### Opción B (rápida): reutilizar las librerías de Jaspersoft Studio / iReport

1. Instala **Jaspersoft Studio** (o iReport si ya lo tienes).
2. En NetBeans: **Project Properties → Libraries → Add JAR/Folder…**
3. Agrega todos los `.jar` de la carpeta `lib/` de la instalación.

## Prueba de ejecución (manual)

1. Ejecuta la app desde `src/examen_tercer_parcial/Examen_tercer_parcial.java`.
2. En cada módulo:
   - **Ventas / Compras / Decoraciones / Inventario / Proveedores**
   - Click en **Ver Informe**
   - Click en **Generar Reporte**
3. Si aparece `ClassNotFoundException: net.sf.jasperreports...`:
   - faltan JARs de JasperReports en el classpath del proyecto.
4. Si aparece error de SQL:
   - confirma que ejecutaste `src/Modelo/init.sql` en MySQL, base `db_decoraciones`.
   - verifica que `Modelo/Conexion.java` conecta correctamente.

