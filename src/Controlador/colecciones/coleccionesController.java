package Controlador.colecciones;

import Vista.colecciones.coleccionesVista;
import Vista.colecciones.FormularioAgregarColeccion;
import Vista.colecciones.FormularioEditarColeccion;
import Vista.colecciones.reportesColecciones;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class coleccionesController {
    private coleccionesVista vista;
    private FormularioAgregarColeccion formularioAgregar;
    private FormularioEditarColeccion formularioEditar;
    private reportesColecciones reportes;

    public coleccionesController(coleccionesVista vista, FormularioAgregarColeccion formularioAgregar,
            FormularioEditarColeccion formularioEditar, reportesColecciones reportes) {
        this.vista = vista;
        this.formularioAgregar = formularioAgregar;
        this.formularioEditar = formularioEditar;
        this.reportes = reportes;

        initController();
    }

    private void initController() {
        // Botones de navegación
        vista.botonAgregar.addActionListener(e -> abrirFormularioAgregar());
        vista.botonInforme.addActionListener(e -> abrirReportes());

        // Botones de búsqueda
        vista.botonBuscar.addActionListener(e -> buscarColecciones());

        // Menú contextual
        vista.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarMenuContextual(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarMenuContextual(e);
                }
            }
        });

        // Botones de los formularios
        formularioAgregar.botonGuardar.addActionListener(e -> guardarColeccion());
        formularioAgregar.botonCancelar.addActionListener(e -> formularioAgregar.setVisible(false));

        formularioEditar.botonGuardar.addActionListener(e -> actualizarColeccion());
        formularioEditar.botonCancelar.addActionListener(e -> formularioEditar.setVisible(false));

        // Botones de reportes
        reportes.botonGenerarReporte.addActionListener(e -> generarReporte());
        reportes.botonCancelar.addActionListener(e -> reportes.setVisible(false));
    }

    public void iniciar() {
        vista.setVisible(true);
        cargarColecciones();
    }

    private void abrirFormularioAgregar() {
        limpiarFormularioAgregar();
        formularioAgregar.setVisible(true);
    }

    private void abrirFormularioEditar() {
        // Obtener la colección seleccionada de la tabla
        int filaSeleccionada = vista.tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            // Cargar datos de la colección en el formulario
            cargarDatosColeccionEnFormulario(filaSeleccionada);
            formularioEditar.setVisible(true);
        } else {
            javax.swing.JOptionPane.showMessageDialog(vista,
                    "Por favor seleccione una colección para editar",
                    "Seleccione Colección",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    private void abrirReportes() {
        reportes.setVisible(true);
    }

    private void buscarColecciones() {
        String textoBusqueda = vista.inputBusqueda.getText().toLowerCase();
        // Implementar lógica de búsqueda
        System.out.println("Buscando colecciones: " + textoBusqueda);
    }

    private void guardarColeccion() {
        // Validar campos
        if (validarFormularioAgregar()) {
            // Implementar lógica de guardado
            System.out.println("Guardando colección...");
            formularioAgregar.setVisible(false);
            cargarColecciones();
        }
    }

    private void actualizarColeccion() {
        // Validar campos
        if (validarFormularioEditar()) {
            // Implementar lógica de actualización
            System.out.println("Actualizando colección...");
            formularioEditar.setVisible(false);
            cargarColecciones();
        }
    }

    private void generarReporte() {
        String tipoReporte = (String) reportes.comboBoxTipoReporte.getSelectedItem();
        String fechaInicio = reportes.inputFechaInicio.getText();
        String fechaFin = reportes.inputFechaFin.getText();

        System.out.println("Generando reporte: " + tipoReporte);
        System.out.println("Fecha inicio: " + fechaInicio);
        System.out.println("Fecha fin: " + fechaFin);

        // Implementar lógica de generación de reportes
    }

    private void cargarColecciones() {
        try {
            // Importar el modelo de colecciones
            Modelo.colecciones.ColeccionModel coleccionModel = new Modelo.colecciones.ColeccionModel();

            // Obtener todas las colecciones
            java.util.ArrayList<Type.colecciones.ColeccionType> colecciones = coleccionModel.getAll();

            // Limpiar tabla
            vista.tabla.setModel(new javax.swing.table.DefaultTableModel());

            // Configurar columnas
            javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) vista.tabla.getModel();
            modelo.setColumnIdentifiers(new Object[] {
                    "ID", "Nombre", "Diseñador", "# Colección", "Año", "Descripción", "Estado", "# Decoraciones"
            });

            // Agregar filas con conteo de decoraciones
            for (Type.colecciones.ColeccionType coleccion : colecciones) {
                // Obtener conteo de decoraciones para esta colección
                int conteoDecoraciones = coleccionModel.getConteoDecoracionesPorColeccion(coleccion.getIdColeccion());

                Object[] fila = {
                        coleccion.getIdColeccion(),
                        coleccion.getNombreColeccion(),
                        coleccion.getDisenadorColeccion(),
                        coleccion.getNumColeccionColeccion(),
                        coleccion.getAnioColeccion(),
                        coleccion.getDescripcionColeccion(),
                        coleccion.isEstadoColeccion() ? "Activo" : "Inactivo",
                        conteoDecoraciones
                };

                modelo.addRow(fila);
            }

            System.out.println("Colecciones cargadas correctamente: " + colecciones.size() + " registros");

        } catch (Exception ex) {
            System.out.println("Error al cargar colecciones: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void limpiarFormularioAgregar() {
        formularioAgregar.inputNombreColeccion.setText("");
        formularioAgregar.inputDisenadorColeccion.setText("");
        formularioAgregar.inputNumColeccionColeccion.setText("");
        formularioAgregar.inputAnioColeccion.setText("");
        formularioAgregar.inputDescripcionColeccion.setText("");
    }

    private void cargarDatosColeccionEnFormulario(int fila) {
        // Implementar lógica para cargar datos de la colección seleccionada
        // en el formulario de edición
        System.out.println("Cargando datos de la colección de la fila: " + fila);
    }

    private boolean validarFormularioAgregar() {
        String nombre = formularioAgregar.inputNombreColeccion.getText().trim();
        String disenador = formularioAgregar.inputDisenadorColeccion.getText().trim();
        String numColeccion = formularioAgregar.inputNumColeccionColeccion.getText().trim();
        String anio = formularioAgregar.inputAnioColeccion.getText().trim();

        if (nombre.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El nombre de la colección es obligatorio",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (disenador.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El diseñador es obligatorio",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (numColeccion.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El número de colección es obligatorio",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (anio.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El año es obligatorio",
                    "Campo Requerido",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar que el año sea un número válido
        try {
            int anioNum = Integer.parseInt(anio);
            if (anioNum < 1900 || anioNum > 2100) {
                javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                        "El año debe estar entre 1900 y 2100",
                        "Año Inválido",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(formularioAgregar,
                    "El año debe ser un número válido",
                    "Error de Formato",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean validarFormularioEditar() {
        // Misma validación que el formulario agregar
        return validarFormularioAgregar();
    }

    private void mostrarMenuContextual(MouseEvent e) {
        int fila = vista.tabla.rowAtPoint(e.getPoint());
        if (fila >= 0) {
            vista.tabla.setRowSelectionInterval(fila, fila);
            javax.swing.JPopupMenu menu = new javax.swing.JPopupMenu();

            javax.swing.JMenuItem menuItemEditar = new javax.swing.JMenuItem("Editar");
            menuItemEditar.addActionListener(evt -> abrirFormularioEditar());

            javax.swing.JMenuItem menuItemEliminar = new javax.swing.JMenuItem("Eliminar");
            menuItemEliminar.addActionListener(evt -> eliminar());

            menu.add(menuItemEditar);
            menu.add(menuItemEliminar);
            menu.show(vista.tabla, e.getX(), e.getY());
        }
    }

    private void eliminar() {
        int filaSeleccionada = vista.tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int confirmacion = javax.swing.JOptionPane.showConfirmDialog(vista,
                    "¿Está seguro de que desea eliminar esta colección?",
                    "Confirmar Eliminación",
                    javax.swing.JOptionPane.YES_NO_OPTION);
            if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
                System.out.println("Eliminando colección en la fila: " + filaSeleccionada);
                cargarColecciones();
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(vista,
                    "Por favor seleccione una colección para eliminar",
                    "Seleccione Colección",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    public void volverAlHome() {
        vista.setVisible(false);
    }
}
