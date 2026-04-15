# Índice de Estructura del Proyecto Java

## Guía de Inicio Rápido

### Antes de Empezar Cualquier Tarea

1. **Revisar Estructura de Carpetas**: Siempre consultar `EstructuraDeCarpetas.md`
2. **Verificar Convenciones**: Seguir estrictamente las nomenclaturas establecidas
3. **Validar Patrón MVC**: Mantener separación clara entre Modelo, Vista y Controlador

---

## Flujo de Trabajo por Tipo de Tarea

### Para Crear Vistas Principales

**Referencia**: `EstructuraVistas.md`

**Pasos a seguir**:

1. Revisar `EstructuraDeCarpetas.md` para ubicación correcta
2. Identificar tabla en `init.sql` y Type classes correspondientes
3. Crear archivos `[Entidad]Vista.java` y `[Entidad]Vista.form`
4. Aplicar estructura visual estándar (encabezado, tabla, buscador, botones)
5. Seguir nomenclatura: `tabla`, `inputBusqueda`, `botonBuscar`, `botonAgregar`
6. Asegurar todos los componentes sean públicos

**Checklist final**:

- [ ] Extiende de JDialog
- [ ] Nombre sigue patrón `[Entidad]Vista`
- [ ] Encabezado con nombre en mayúsculas
- [ ] Tabla principal llamada `tabla`
- [ ] Buscador con `inputBusqueda` y `botonBuscar`
- [ ] Botón `botonAgregar` presente
- [ ] Botón `botonInforme` si aplica
- [ ] Todos los componentes son públicos
- [ ] Sin lógica de negocio
- [ ] Archivo .form correspondiente creado
- [ ] Ubicación correcta según `EstructuraDeCarpetas.md`

### Para Crear Formularios

**Referencia**: `EstructuraFormularios.md`

**Pasos a seguir**:

1. Revisar `EstructuraDeCarpetas.md` para ubicación correcta
2. Analizar tabla en `init.sql` para identificar campos manuales vs automáticos
3. Revisar Type classes para confirmar estructura de datos
4. Crear `FormularioAgregar[Entidad].java` y `.form`
5. Diseñar visualmente con campos manuales únicamente
6. Aplicar nomenclatura: `input[Campo]`, `comboBox[Entidad]`, `boton[Acción]`
7. Duplicar para crear `FormularioEditar[Entidad]`
8. Refactorizar nombre y título del formulario de edición

**Checklist final**:

- [ ] Extiende de JDialog
- [ ] Nombre sigue patrón `FormularioAgregar[Entidad]` / `FormularioEditar[Entidad]`
- [ ] Título indica "Agregar [Entidad]" o "Editar [Entidad]"
- [ ] Campos manuales incluidos según init.sql
- [ ] Campos automáticos excluidos (IDs, timestamps, estados)
- [ ] Nomenclatura `input[Campo]` aplicada correctamente
- [ ] Todos los componentes son públicos
- [ ] Sin lógica de negocio ni validaciones
- [ ] Archivo .form correspondiente creado
- [ ] Ubicación correcta según `EstructuraDeCarpetas.md`

### Para Crear Reportes

**Referencia**: `EstructuraReportes.md`

**Pasos a seguir**:

1. Buscar README.md o READMI.md para identificar requerimientos de reportes
2. Analizar módulos del sistema para determinar reportes necesarios
3. Identificar tablas y filtros requeridos (fechas, categorías, IDs)
4. Construir query SQL según protocolo establecido
5. Clasificar tipo de reporte (Listado, Agrupado, Maestro-Detalle, Estadístico)
6. Generar archivo .jrxml sin comentarios
7. Crear método Java para ejecución desde NetBeans

**Checklist final**:

- [ ] Requisitos identificados desde README.md
- [ ] Query SQL construido correctamente
- [ ] Tipo de reporte clasificado apropiadamente
- [ ] Parámetros definidos según filtros
- [ ] Código .jrxml sin comentarios
- [ ] Método Java de integración creado
- [ ] Tipos de datos coinciden con java.sql

---

## Validación General de Estructura

### Checklist de Cumplimiento de Proyecto

**Al finalizar CUALQUIER tarea, verificar**:

#### Estructura de Carpetas

- [ ] Archivos organizados en subcarpetas temáticas
- [ ] Ningún archivo .java o .form suelto en carpetas principales
- [ ] Carpetas con nombres en minúsculas y singular
- [ ] Archivos .java y .form juntos en misma carpeta

#### Convenciones de Nomenclatura

- [ ] Modelos: `[Entidad]Model.java`
- [ ] Vistas: `[entidad]Vista.java` y `.form`
- [ ] Formularios: `Formulario[Acción][Entidad].java` y `.form`
- [ ] Controladores: `[NombreFormulario]Controller.java`
- [ ] Reportes: `reportes[Entidad].java` y `.form`
- [ ] Types: `[Entidad][Propósito]Type.java`

#### Principios MVC

- [ ] Modelo: solo lógica de datos y negocio
- [ ] Vista: solo interfaces gráficas, componentes públicos
- [ ] Controlador: coordinación entre Modelo y Vista
- [ ] Sin conexión directa a BD desde vistas
- [ ] Sin lógica de negocio en vistas

#### Archivos Especiales

- [ ] `init.sql` y `Conexion.java` sueltos en Modelo/
- [ ] Librerías .jar en `misLibrerias/` (fuera de src)

---

## Documentación de Referencia

| Archivo                    | Propósito                         | Cuándo consultar                    |
| -------------------------- | --------------------------------- | ----------------------------------- |
| `EstructuraDeCarpetas.md`  | Organización física del proyecto  | **SIEMPRE** antes de crear archivos |
| `EstructuraFormularios.md` | Patrones para formularios JDialog | Al crear formularios Agregar/Editar |
| `EstructuraVistas.md`      | Patrones para vistas principales  | Al crear vistas de listado          |
| `EstructuraReportes.md`    | Guía para JasperReports           | Al crear reportes                   |
| `EstructuraModelo.md`      | Skill CRUD y patrones del Modelo  | **SIEMPRE** al crear modelos CRUD   |
| `EstructuraProyecto.md`    | Arquitectura general y datos      | Para entender contexto del sistema  |

---

## Advertencias Críticas

### Errores Comunes a Evitar

1. **NO colocar archivos sueltos** en carpetas principales
2. **NO incluir campos automáticos** en formularios (IDs, timestamps)
3. **NO agregar lógica de negocio** en vistas
4. **NO olvidar hacer públicos** los componentes
5. **NO mezclar convenciones** de nomenclatura
6. **NO crear archivos .form** separados del .java

### Recordatorios de Calidad

- **Siempre verificar estructura** antes de crear
- **Mantener consistencia** en nombres y patrones
- **Seguir MVC estrictamente** sin excepciones
- **Documentar cambios** si se desvía del estándar
- **Validar ubicación** según `EstructuraDeCarpetas.md`

---

## Flujo de Trabajo Resumido

```
INICIO TAREA
    |
    v
Revisar EstructuraDeCarpetas.md
    |
    v
Identificar archivo guía específico
    |
    v
Seguir pasos según tipo de tarea
    |
    v
Aplicar checklist de validación
    |
    v
Verificar estructura general del proyecto
    |
    v
TAREA COMPLETADA
```

**Recuerda**: La calidad y consistencia del proyecto dependen del seguimiento estricto de estas guías.
