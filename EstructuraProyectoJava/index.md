# Índice de Estructura del Proyecto Java - Guía para IA

## 🚀 Guía Rápida para Inteligencia Artificial

### 📍 Antes de Cualquier Tarea - Lectura Obligatoria

**Si te piden hacer una REVISIÓN / AUDITORÍA del proyecto →** `ManualDeRevicion.md`
**Si te piden crear TABLAS →** `EstructuraDeTablas.md`
**Si te piden crear VISTAS →** `EstructuraVistas.md`
**Si te piden crear FORMULARIOS →** `EstructuraFormularios.md`
**Si te piden crear CONTROLADORES →** `EstructuraControlador.md`
**Si te piden crear REPORTES →** `EstructuraReportes.md`
**Si te piden organizar CARPETAS →** `EstructuraDeCarpetas.md`

---

## 🎯 Flujo de Decisión por Tipo de Petición

### ✅ Para HACER UNA REVISIÓN COMPLETA DEL PROYECTO

**Referencia principal**: `ManualDeRevicion.md`

**Cuándo usar**: Cuando el usuario diga “revisa mi proyecto”, “audita”, “asegúrate que todo esté bien”, “haz una revisión completa”

**Objetivo**: Seguir el checklist para validar estructura, DB, Types, Modelos, Controladores, Vistas, tablas, formularios, reportes y requisitos del proyecto.

**Redirección específica**: Ver `ManualDeRevicion.md`

---

### 📊 Para TRABAJAR CON TABLAS

**Referencia principal**: `EstructuraDeTablas.md`

**Cuándo usar**: Siempre que te mencionen tablas, JTable, listados de datos

**Elementos obligatorios que debes implementar**:

- Columna NO como índice
- Resaltado de fila completa al seleccionar
- Menú contextual (click derecho) con editar y eliminar
- Métodos de refresco después de cada operación CRUD
- Refresco al iniciar la vista

**Redirección específica**: Ver `EstructuraDeTablas.md` para código completo

---

### 🖼️ Para CREAR VISTAS PRINCIPALES

**Referencia principal**: `EstructuraVistas.md`

**Cuándo usar**: Cuando te pidan ventanas principales con listados

**Características obligatorias**:

- Extender de JDialog
- Nombre: `[Entidad]Vista.java`
- Encabezado con nombre en mayúsculas
- Tabla principal llamada `tabla`
- Buscador con `inputBusqueda` y `botonBuscar`
- Botón `botonAgregar`
- Todos los componentes públicos

**Redirección específica**: Ver `EstructuraVistas.md` para estructura visual completa

---

### 📝 Para CREAR FORMULARIOS (Agregar/Editar)

**Referencia principal**: `EstructuraFormularios.md`

**Cuándo usar**: Cuando te pidan formularios de agregar o editar datos

**Características obligatorias**:

- Extender de JDialog
- Nombres: `FormularioAgregar[Entidad].java` y `FormularioEditar[Entidad].java`
- Títulos: "Agregar [Entidad]" o "Editar [Entidad]"
- Campos manuales según init.sql (NO automáticos)
- Nomenclatura: `input[Campo]`, `comboBox[Entidad]`, `boton[Acción]`
- Todos los componentes públicos

**Redirección específica**: Ver `EstructuraFormularios.md` para validaciones y estructura

---

### 🎮 Para CREAR CONTROLADORES

**Referencia principal**: `EstructuraControlador.md`

**Cuándo usar**: Cuando te pidan crear o modificar controladores

**Reglas estrictas**:

- NO CREAR VISTAS (solo recibirlas por constructor)
- Inyección de dependencias obligatoria
- Responsabilidad única (solo vistas relacionadas)
- Constructor con vistas como parámetros

**Redirección específica**: Ver `EstructuraControlador.md` para patrones arquitectónicos

---

### 📈 Para CREAR REPORTES

**Referencia principal**: `EstructuraReportes.md`

**Cuándo usar**: Cuando te pidan reportes, informes o JasperReports

**Proceso obligatorio**:

- Identificar requisitos desde README.md
- Construir query SQL según protocolo
- Clasificar tipo de reporte
- Generar .jrxml sin comentarios
- Crear método Java para ejecución

**Redirección específica**: Ver `EstructuraReportes.md` para protocolo completo

---

### 🗂️ Para ORGANIZACIÓN DE CARPETAS

**Referencia principal**: `EstructuraDeCarpetas.md`

**Cuándo usar**: Siempre antes de crear cualquier archivo

**Reglas estrictas**:

- Subcarpetas temáticas obligatorias
- Nombres en minúsculas y singular
- Archivos .java y .form juntos
- Ningún archivo suelto en carpetas principales

**Redirección específica**: Ver `EstructuraDeCarpetas.md` para estructura física

---

### 🏠 Para PUNTO DE ENTRADA E INICIALIZACIÓN

**Referencia principal**: `EstructuraPuntoDeEntrada.md`

**Cuándo usar**: Cuando te pidan configurar el inicio de la aplicación

**Proceso obligatorio**:

- Crear todas las vistas en main()
- Crear HomeController con vistas principales
- Inyectar dependencias correctamente
- Iniciar aplicación a través de HomeController

**Redirección específica**: Ver `EstructuraPuntoDeEntrada.md` para inicialización

---

### 🗃️ Para MODELOS Y BASE DE DATOS

**Referencia principal**: `EstructuraModelo.md`

**Cuándo usar**: Cuando te pidan crear modelos, DAOs o trabajar con base de datos

**Elementos clave**:

- Skill CRUD para patrones del Modelo
- Estructura de entidades
- Conexión a base de datos
- Operaciones CRUD estándar

**Redirección específica**: Ver `EstructuraModelo.md` para patrones CRUD

---

## 🚨 ADVERTENCIAS CRÍTICAS - Errores a Evitar

### ❌ NUNCA HAGAS ESTO:

1. **NO crear archivos sueltos** en carpetas principales
2. **NO incluir campos automáticos** en formularios (IDs, timestamps)
3. **NO agregar lógica de negocio** en vistas
4. **NO olvidar hacer públicos** los componentes
5. **NO crear vistas en controladores** (solo recibir por parámetro)
6. **NO mezclar convenciones** de nomenclatura

### ✅ SIEMPRE HAZ ESTO:

1. **SIEMPRE revisar `EstructuraDeCarpetas.md`** antes de crear
2. **SIEMPRE seguir MVC estrictamente**
3. **SIEMPRE mantener consistencia** en nombres
4. **SIEMPLE validar ubicación** según estructura
5. **SIEMPRE consultar el archivo guía específico** para cada tarea

---

## 📋 MAPA DE NAVEGACIÓN RÁPIDA

| Petición del Usuario    | Archivo de Referencia         | Acción Inmediata                                              |
| ----------------------- | ----------------------------- | ------------------------------------------------------------- |
| "Crear tabla"           | `EstructuraDeTablas.md`       | Implementar columna NO, resaltado, menú contextual, refrescos |
| "Crear vista principal" | `EstructuraVistas.md`         | Crear [Entidad]Vista con tabla, buscador, botones             |
| "Crear formulario"      | `EstructuraFormularios.md`    | Crear FormularioAgregar/Editar con campos manuales            |
| "Crear controlador"     | `EstructuraControlador.md`    | Recibir vistas por constructor, NO crear                      |
| "Crear reporte"         | `EstructuraReportes.md`       | Query SQL → .jrxml → método Java                              |
| "Organizar carpetas"    | `EstructuraDeCarpetas.md`     | Subcarpetas temáticas, archivos juntos                        |
| "Iniciar aplicación"    | `EstructuraPuntoDeEntrada.md` | Crear vistas → HomeController → inyección                     |
| "Crear modelo/DAO"      | `EstructuraModelo.md`         | Skill CRUD, patrones estándar                                 |
| "Revisar proyecto"      | `ManualDeRevicion.md`         | Seguir checklist completo de revisión                          |

---

## 🔄 FLUJO DE TRABAJO AUTOMÁTICO

```
RECIBIR PETICIÓN
    |
    v
IDENTIFICAR TIPO DE TAREA (tabla/vista/formulario/controlador/reporte)
    |
    v
IR AL ARCHIVO GUÍA ESPECÍFICO (según tabla arriba)
    |
    v
SEGUIR PASOS OBLIGATORIOS DEL ARCHIVO GUÍA
    |
    v
APLICAR CHECKLIST DE VALIDACIÓN
    |
    v
VERIFICAR ESTRUCTURA GENERAL
    |
    v
TAREA COMPLETADA
```

---

## 📚 ARCHIVOS DE REFERENCIA COMPLETA

- `ManualDeRevicion.md` - Checklist obligatorio para revisar el proyecto completo
- `EstructuraDeTablas.md` - Guía completa para tablas con resaltado, menú contextual y refrescos
- `EstructuraVistas.md` - Patrones para vistas principales con componentes estándar
- `EstructuraFormularios.md` - Formularios Agregar/Editar con validaciones
- `EstructuraControlador.md` - Arquitectura de controladores con inyección de dependencias
- `EstructuraReportes.md` - Protocolo completo para JasperReports
- `EstructuraDeCarpetas.md` - Organización física del proyecto
- `EstructuraPuntoDeEntrada.md` - Inicialización y punto de entrada
- `EstructuraModelo.md` - Patrones CRUD y modelos de datos
- `EstructuraProyecto.md` - Contexto general y arquitectura del sistema

---

**🎯 OBJETIVO**: Este índice es tu mapa de navegación. Identifica el tipo de tarea y ve directamente al archivo guía correspondiente. No improvises, sigue las estructuras establecidas.
