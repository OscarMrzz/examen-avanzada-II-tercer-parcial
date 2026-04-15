# AGENTS.md

## Project Overview

Java Swing desktop app (NetBeans, Java 8) for a decoration store. MVC architecture with packages `Modelo`, `Vista`, `Controlador`, `Type`. Uses FlatLaf Dark theme.

## Build & Run

- **Build**: NetBeans Ant (`clean and build`). `build.xml` imports `nbproject/build-impl.xml`.
- **Run**: `Examen_tercer_parcial.main()` (main class in `src/examen_tercer_parcial/`).
- **Libraries**: JARs go in `misLibrerias/` (outside `src/`). Currently: `mysql-connector-j-8.0.33.jar`, `flatlaf-2.6.jar`, `flatlaf-extras-2.6.jar`, `jsvg-1.0.0.jar`.
- **DB init**: Run `src/Modelo/init.sql` in MySQL first. DB name: `db_decoraciones`, user: `root`, pass: `12345678`.

## Key Business Rules

- **ISV (tax)**: 18%
- **Margin**: precio_venta = precio_costo × 1.48
- **Collection discount**: 10% if buying 2+ items from the same collection
- **Stock min warning**: Alert when stock <= stock_minimo (still allows sale)
- **Admin login**: user=`ADMIN`, pass=`ADMIN2025`
- **Privileges**: ADMIN (full), VENTAS (clients + sales), INVENTARIO (decorations + purchases + suppliers)

## Architecture Rules

- **Controllers receive views by constructor injection** — never `new` a view inside a controller.
- **Vistas extend `JDialog`** and are created in `Examen_tercer_parcial.main()`. All components must be `public`.
- **First table column is a sequential index** (`NO`), not an ID. Real IDs come from `modelo.getAll().get(rowIndex).getId()`.
- **No loose files** in `Modelo/`, `Vista/`, `Controlador/`, `Type/` root — use subfolders by entity (singular, lowercase).
- `.java` and `.form` files stay together in the same folder.

## Reference Files

This repo has extensive Spanish documentation in `EstructuraProyectoJava/`:
- `index.md` — ALWAYS read this first (before planning or changes)
- `index.md` — Quick reference map for every file type
- `EstructuraDeCarpetas.md` — Mandatory folder/subfolder structure
- `EstructuraVistas.md` — JDialog patterns (component naming, NO/tabla/inputBusqueda/botonBuscar/botonAgregar)
- `EstructuraControlador.md` — Controller patterns (dependency injection, table loading, menu context)
- `EstructuraModelo.md` — DAO/Model CRUD patterns
- `EstructuraReportes.md` — JasperReports integration
- `EstructuraFormularios.md` — Add/Edit form patterns

## Planning rule (mandatory)

- Before any implementation or planning, **read `EstructuraProyectoJava/index.md` first** and base the plan on the referenced guide file(s) for the task type (tables/views/forms/controllers/reports/review/etc.).

Also read `EstructuraProyecto.md` and `PlanDeImplementacion.md` for business logic constants and implementation status.

## UI Colors

Red (#CC0000), Gray (#333333), Black (#000000). Applied via FlatLaf Dark + manual component coloring.
