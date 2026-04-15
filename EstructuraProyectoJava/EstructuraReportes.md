# JasperReports Expert Architect (Java 8 & NetBeans Edition)

## Contexto de Ejecución

El objetivo es generar archivos `.jrxml` y el código de integración en Java 8 siguiendo el patrón MVC. La IA debe actuar como un arquitecto que analiza archivos de especificación (como diccionarios de datos o documentos de requerimientos) para determinar la estructura del reporte.

### Búsqueda de Requerimientos

La IA debe buscar primero los siguientes archivos en el proyecto:

1. **README.md** o **READMI.md** - Contiene los requerimientos generales del sistema
2. **requerimientos.md** - Documento específico de requerimientos (si existe)

Basarse en la información encontrada para identificar los reportes necesarios según los módulos del sistema.

---

## 1. Análisis de Requerimientos (Filtros y Parámetros)

La IA debe buscar en el proyecto etiquetas o definiciones de filtros para configurar los parameters del reporte:

### Filtros de Rango (Fechas):

- **Definir parámetros** `$P{fechaInicio}` y `$P{fechaFin}` de tipo `java.util.Date`.
- **Cláusula SQL**: `WHERE columna_fecha BETWEEN $P{fechaInicio} AND $P{fechaFin}`.

### Filtros por Categoría (Selección):

- **Definir parámetro** `$P{idCategoria}` de tipo `java.lang.Integer`.
- **Cláusula SQL**: `WHERE id_categoria = $P{idCategoria}`.

### Filtros Opcionales (Dinámicos):

- Si el requerimiento indica que un filtro puede ser nulo, usar:
  ```sql
  WHERE ($P{idCategoria} IS NULL OR id_categoria = $P{idCategoria})
  ```

---

## 2. Clasificación de Tipos de Reporte

Según la especificación leída, la IA seleccionará una de estas estructuras:

| Tipo de Reporte   | Estructura JRXML                     | Uso Típico                                            |
| ----------------- | ------------------------------------ | ----------------------------------------------------- |
| Listado Simple    | Column Header + Detail               | Inventarios, listas de usuarios                       |
| Agrupado (Groups) | Group Header + Detail + Group Footer | Reportes de ventas por categoría o por fecha          |
| Maestro-Detalle   | Subreports dentro del Detail         | Facturas con múltiples ítems o historiales académicos |
| Estadístico       | Summary Band (Gráficos/Charts)       | Resúmenes de rendimiento o métricas anuales           |

---

## 3. Protocolo de Construcción SQL

La IA debe construir el Query basándose en el esquema de base de datos del proyecto:

- **SELECT**: Solo campos necesarios (evitar `SELECT *`).
- **JOINs**: Vincular tablas según las llaves foráneas detectadas en las especificaciones.
- **ORDER BY**: Siempre incluir un ordenamiento para reportes agrupados.

---

## 4. Implementación en Java 8 (NetBeans)

Para la ejecución desde el IDE, la IA debe generar un método de control que siga este flujo:

```java
public void generarReporte(Connection conn, Map<String, Object> parametros) {
    try {
        // 1. Cargar el archivo compilado (.jasper)
        InputStream reporteFile = getClass().getResourceAsStream("/reportes/mi_reporte.jasper");

        // 2. Llenar el reporte con datos y parámetros
        JasperPrint jprint = JasperFillManager.fillReport(reporteFile, parametros, conn);

        // 3. Lanzar el visor de Jasper (JasperViewer)
        JasperViewer viewer = new JasperViewer(jprint, false);
        viewer.setVisible(true);
    } catch (JRException e) {
        e.printStackTrace();
    }
}
---

## 4.1 Librerías necesarias (obligatorio) - JasperReports en NetBeans (Java 8)

Para que compile/ejecute un `.jrxml` en runtime (y se abra `JasperViewer`) **debes** agregar las librerías `.jar` al proyecto desde NetBeans.

### Ubicación recomendada en el proyecto

- Guardar los `.jar` en `misLibrerias/` (fuera de `src/`), por ejemplo:
  - `misLibrerias/jasperreports-6.20.6/lib/*.jar`
  - `misLibrerias/mysql-connector-j-8.0.33.jar` (si el reporte usa BD MySQL)

### Dónde descargar (fuentes recomendadas)

- **Maven Central (descarga directa de .jar)**:
  - JasperReports principal:
    - `https://repo1.maven.org/maven2/net/sf/jasperreports/jasperreports/6.20.6/jasperreports-6.20.6.jar`
  - Dependencias comunes (suelen ser necesarias cuando compilas `.jrxml`):
    - `commons-logging-1.1.1.jar`
      - `https://repo1.maven.org/maven2/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar`
    - `commons-digester-2.1.jar`
      - `https://repo1.maven.org/maven2/commons-digester/commons-digester/2.1/commons-digester-2.1.jar`
    - `commons-beanutils-1.9.4.jar`
      - `https://repo1.maven.org/maven2/commons-beanutils/commons-beanutils/1.9.4/commons-beanutils-1.9.4.jar`
    - `commons-collections4-4.2.jar`
      - `https://repo1.maven.org/maven2/org/apache/commons/commons-collections4/4.2/commons-collections4-4.2.jar`
    - Charts (gráficas):
      - `jfreechart-1.0.19.jar` → `https://repo1.maven.org/maven2/org/jfree/jfreechart/1.0.19/jfreechart-1.0.19.jar`
      - `jcommon-1.0.23.jar` → `https://repo1.maven.org/maven2/org/jfree/jcommon/1.0.23/jcommon-1.0.23.jar`
    - Compilación:
      - `ecj-3.21.0.jar` → `https://repo1.maven.org/maven2/org/eclipse/jdt/ecj/3.21.0/ecj-3.21.0.jar`
    - PDF:
      - `openpdf-1.3.30.jar` → `https://repo1.maven.org/maven2/com/github/librepdf/openpdf/1.3.30/openpdf-1.3.30.jar`
    - XML/JSON (frecuente en Jasper):
      - `jackson-core-2.14.1.jar` → `https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.14.1/jackson-core-2.14.1.jar`
      - `jackson-annotations-2.14.1.jar` → `https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.14.1/jackson-annotations-2.14.1.jar`
      - `jackson-databind-2.14.1.jar` → `https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.14.1/jackson-databind-2.14.1.jar`
      - `jackson-dataformat-xml-2.14.1.jar` → `https://repo1.maven.org/maven2/com/fasterxml/jackson/dataformat/jackson-dataformat-xml/2.14.1/jackson-dataformat-xml-2.14.1.jar`
    - SVG (frecuente por soporte de gráficos):
      - `batik-anim-1.17.jar` → `https://repo1.maven.org/maven2/org/apache/xmlgraphics/batik-anim/1.17/batik-anim-1.17.jar`
      - `batik-awt-util-1.17.jar` → `https://repo1.maven.org/maven2/org/apache/xmlgraphics/batik-awt-util/1.17/batik-awt-util-1.17.jar`
      - `batik-bridge-1.17.jar` → `https://repo1.maven.org/maven2/org/apache/xmlgraphics/batik-bridge/1.17/batik-bridge-1.17.jar`
      - `batik-dom-1.17.jar` → `https://repo1.maven.org/maven2/org/apache/xmlgraphics/batik-dom/1.17/batik-dom-1.17.jar`
      - `batik-gvt-1.17.jar` → `https://repo1.maven.org/maven2/org/apache/xmlgraphics/batik-gvt/1.17/batik-gvt-1.17.jar`
      - `batik-svg-dom-1.17.jar` → `https://repo1.maven.org/maven2/org/apache/xmlgraphics/batik-svg-dom/1.17/batik-svg-dom-1.17.jar`
      - `batik-svggen-1.17.jar` → `https://repo1.maven.org/maven2/org/apache/xmlgraphics/batik-svggen/1.17/batik-svggen-1.17.jar`

### Cómo agregarlas en NetBeans

1. Clic derecho al proyecto → **Properties**
2. **Libraries** → **Add JAR/Folder…**
3. Seleccionar **todos** los `.jar` necesarios (recomendado agregarlos desde `misLibrerias/jasperreports-6.20.6/lib/`)

### Regla práctica de diagnóstico

- Si sale `NoClassDefFoundError: ...` entonces falta un `.jar` en Libraries. La solución correcta es **agregar el `.jar` que contiene esa clase** (descargado desde Maven Central o incluido en el `lib/`).

## 5. Reglas de Formato y Estilo

- **Cero comentarios**: No incluir explicaciones dentro del código fuente generado.
- **Internacionalización**: Si el reporte requiere títulos dinámicos, usar parámetros para las etiquetas.
- **Precisión de Tipos**: Asegurar que los tipos de datos en los parámetros de Jasper coincidan estrictamente con los tipos de `java.sql`.

---

## Instrucción para la IA

"Busca primero los archivos README.md, READMI.md o requerimientos.md en el directorio del proyecto. Lee las especificaciones encontradas para identificar los módulos del sistema y los reportes requeridos. Identifica las tablas involucradas y los filtros solicitados (fechas, categorías, IDs). Genera el código SQL para el JRXML y el método Java correspondiente para llamar el reporte desde NetBeans, asegurando que no haya comentarios en el código."
```
