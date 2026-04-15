# ManualDeRevicion.md

Manual de revisión **obligatorio** para este proyecto (Java 8 + NetBeans Swing + MVC + MySQL).

## Cómo usar este manual
- **Objetivo**: que cualquier IA (o persona) pueda revisar el proyecto de forma consistente y detectar errores típicos (estructura, MVC, DB, UI, reportes).
- **Regla**: revisar en el orden de este documento. No “arreglar” nada sin pasar por los puntos base (DB → Type → Modelo → Controlador → Vista → Punto de entrada).

---

## 1) Revisión de requisitos del proyecto (al final, pero conocidos desde el inicio)
**Referencia**: `READMI.md` (requerimientos funcionales/UX).

- **UI/UX**:
  - FlatLaf Dark aplicado.
  - Colores: **rojo, gris, negro**.
  - Existe **pantalla de inicio con logo antes del login**.
- **Login real contra BD** (no hardcode):
  - Usuario admin: `ADMIN` / `ADMIN2025`.
- **Privilegios**:
  - `ADMIN` (todo + único que crea usuarios y asigna privilegios).
  - `VENTAS` (clientes + ventas + reportes correspondientes).
  - `INVENTARIO` (decoraciones + proveedores/compras/inventario + reportes correspondientes).
- **CRUD obligatorio**:
  - Usuarios (incluye **foto**).
  - Clientes (incluye RTN; permite “Consumidor Final”).
  - Proveedores (CRUD + pagos/recibos + reportes).
  - Decoraciones (CRUD + imagen; reglas de colección; eliminar solo si no hay existencias).
  - Compras/Inventario (compras alimentan inventario; stock min/max; utilidad 48%).
  - Ventas (facturación, impuestos 18%, descuento colección 10% en reglas; no inventario negativo).
- **Reportes (Jasper/iReport)**:
  - Decoraciones en existencia.
  - Decoraciones más vendidas (tabla + gráfica).
  - Compras por fechas.
  - Inventario general y por rango de fechas.
  - Ventas diarias y por fechas (tabla + gráfica).
  - Factura de venta.

> Nota: estos requisitos se validan **al final** en la sección 10, pero cualquier cambio debe mantenerlos.

---

## 2) Estructura física del proyecto (carpetas y ubicación)
**Referencia**: `EstructuraDeCarpetas.md`.

- **Estructura base**:
  - `src/Modelo/`, `src/Vista/`, `src/Controlador/`, `src/Type/`
  - `misLibrerias/` para `.jar` (fuera de `src`).
- **No archivos sueltos** en `Vista/`, `Controlador/`, `Type/` raíz:
  - Todo en subcarpetas por entidad (minúsculas, singular).
- **Excepciones permitidas**:
  - `src/Modelo/init.sql`
  - `src/Modelo/Conexion.java`
- **NetBeans**:
  - Cada vista/formulario debe tener su **`.java` y `.form`** juntos en la misma carpeta.

Checklist:
- [ ] Cada entidad tiene su subcarpeta en Modelo/Vista/Controlador/Type.
- [ ] No hay `.java` suelto en raíces prohibidas.
- [ ] `.form` siempre acompaña a su `.java`.

---

## 3) Base de datos: `init.sql` y coherencia total
**Referencias**: `src/Modelo/init.sql`, `EstructuraModelo.md`, `ExtruturaType.md`.

- **Validar que `init.sql` se ejecuta completo** y crea:
  - BD `db_decoraciones`
  - Tablas principales
  - Vistas SQL necesarias para reportes/tablas detalladas
  - Datos de ejemplo (si aplica)
- **Validar nombres exactos** de tablas/columnas/ENUM:
  - Todo el código (Model/Type/Controller) debe coincidir con `init.sql` sin inventar nombres.
- **Reglas de estado (eliminación lógica)**:
  - Ver que cada entidad tenga `estado_*` (boolean) y que `getAll()` filtre activos si aplica.

Checklist:
- [ ] `Conexion.java` apunta a `db_decoraciones` y credenciales correctas.
- [ ] Tablas/columnas usadas en queries existen con el mismo nombre.
- [ ] ENUMs de DB están mapeados (ver sección 4).
- [ ] Vistas SQL para reportes existen y funcionan en MySQL 8+.

---

## 4) Types (mapeo de datos: tablas y vistas SQL)
**Referencia**: `ExtruturaType.md`.

- **Cada tabla/vista SQL** usada por el sistema debe tener su `Type` correspondiente.
- **Campos**:
  - Deben representar columnas SQL necesarias (tabla o vista).
  - Tipos Java correctos (`String`, `int`, `BigDecimal`, `Date/Timestamp`, `boolean`, `Enum`).
- **Enums**:
  - Deben soportar valores de BD (ej. “Consumidor Final”, “Crédito”, “Mixto”, etc.).
  - Evitar depender de `Enum.valueOf(valorDB)` si el DB no guarda el nombre exacto de la constante.

Checklist:
- [ ] Existe `Type` para cada entidad principal.
- [ ] Existe `Type` para cada vista SQL de reportes/detallado.
- [ ] Enums: valores y conversión DB→Enum están definidos.

---

## 5) Modelos (DAO/CRUD) y consistencia con `init.sql`
**Referencia**: `EstructuraModelo.md`.

Reglas:
- Modelo **extiende `Conexion`**.
- CRUD estándar:
  - `create()`, `getById()`, `getAll()`, `update()`, `delete()` (preferir eliminación lógica).
- Uso de `PreparedStatement`.
- Cierra conexión en `finally`.

Validaciones obligatorias:
- **Queries**:
  - `SELECT/INSERT/UPDATE` usan columnas exactas del SQL.
- **Enums**:
  - Conversión segura (DB→Enum) para evitar `IllegalArgumentException` silencioso.
- **Filtros por estado**:
  - `getAll()` debe filtrar activos si la entidad maneja estado.

Checklist:
- [ ] `getAll()` retorna datos reales y no falla por enums.
- [ ] `delete()` respeta eliminación lógica donde corresponda.
- [ ] No hay lógica de UI en Modelo.

---

## 6) Vistas principales (JDialog) - estructura y componentes públicos
**Referencia**: `EstructuraVistas.md`.

Reglas:
- Extienden `JDialog`.
- Componentes **públicos**.
- Tabla principal: `public JTable tabla`.
- Buscador: `inputBusqueda` + `botonBuscar`.
- Botón: `botonAgregar` (y `botonInforme` si aplica).
- Encabezado: label grande en mayúsculas.
- **Sin lógica de negocio** en la vista.

Checklist:
- [ ] `tabla` se llama exactamente `tabla`.
- [ ] Todos los componentes usados por el controlador son `public`.
- [ ] La tabla tiene columnas correctas (ver sección 7).

---

## 7) Tablas (JTable): carga, columnas, índice NO, menú contextual, refrescos
**Referencia**: `EstructuraDeTablas.md`.

Obligatorio:
- Primera columna siempre **`NO`** (índice secuencial), **no ID**.
- Menú contextual (click derecho): **Editar** y **Eliminar**.
- Refresco:
  - al iniciar vista
  - después de agregar/editar/eliminar
- El ID real se obtiene desde la lista del modelo (`modelo.getAll().get(fila).getId...()`), no desde la columna `NO`.

Checklist:
- [ ] Columnas en la vista coinciden con el `Object[] fila` del controlador.
- [ ] Un click abre módulo una sola vez; no hay listeners duplicados.
- [ ] `cargarTabla()` se ejecuta al abrir el módulo.

---

## 8) Formularios (Agregar/Editar): nombres, campos, combobox, imágenes
**Referencia**: `EstructuraFormularios.md` + `EstructuraControladorFormulario.md`.

Reglas:
- `FormularioAgregar[Entidad]` y `FormularioEditar[Entidad]` (JDialog + `.form`).
- Componentes públicos.
- **No incluir** campos automáticos (IDs, timestamps, estados por defecto).
- Nomenclatura:
  - `input[Campo]`, `comboBox[Entidad]`, `botonGuardar`, `botonCancelar`
- Si la BD tiene campo de imagen:
  - el formulario debe tener campo/acción para **cargar/buscar imagen** y vista previa (label).
- Si hay `JComboBox` de FK/relaciones:
  - debe existir método `cargarCombos()` en el controlador y no quedar vacío.
  - el formato recomendado: `"ID - Nombre - ..."` y extraer ID con `split(" - ")[0]`.

Checklist:
- [ ] Campos del formulario corresponden a `init.sql` (solo manuales).
- [ ] Combobox se cargan con datos reales del modelo.
- [ ] Imagen: existe botón para cargar + ruta se guarda y se previsualiza.

---

## 9) Controladores: MVC estricto e inyección de vistas
**Referencia**: `EstructuraControlador.md`.

Reglas:
- **Prohibido** `new` de vistas dentro de controladores.
- Las vistas se **inyectan por constructor**.
- Un solo controlador debe “dominar” cada vista (evitar listeners duplicados).
- El controlador:
  - registra listeners
  - llama modelo CRUD
  - carga tabla (y refresca)
  - abre formularios (inyectados) y pasa IDs reales para editar

Checklist:
- [ ] Ningún controlador instancia una vista principal.
- [ ] No hay doble `addActionListener()` para el mismo botón desde dos controladores.
- [ ] `cargarTabla()` existe y se ejecuta.

---

## 10) Punto de entrada: creación centralizada de vistas y controladores
**Referencia**: `EstructuraPuntoDeEntrada.md`.

Obligatorio:
- En `main()`:
  - se crea **todas** las vistas (JDialogs/JFrame)
  - se crea **todos** los controladores con inyección
  - se inicia desde `HomeController`
- Evitar duplicidad: no crear dos controladores que manejen la misma navegación.

Checklist:
- [ ] `main()` crea vistas una vez.
- [ ] Los controladores reciben vistas por constructor.
- [ ] Home inicia y los módulos se abren sin duplicarse.

---

## 11) Eliminación: reglas de negocio + eliminación lógica
**Referencia**: `EstructuraEliminar.md` + requerimientos de `READMI.md`.

Reglas:
- Eliminar con confirmación.
- Preferir eliminación lógica usando `estado_*`.
- Regla específica: **Decoraciones solo se eliminan si no tienen existencias** (stock = 0).

Checklist:
- [ ] `delete()` respeta estado.
- [ ] Decoraciones valida stock antes de permitir eliminar.
- [ ] Tabla se refresca tras eliminar.

---

## 12) Reportes (Jasper/iReport): existencia, queries y permisos
**Referencia**: `EstructuraReportes.md`.

Reglas:
- Reportes se basan en:
  - requerimientos (`READMI.md`)
  - tablas/vistas de `init.sql`
- Query:
  - evitar `SELECT *` (cuando sea posible)
  - soportar parámetros (fechas/rangos)
- Acceso:
  - los usuarios no admin solo ven reportes según su módulo/privilegio.

Checklist:
- [ ] Existen reportes exigidos y se abren desde los módulos.
- [ ] Queries usan tablas/vistas reales.
- [ ] Parámetros (fechaInicio/fechaFin) funcionan.

---

## 13) Revisión final (obligatoria): cumplir `READMI.md` completo
**Referencia**: `READMI.md`.

Al terminar todo lo anterior, validar uno por uno los requisitos:
- Login real por BD.
- Privilegios y restricciones.
- CRUD de usuarios con foto.
- CRUD clientes con RTN y “Consumidor Final”.
- Decoraciones: imagen, reglas de colección, eliminación por stock.
- Proveedores + pagos + recibos + reportes.
- Compras alimentan inventario; utilidad 48%; stock min/max.
- Ventas: 18% ISV, descuento 10% colección (2+), stock mínimo alerta, no inventario negativo, factura iReport, cambio y pagos mixtos.
- Reportes: ventas diarias/por fechas, decoraciones, compras por fechas, inventario general/por fechas, gráficas donde se pide.

