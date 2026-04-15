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

## 5. Reglas de Formato y Estilo

- **Cero comentarios**: No incluir explicaciones dentro del código fuente generado.
- **Internacionalización**: Si el reporte requiere títulos dinámicos, usar parámetros para las etiquetas.
- **Precisión de Tipos**: Asegurar que los tipos de datos en los parámetros de Jasper coincidan estrictamente con los tipos de `java.sql`.

---

## Instrucción para la IA

"Busca primero los archivos README.md, READMI.md o requerimientos.md en el directorio del proyecto. Lee las especificaciones encontradas para identificar los módulos del sistema y los reportes requeridos. Identifica las tablas involucradas y los filtros solicitados (fechas, categorías, IDs). Genera el código SQL para el JRXML y el método Java correspondiente para llamar el reporte desde NetBeans, asegurando que no haya comentarios en el código."
```
