package Vista;

import Controlador.ReportesController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Vista principal para generar todos los reportes del sistema
 * 
 * @author ossca
 */
public class ReportesVista extends JFrame {

    private static final Logger logger = Logger.getLogger(ReportesVista.class.getName());
    private ReportesController controlador;
    
    // Componentes principales
    private JPanel panelPrincipal;
    private JPanel panelBotones;
    private JScrollPane panelTabla;
    private JTable tablaReportes;
    private DefaultTableModel modeloTabla;
    
    // Botones de reportes
    private JButton botonReporteUsuarios;
    private JButton botonReporteClientes;
    private JButton botonReporteProveedores;
    private JButton botonReporteColecciones;
    private JButton botonReporteDecoraciones;
    private JButton botonReporteCompras;
    private JButton botonReporteVentas;
    private JButton botonReporteInventario;
    private JButton botonReporteStockBajo;
    private JButton botonExportarCSV;
    private JButton botonLimpiar;
    
    // Labels de información
    private JLabel labelTitulo;
    private JLabel labelInformacion;

    public ReportesVista() {
        super("Sistema de Reportes - Decoraciones");
        this.controlador = new ReportesController();
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        // Panel principal
        panelPrincipal = new JPanel(new BorderLayout());
        
        // Panel de botones
        panelBotones = new JPanel(new GridLayout(0, 3, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de información
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        
        // Título
        labelTitulo = new JLabel("SISTEMA DE REPORTES", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setForeground(new Color(0, 102, 204));
        
        // Información
        labelInformacion = new JLabel("Seleccione un reporte para generar", SwingConstants.CENTER);
        labelInformacion.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Configurar tabla
        String[] columnas = {"ID", "Nombre", "Descripción", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaReportes = new JTable(modeloTabla);
        tablaReportes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaReportes.setRowHeight(25);
        tablaReportes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaReportes.setFont(new Font("Arial", Font.PLAIN, 11));
        
        panelTabla = new JScrollPane(tablaReportes);
        panelTabla.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        // Crear botones
        crearBotones();
        
        // Agregar botones al panel
        panelBotones.add(botonReporteUsuarios);
        panelBotones.add(botonReporteClientes);
        panelBotones.add(botonReporteProveedores);
        panelBotones.add(botonReporteColecciones);
        panelBotones.add(botonReporteDecoraciones);
        panelBotones.add(botonReporteCompras);
        panelBotones.add(botonReporteVentas);
        panelBotones.add(botonReporteInventario);
        panelBotones.add(botonReporteStockBajo);
        
        // Panel de acciones
        JPanel panelAcciones = new JPanel(new FlowLayout());
        panelAcciones.add(botonExportarCSV);
        panelAcciones.add(botonLimpiar);
        
        // Ensamblar componentes
        panelInfo.add(labelTitulo, BorderLayout.NORTH);
        panelInfo.add(labelInformacion, BorderLayout.CENTER);
        panelInfo.add(panelAcciones, BorderLayout.SOUTH);
        
        panelPrincipal.add(panelInfo, BorderLayout.NORTH);
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        // Configurar eventos
        configurarEventos();
    }

    private void crearBotones() {
        // Botones de reportes
        botonReporteUsuarios = new JButton("Usuarios");
        botonReporteUsuarios.setToolTipText("Generar reporte de usuarios con privilegios");
        botonReporteUsuarios.setBackground(new Color(70, 130, 180));
        botonReporteUsuarios.setForeground(Color.WHITE);
        botonReporteUsuarios.setFocusPainted(false);
        
        botonReporteClientes = new JButton("Clientes");
        botonReporteClientes.setToolTipText("Generar reporte de clientes con RTN y tipo");
        botonReporteClientes.setBackground(new Color(60, 179, 113));
        botonReporteClientes.setForeground(Color.WHITE);
        botonReporteClientes.setFocusPainted(false);
        
        botonReporteProveedores = new JButton("Proveedores");
        botonReporteProveedores.setToolTipText("Generar reporte de proveedores");
        botonReporteProveedores.setBackground(new Color(255, 140, 0));
        botonReporteProveedores.setForeground(Color.WHITE);
        botonReporteProveedores.setFocusPainted(false);
        
        botonReporteColecciones = new JButton("Colecciones");
        botonReporteColecciones.setToolTipText("Generar reporte de colecciones");
        botonReporteColecciones.setBackground(new Color(148, 0, 211));
        botonReporteColecciones.setForeground(Color.WHITE);
        botonReporteColecciones.setFocusPainted(false);
        
        botonReporteDecoraciones = new JButton("Decoraciones");
        botonReporteDecoraciones.setToolTipText("Generar reporte de decoraciones con stock");
        botonReporteDecoraciones.setBackground(new Color(220, 20, 60));
        botonReporteDecoraciones.setForeground(Color.WHITE);
        botonReporteDecoraciones.setFocusPainted(false);
        
        botonReporteCompras = new JButton("Compras");
        botonReporteCompras.setToolTipText("Generar reporte de compras");
        botonReporteCompras.setBackground(new Color(255, 215, 0));
        botonReporteCompras.setForeground(Color.BLACK);
        botonReporteCompras.setFocusPainted(false);
        
        botonReporteVentas = new JButton("Ventas");
        botonReporteVentas.setToolTipText("Generar reporte de ventas");
        botonReporteVentas.setBackground(new Color(34, 139, 34));
        botonReporteVentas.setForeground(Color.WHITE);
        botonReporteVentas.setFocusPainted(false);
        
        botonReporteInventario = new JButton("Inventario");
        botonReporteInventario.setToolTipText("Generar reporte de inventario");
        botonReporteInventario.setBackground(new Color(106, 90, 205));
        botonReporteInventario.setForeground(Color.WHITE);
        botonReporteInventario.setFocusPainted(false);
        
        botonReporteStockBajo = new JButton("Stock Bajo");
        botonReporteStockBajo.setToolTipText("Generar reporte de productos con stock bajo");
        botonReporteStockBajo.setBackground(new Color(255, 69, 0));
        botonReporteStockBajo.setForeground(Color.WHITE);
        botonReporteStockBajo.setFocusPainted(false);
        
        // Botones de acciones
        botonExportarCSV = new JButton("Exportar a CSV");
        botonExportarCSV.setToolTipText("Exportar datos actuales a formato CSV");
        botonExportarCSV.setBackground(new Color(70, 130, 180));
        botonExportarCSV.setForeground(Color.WHITE);
        botonExportarCSV.setFocusPainted(false);
        
        botonLimpiar = new JButton("Limpiar");
        botonLimpiar.setToolTipText("Limpiar tabla actual");
        botonLimpiar.setBackground(new Color(169, 169, 169));
        botonLimpiar.setForeground(Color.WHITE);
        botonLimpiar.setFocusPainted(false);
    }

    private void configurarEventos() {
        // Eventos de botones de reportes
        botonReporteUsuarios.addActionListener(e -> {
            actualizarColumnasTabla("ID", "Usuario", "Privilegio", "Estado", "Fecha Creación");
            controlador.generarReporteUsuarios(modeloTabla);
            labelInformacion.setText("Reporte de usuarios generado");
        });
        
        botonReporteClientes.addActionListener(e -> {
            actualizarColumnasTabla("ID", "Nombre", "Apellido", "RTN", "Tipo", "Dirección", "Teléfono", "Email", "Estado");
            controlador.generarReporteClientes(modeloTabla);
            labelInformacion.setText("Reporte de clientes generado");
        });
        
        botonReporteProveedores.addActionListener(e -> {
            actualizarColumnasTabla("ID", "Nombre", "RTN", "Dirección", "Teléfono", "Email", "Tipo", "Estado");
            controlador.generarReporteProveedores(modeloTabla);
            labelInformacion.setText("Reporte de proveedores generado");
        });
        
        botonReporteColecciones.addActionListener(e -> {
            actualizarColumnasTabla("ID", "Nombre", "Descripción", "Temporada", "Año", "Estado", "Fecha Creación");
            controlador.generarReporteColecciones(modeloTabla);
            labelInformacion.setText("Reporte de colecciones generado");
        });
        
        botonReporteDecoraciones.addActionListener(e -> {
            actualizarColumnasTabla("ID", "Nombre", "Stock", "Stock Mínimo", "Stock Máximo", "Estado Stock", "Precio", "Valor Total", "Colección", "Estado");
            controlador.generarReporteDecoraciones(modeloTabla);
            labelInformacion.setText("Reporte de decoraciones generado");
        });
        
        botonReporteCompras.addActionListener(e -> {
            actualizarColumnasTabla("ID", "Número Factura", "Proveedor", "Fecha", "Vencimiento", "Total", "Tipo Pago", "Estado", "Condición");
            controlador.generarReporteCompras(modeloTabla);
            labelInformacion.setText("Reporte de compras generado");
        });
        
        botonReporteVentas.addActionListener(e -> {
            actualizarColumnasTabla("ID", "Número Factura", "Cliente", "Vendedor", "Fecha", "Subtotal", "Impuesto", "Descuento", "Total", "Tipo Pago", "Estado");
            controlador.generarReporteVentas(modeloTabla);
            labelInformacion.setText("Reporte de ventas generado");
        });
        
        botonReporteInventario.addActionListener(e -> {
            actualizarColumnasTabla("ID", "Nombre", "Stock", "Stock Mínimo", "Stock Máximo", "Estado Stock", "Proveedor", "Colección", "Estado");
            controlador.generarReporteInventario(modeloTabla);
            labelInformacion.setText("Reporte de inventario generado");
        });
        
        botonReporteStockBajo.addActionListener(e -> {
            actualizarColumnasTabla("ID", "Nombre", "Stock", "Stock Mínimo", "Stock Máximo", "Proveedor", "Colección", "Estado");
            controlador.generarReporteStockBajo(modeloTabla);
            labelInformacion.setText("Reporte de stock bajo generado");
        });
        
        // Eventos de acciones
        botonExportarCSV.addActionListener(e -> {
            if (modeloTabla.getRowCount() > 0) {
                controlador.exportarACSV(modeloTabla, "reporte_" + System.currentTimeMillis());
            } else {
                JOptionPane.showMessageDialog(this, 
                        "No hay datos para exportar. Genere un reporte primero.",
                        "Sin Datos", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        botonLimpiar.addActionListener(e -> {
            modeloTabla.setRowCount(0);
            labelInformacion.setText("Tabla limpiada - Seleccione un reporte para generar");
        });
    }

    private void actualizarColumnasTabla(String... columnas) {
        modeloTabla.setColumnCount(0);
        for (String columna : columnas) {
            modeloTabla.addColumn(columna);
        }
        modeloTabla.setRowCount(0);
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
        
        // Icono de la ventana
        try {
            setIconImage(new ImageIcon(getClass().getResource("/iconos/reportes.png")).getImage());
        } catch (Exception e) {
            // Si no hay icono, continuar sin él
        }
        
        // Cambiar cursor mientras carga
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        // Restaurar cursor después de cargar
        SwingUtilities.invokeLater(() -> setCursor(Cursor.getDefaultCursor()));
    }

    /**
     * Método estático para mostrar la vista de reportes
     */
    public static void mostrarReportes() {
        SwingUtilities.invokeLater(() -> {
            try {
                ReportesVista vista = new ReportesVista();
                vista.setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(ReportesVista.class.getName()).log(Level.SEVERE, 
                        "Error al mostrar vista de reportes", ex);
                JOptionPane.showMessageDialog(null, 
                        "Error al abrir la vista de reportes: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
