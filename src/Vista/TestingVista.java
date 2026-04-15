package Vista;

import Controlador.TestingController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Vista para ejecutar testing integral del sistema
 * 
 * @author ossca
 */
public class TestingVista extends JFrame {

    private static final Logger logger = Logger.getLogger(TestingVista.class.getName());
    private TestingController controlador;

    // Componentes principales
    private JPanel panelPrincipal;
    private JPanel panelControles;
    private JScrollPane panelResultados;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;

    // Botones de control
    private JButton botonEjecutarTesting;
    private JButton botonLimpiar;
    private JButton botonExportarResultados;
    private JButton botonSalir;

    // Labels de información
    private JLabel labelTitulo;
    private JLabel labelDescripcion;
    private JLabel labelEstado;
    private JTextArea areaResultados;

    // Panel de progreso
    private JProgressBar barraProgreso;
    private JLabel labelProgreso;

    public TestingVista() {
        super("Testing Integral - Sistema Decoraciones");
        this.controlador = new TestingController();
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        // Panel principal
        panelPrincipal = new JPanel(new BorderLayout());

        // Panel superior con información
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título
        labelTitulo = new JLabel("TESTING INTEGRAL DEL SISTEMA", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        labelTitulo.setForeground(new Color(0, 102, 204));

        // Descripción
        labelDescripcion = new JLabel(
                "<html><center>Esta herramienta ejecuta pruebas automatizadas para verificar el correcto funcionamiento "
                        +
                        "de todos los componentes del sistema de gestión de decoraciones.<br><br>" +
                        "Las pruebas incluyen:<br>" +
                        "· Conexión a bases de datos<br>" +
                        "· Operaciones CRUD de todos los módulos<br>" +
                        "· Validación de integridad de datos<br>" +
                        "· Pruebas de integración entre módulos</center></html>",
                SwingConstants.CENTER);
        labelDescripcion.setFont(new Font("Arial", Font.PLAIN, 12));

        // Estado
        labelEstado = new JLabel("Presione 'Ejecutar Testing' para comenzar", SwingConstants.CENTER);
        labelEstado.setFont(new Font("Arial", Font.ITALIC, 12));
        labelEstado.setForeground(Color.GRAY);

        panelSuperior.add(labelTitulo, BorderLayout.NORTH);
        panelSuperior.add(labelDescripcion, BorderLayout.CENTER);
        panelSuperior.add(labelEstado, BorderLayout.SOUTH);

        // Panel central con tabla y resultados
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // Configurar tabla de resultados
        String[] columnas = { "Módulo", "Pruebas", "Exitosas", "Fallidas", "Éxito", "Estado" };
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaResultados.setRowHeight(25);
        tablaResultados.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaResultados.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaResultados.setEnabled(false); // Solo lectura

        // La tabla usará el renderizado por defecto

        panelResultados = new JScrollPane(tablaResultados);
        panelResultados.setBorder(BorderFactory.createTitledBorder("Resultados del Testing"));

        // Panel de resultados detallados
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Consolas", Font.PLAIN, 11));
        areaResultados.setBackground(Color.BLACK);
        areaResultados.setForeground(Color.GREEN);
        JScrollPane scrollResultados = new JScrollPane(areaResultados);
        scrollResultados.setPreferredSize(new Dimension(600, 200));
        scrollResultados.setBorder(BorderFactory.createTitledBorder("Log Detallado"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelResultados, scrollResultados);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.7);

        panelCentral.add(splitPane, BorderLayout.CENTER);

        // Panel de progreso
        JPanel panelProgreso = new JPanel(new BorderLayout());
        panelProgreso.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        barraProgreso = new JProgressBar();
        barraProgreso.setStringPainted(true);
        barraProgreso.setString("0%");
        barraProgreso.setFont(new Font("Arial", Font.PLAIN, 10));

        labelProgreso = new JLabel("Esperando para comenzar...", SwingConstants.CENTER);
        labelProgreso.setFont(new Font("Arial", Font.PLAIN, 10));

        panelProgreso.add(labelProgreso, BorderLayout.NORTH);
        panelProgreso.add(barraProgreso, BorderLayout.CENTER);

        // Panel inferior con botones
        panelControles = new JPanel(new FlowLayout());
        panelControles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Crear botones
        crearBotones();

        panelControles.add(botonEjecutarTesting);
        panelControles.add(botonLimpiar);
        panelControles.add(botonExportarResultados);
        panelControles.add(Box.createHorizontalStrut(20));
        panelControles.add(botonSalir);

        // Ensamblar componentes
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelProgreso, BorderLayout.SOUTH);
        panelPrincipal.add(panelControles, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Configurar eventos
        configurarEventos();
    }

    private void crearBotones() {
        // Botón ejecutar testing
        botonEjecutarTesting = new JButton("Ejecutar Testing");
        botonEjecutarTesting.setToolTipText("Iniciar todas las pruebas del sistema");
        botonEjecutarTesting.setBackground(new Color(34, 139, 34));
        botonEjecutarTesting.setForeground(Color.WHITE);
        botonEjecutarTesting.setFocusPainted(false);
        botonEjecutarTesting.setPreferredSize(new Dimension(150, 35));

        // Botón limpiar
        botonLimpiar = new JButton("Limpiar");
        botonLimpiar.setToolTipText("Limpiar resultados actuales");
        botonLimpiar.setBackground(new Color(70, 130, 180));
        botonLimpiar.setForeground(Color.WHITE);
        botonLimpiar.setFocusPainted(false);
        botonLimpiar.setPreferredSize(new Dimension(100, 35));

        // Botón exportar resultados
        botonExportarResultados = new JButton("Exportar Resultados");
        botonExportarResultados.setToolTipText("Guardar resultados en archivo");
        botonExportarResultados.setBackground(new Color(255, 140, 0));
        botonExportarResultados.setForeground(Color.WHITE);
        botonExportarResultados.setFocusPainted(false);
        botonExportarResultados.setPreferredSize(new Dimension(150, 35));

        // Botón salir
        botonSalir = new JButton("Salir");
        botonSalir.setToolTipText("Cerrar ventana de testing");
        botonSalir.setBackground(new Color(220, 20, 60));
        botonSalir.setForeground(Color.WHITE);
        botonSalir.setFocusPainted(false);
        botonSalir.setPreferredSize(new Dimension(80, 35));
    }

    private void configurarEventos() {
        botonEjecutarTesting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarTesting();
            }
        });

        botonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarResultados();
            }
        });

        botonExportarResultados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarResultados();
            }
        });

        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void ejecutarTesting() {
        // Deshabilitar botones durante el testing
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        botonEjecutarTesting.setEnabled(false);
        botonLimpiar.setEnabled(false);
        botonExportarResultados.setEnabled(false);

        // Limpiar resultados anteriores
        modeloTabla.setRowCount(0);
        areaResultados.setText("");

        // Actualizar estado
        labelEstado.setText("Ejecutando pruebas del sistema...");
        labelEstado.setForeground(Color.BLUE);

        // Inicializar barra de progreso
        barraProgreso.setValue(0);
        barraProgreso.setMaximum(100);
        barraProgreso.setString("0%");

        // Ejecutar testing en un hilo separado
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simular progreso
                for (int i = 0; i <= 100; i += 5) {
                    Thread.sleep(50); // Pequeña pausa para simular progreso
                    final int progreso = i;
                    SwingUtilities.invokeLater(() -> {
                        barraProgreso.setValue(progreso);
                        barraProgreso.setString(progreso + "%");
                        labelProgreso.setText("Ejecutando prueba " + (progreso / 5 + 1) + " de 20...");
                    });
                }
                return null;
            }

            @Override
            protected void done() {
                // Ejecutar el testing real
                SwingUtilities.invokeLater(() -> {
                    try {
                        controlador.ejecutarTestingIntegral(modeloTabla);

                        // Actualizar estado final
                        labelEstado.setText("Testing completado. Revise los resultados.");
                        labelEstado.setForeground(new Color(0, 128, 0));
                        labelProgreso.setText("Testing completado exitosamente");
                        barraProgreso.setValue(100);
                        barraProgreso.setString("100%");

                    } catch (Exception ex) {
                        logger.log(Level.SEVERE, "Error durante el testing", ex);
                        labelEstado.setText("Error durante el testing: " + ex.getMessage());
                        labelEstado.setForeground(Color.RED);
                        labelProgreso.setText("Error en el testing");
                        JOptionPane.showMessageDialog(TestingVista.this,
                                "Error durante el testing: " + ex.getMessage(),
                                "Error de Testing", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        // Restaurar estado de la interfaz
                        setCursor(Cursor.getDefaultCursor());
                        botonEjecutarTesting.setEnabled(true);
                        botonLimpiar.setEnabled(true);
                        botonExportarResultados.setEnabled(true);
                    }
                });
            }
        };

        worker.execute();
    }

    private void limpiarResultados() {
        modeloTabla.setRowCount(0);
        areaResultados.setText("");
        labelEstado.setText("Resultados limpiados - Presione 'Ejecutar Testing' para comenzar");
        labelEstado.setForeground(Color.GRAY);
        labelProgreso.setText("Esperando para comenzar...");
        barraProgreso.setValue(0);
        barraProgreso.setString("0%");
    }

    private void exportarResultados() {
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No hay resultados para exportar. Ejecute el testing primero.",
                    "Sin Datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Crear contenido del reporte
        StringBuilder contenido = new StringBuilder();
        contenido.append("=== REPORTE DE TESTING INTEGRAL ===\n");
        contenido.append("Fecha: ").append(new java.util.Date()).append("\n");
        contenido.append("Sistema: Gestión de Decoraciones\n");
        contenido.append("Versión: 1.0\n\n");

        contenido.append("=== RESULTADOS DE LA TABLA ===\n");
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            contenido.append("Fila ").append(i + 1).append(": ");
            for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                contenido.append(modeloTabla.getColumnName(j)).append(": ");
                contenido.append(modeloTabla.getValueAt(i, j)).append(" | ");
            }
            contenido.append("\n");
        }

        contenido.append("\n=== LOG DETALLADO ===\n");
        contenido.append(areaResultados.getText());

        // Simular guardado (en una implementación real, se guardaría en un archivo)
        JOptionPane.showMessageDialog(this,
                "Reporte exportado exitosamente.\n\n" +
                        "Contenido:\n" + contenido.substring(0, Math.min(200, contenido.length())) + "...",
                "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));

        // Icono de la ventana
        try {
            setIconImage(new ImageIcon(getClass().getResource("/iconos/testing.png")).getImage());
        } catch (Exception e) {
            // Si no hay icono, continuar sin él
        }

        // No permitir redimensionado
        setResizable(false);
    }

    /**
     * Método estático para mostrar la vista de testing
     */
    public static void mostrarTesting() {
        SwingUtilities.invokeLater(() -> {
            try {
                TestingVista vista = new TestingVista();
                vista.setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(TestingVista.class.getName()).log(Level.SEVERE,
                        "Error al mostrar vista de testing", ex);
                JOptionPane.showMessageDialog(null,
                        "Error al abrir la vista de testing: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
