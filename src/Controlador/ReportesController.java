package Controlador;

import Modelo.usuarios.UsuarioModel;
import Modelo.clientes.ClienteModel;
import Modelo.proveedores.ProveedorModel;
import Modelo.colecciones.ColeccionModel;
import Modelo.decoraciones.DecoracionModel;
import Modelo.compras.FacturaCompraModel;
import Modelo.ventas.VentaModel;
import Modelo.inventario.InventarioModel;
import Type.usuarios.UsuarioType;
import Type.clientes.ClienteType;
import Type.proveedores.ProveedorType;
import Type.colecciones.ColeccionType;
import Type.decoraciones.DecoracionType;
import Type.compras.FacturaCompraType;
import Type.ventas.VentaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador centralizado para generar todos los reportes del sistema
 * 
 * @author ossca
 */
public class ReportesController {

    private static final Logger logger = Logger.getLogger(ReportesController.class.getName());

    // Modelos para acceder a los datos
    private UsuarioModel usuarioModel;
    private ClienteModel clienteModel;
    private ProveedorModel proveedorModel;
    private ColeccionModel coleccionModel;
    private DecoracionModel decoracionModel;
    private FacturaCompraModel compraModel;
    private VentaModel ventaModel;
    private InventarioModel inventarioModel;

    public ReportesController() {
        this.usuarioModel = new UsuarioModel();
        this.clienteModel = new ClienteModel();
        this.proveedorModel = new ProveedorModel();
        this.coleccionModel = new ColeccionModel();
        this.decoracionModel = new DecoracionModel();
        this.compraModel = new FacturaCompraModel();
        this.ventaModel = new VentaModel();
        this.inventarioModel = new InventarioModel();
    }

    /**
     * Genera reporte de clientes con RTN y tipo
     */
    public void generarReporteClientes(DefaultTableModel modeloTabla) {
        try {
            ArrayList<ClienteType> clientes = clienteModel.getAll();
            modeloTabla.setRowCount(0);

            for (ClienteType cliente : clientes) {
                Object[] fila = {
                        cliente.getIdCliente(),
                        cliente.getNombreCliente(),
                        cliente.getRtnCliente(),
                        cliente.getTipoCliente() != null ? cliente.getTipoCliente().toString() : "N/A",
                        cliente.getDireccionCliente(),
                        cliente.getTelefonoCliente(),
                        cliente.getEmailCliente(),
                        cliente.isEstadoCliente() ? "ACTIVO" : "INACTIVO"
                };
                modeloTabla.addRow(fila);
            }

            JOptionPane.showMessageDialog(null,
                    "Reporte de clientes generado exitosamente. Total: " + clientes.size() + " registros",
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de clientes: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de clientes: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de proveedores
     */
    public void generarReporteProveedores(DefaultTableModel modeloTabla) {
        try {
            ArrayList<ProveedorType> proveedores = proveedorModel.getAll();
            modeloTabla.setRowCount(0);

            for (ProveedorType proveedor : proveedores) {
                Object[] fila = {
                        proveedor.getIdProveedor(),
                        proveedor.getNombreProveedor(),
                        proveedor.getRtnProveedor(),
                        proveedor.getDireccionProveedor(),
                        proveedor.getTelefonoProveedor(),
                        proveedor.getEmailProveedor(),
                        proveedor.isEstadoProveedor() ? "ACTIVO" : "INACTIVO"
                };
                modeloTabla.addRow(fila);
            }

            JOptionPane.showMessageDialog(null,
                    "Reporte de proveedores generado exitosamente. Total: " + proveedores.size() + " registros",
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de proveedores: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de proveedores: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de colecciones
     */
    public void generarReporteColecciones(DefaultTableModel modeloTabla) {
        try {
            ArrayList<ColeccionType> colecciones = coleccionModel.getAll();
            modeloTabla.setRowCount(0);

            for (ColeccionType coleccion : colecciones) {
                Object[] fila = {
                        coleccion.getIdColeccion(),
                        coleccion.getNombreColeccion(),
                        coleccion.getDescripcionColeccion(),
                        coleccion.getAnioColeccion(),
                        coleccion.isEstadoColeccion() ? "ACTIVA" : "INACTIVA",
                        coleccion.getFechaCreacion()
                };
                modeloTabla.addRow(fila);
            }

            JOptionPane.showMessageDialog(null,
                    "Reporte de colecciones generado exitosamente. Total: " + colecciones.size() + " registros",
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de colecciones: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de colecciones: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de decoraciones con stock y precios
     */
    public void generarReporteDecoraciones(DefaultTableModel modeloTabla) {
        try {
            ArrayList<DecoracionType> decoraciones = decoracionModel.getAll();
            modeloTabla.setRowCount(0);

            double valorTotalInventario = 0.0;

            for (DecoracionType decoracion : decoraciones) {
                double valorTotal = decoracion.getStockDecoracion() * getPrecioVentaDecoracion(decoracion);
                valorTotalInventario += valorTotal;

                Object[] fila = {
                        decoracion.getIdDecoracion(),
                        decoracion.getNombreDecoracion(),
                        decoracion.getStockDecoracion(),
                        decoracion.getStockMinimoDecoracion(),
                        decoracion.getStockMaximoDecoracion(),
                        decoracion.getStockDecoracion() <= decoracion.getStockMinimoDecoracion() ? "BAJO" : "NORMAL",
                        getPrecioVentaDecoracion(decoracion),
                        valorTotal,
                        decoracion.getIdColeccionDecoracion() != null ? decoracion.getIdColeccionDecoracion() : "N/A",
                        decoracion.isEstadoDecoracion() ? "ACTIVA" : "INACTIVA"
                };
                modeloTabla.addRow(fila);
            }

            JOptionPane.showMessageDialog(null,
                    "Reporte de decoraciones generado exitosamente.\n" +
                            "Total: " + decoraciones.size() + " registros\n" +
                            "Valor total del inventario: L." + String.format("%.2f", valorTotalInventario),
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de decoraciones: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de decoraciones: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de compras detallado
     */
    public void generarReporteCompras(DefaultTableModel modeloTabla) {
        try {
            ArrayList<FacturaCompraType> compras = compraModel.getAll();
            modeloTabla.setRowCount(0);

            double totalCompras = 0.0;

            for (FacturaCompraType compra : compras) {
                totalCompras += compra.getTotalFacturaCompra();

                Object[] fila = {
                        compra.getIdFacturaCompra(),
                        compra.getNumeroFactura(),
                        compra.getIdProveedorFacturaCompra() != null ? compra.getIdProveedorFacturaCompra() : "N/A",
                        compra.getFechaFacturaCompra(),
                        compra.getFechaVencimientoFactura(),
                        compra.getTotalFacturaCompra(),
                        compra.getTipoPagoFacturaCompra() != null ? compra.getTipoPagoFacturaCompra().toString()
                                : "N/A",
                        compra.getEstadoFacturaCompra() != null ? compra.getEstadoFacturaCompra().toString() : "N/A",
                        compra.getCondicionFactura() != null ? compra.getCondicionFactura() : "N/A"
                };
                modeloTabla.addRow(fila);
            }

            JOptionPane.showMessageDialog(null,
                    "Reporte de compras generado exitosamente.\n" +
                            "Total: " + compras.size() + " registros\n" +
                            "Monto total de compras: L." + String.format("%.2f", totalCompras),
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de compras: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de compras: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de ventas detallado
     */
    public void generarReporteVentas(DefaultTableModel modeloTabla) {
        try {
            ArrayList<VentaType> ventas = ventaModel.getAll();
            modeloTabla.setRowCount(0);

            double totalVentas = 0.0;

            for (VentaType venta : ventas) {
                totalVentas += venta.getTotalVenta();

                Object[] fila = {
                        venta.getIdVenta(),
                        venta.getNumeroFacturaVenta(),
                        venta.getIdClienteVenta() != null ? venta.getIdClienteVenta() : "N/A",
                        venta.getIdUsuarioVendedor() != null ? venta.getIdUsuarioVendedor() : "N/A",
                        venta.getFechaVenta(),
                        venta.getSubtotalVenta(),
                        venta.getImpuestoVenta(),
                        venta.getDescuentoVenta(),
                        venta.getTotalVenta(),
                        venta.getTipoPagoVenta() != null ? venta.getTipoPagoVenta().toString() : "N/A",
                        venta.getEstadoVenta() != null ? venta.getEstadoVenta().toString() : "N/A"
                };
                modeloTabla.addRow(fila);
            }

            JOptionPane.showMessageDialog(null,
                    "Reporte de ventas generado exitosamente.\n" +
                            "Total: " + ventas.size() + " registros\n" +
                            "Monto total de ventas: L." + String.format("%.2f", totalVentas),
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de ventas: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de ventas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de inventario con existencias
     */
    public void generarReporteInventario(DefaultTableModel modeloTabla) {
        try {
            ArrayList<DecoracionType> productos = inventarioModel.getAll();
            modeloTabla.setRowCount(0);

            int totalItems = 0;
            int itemsStockBajo = 0;

            for (DecoracionType producto : productos) {
                totalItems += producto.getStockDecoracion();
                if (producto.getStockDecoracion() <= producto.getStockMinimoDecoracion()) {
                    itemsStockBajo++;
                }

                Object[] fila = {
                        producto.getIdDecoracion(),
                        producto.getNombreDecoracion(),
                        producto.getStockDecoracion(),
                        producto.getStockMinimoDecoracion(),
                        producto.getStockMaximoDecoracion(),
                        producto.getStockDecoracion() <= producto.getStockMinimoDecoracion() ? "BAJO" : "NORMAL",
                        producto.getIdProveedorDecoracion() != null ? producto.getIdProveedorDecoracion() : "N/A",
                        producto.getIdColeccionDecoracion() != null ? producto.getIdColeccionDecoracion() : "N/A",
                        producto.isEstadoDecoracion() ? "ACTIVO" : "INACTIVO"
                };
                modeloTabla.addRow(fila);
            }

            JOptionPane.showMessageDialog(null,
                    "Reporte de inventario generado exitosamente.\n" +
                            "Total productos: " + productos.size() + "\n" +
                            "Total items en stock: " + totalItems + "\n" +
                            "Items con stock bajo: " + itemsStockBajo,
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de inventario: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de inventario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de stock bajo
     */
    public void generarReporteStockBajo(DefaultTableModel modeloTabla) {
        try {
            ArrayList<DecoracionType> productosStockBajo = inventarioModel.getStockBajo();
            modeloTabla.setRowCount(0);

            for (DecoracionType producto : productosStockBajo) {
                Object[] fila = {
                        producto.getIdDecoracion(),
                        producto.getNombreDecoracion(),
                        producto.getStockDecoracion(),
                        producto.getStockMinimoDecoracion(),
                        producto.getStockMaximoDecoracion(),
                        producto.getIdProveedorDecoracion() != null ? producto.getIdProveedorDecoracion() : "N/A",
                        producto.getIdColeccionDecoracion() != null ? producto.getIdColeccionDecoracion() : "N/A",
                        "URGENTE - Necesita reabastecimiento"
                };
                modeloTabla.addRow(fila);
            }

            JOptionPane.showMessageDialog(null,
                    "Reporte de stock bajo generado exitosamente.\n" +
                            "Total productos con stock bajo: " + productosStockBajo.size(),
                    "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de stock bajo: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de stock bajo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método auxiliar para obtener precio de venta de decoración
     * (placeholder - debería obtenerse desde un campo real o cálculo)
     */
    private double getPrecioVentaDecoracion(DecoracionType decoracion) {
        // Este es un placeholder - en una implementación real
        // debería obtener el precio desde un campo específico o cálculo
        return 100.0; // Precio base de ejemplo
    }

    /**
     * Exporta datos a formato CSV
     */
    public void exportarACSV(DefaultTableModel modeloTabla, String nombreArchivo) {
        try {
            StringBuilder csv = new StringBuilder();

            // Agregar encabezados
            for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                csv.append(modeloTabla.getColumnName(i));
                if (i < modeloTabla.getColumnCount() - 1) {
                    csv.append(",");
                }
            }
            csv.append("\n");

            // Agregar datos
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                    Object valor = modeloTabla.getValueAt(i, j);
                    csv.append(valor != null ? valor.toString() : "");
                    if (j < modeloTabla.getColumnCount() - 1) {
                        csv.append(",");
                    }
                }
                csv.append("\n");
            }

            // Aquí debería implementarse la lógica real para guardar el archivo
            JOptionPane.showMessageDialog(null,
                    "Exportación a CSV simulada.\n" +
                            "Archivo: " + nombreArchivo + ".csv\n" +
                            "Registros: " + modeloTabla.getRowCount(),
                    "Exportación", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al exportar a CSV: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al exportar a CSV: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de decoraciones agrupadas por colección
     */
    public void generarReporteDecoracionesPorColeccion(DefaultTableModel modeloTabla) {
        try {
            ArrayList<ColeccionType> colecciones = coleccionModel.getAll();
            modeloTabla.setRowCount(0);

            // Configurar columnas
            modeloTabla.setColumnIdentifiers(new Object[] {
                    "ID Colección", "Nombre Colección", "Cantidad Decoraciones",
                    "Stock Total", "Valor Total Inventario"
            });

            for (ColeccionType coleccion : colecciones) {
                if (coleccion.isEstadoColeccion()) {
                    // Contar decoraciones de esta colección
                    int cantidadDecoraciones = 0;
                    int stockTotal = 0;
                    double valorTotal = 0.0;

                    // Aquí deberías obtener decoraciones filtradas por colección
                    // Por ahora, simulamos el conteo
                    ArrayList<DecoracionType> todasDecoraciones = decoracionModel.getAll();
                    for (DecoracionType decoracion : todasDecoraciones) {
                        if (coleccion.getIdColeccion().equals(decoracion.getIdColeccionDecoracion())) {
                            cantidadDecoraciones++;
                            stockTotal += decoracion.getStockDecoracion();
                            // Precio simulado - debería obtenerse de un campo real
                            valorTotal += decoracion.getStockDecoracion() * 100.0;
                        }
                    }

                    Object[] fila = {
                            coleccion.getIdColeccion(),
                            coleccion.getNombreColeccion(),
                            cantidadDecoraciones,
                            stockTotal,
                            String.format("%.2f", valorTotal)
                    };
                    modeloTabla.addRow(fila);
                }
            }

            logger.log(Level.INFO, "Reporte de decoraciones por colección generado correctamente");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de decoraciones por colección: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de decoraciones por colección: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de ventas por cliente
     */
    public void generarReporteVentasPorCliente(DefaultTableModel modeloTabla) {
        try {
            ArrayList<VentaType> ventas = ventaModel.getAll();
            modeloTabla.setRowCount(0);

            // Configurar columnas
            modeloTabla.setColumnIdentifiers(new Object[] {
                    "ID Cliente", "Nombre Cliente", "Cantidad Ventas",
                    "Total Compras", "Última Compra"
            });

            // Agrupar ventas por cliente (simulación)
            java.util.HashMap<String, Object[]> ventasPorCliente = new java.util.HashMap<>();

            for (VentaType venta : ventas) {
                String idCliente = venta.getIdClienteVenta();
                if (!ventasPorCliente.containsKey(idCliente)) {
                    ventasPorCliente.put(idCliente, new Object[] {
                            idCliente,
                            "Cliente " + idCliente, // Debería obtener nombre real
                            0,
                            0.0,
                            venta.getFechaVenta()
                    });
                }

                Object[] datos = ventasPorCliente.get(idCliente);
                datos[2] = (Integer) datos[2] + 1;
                datos[3] = (Double) datos[3] + venta.getTotalVenta();
                if (venta.getFechaVenta().after((java.sql.Timestamp) datos[4])) {
                    datos[4] = venta.getFechaVenta();
                }
            }

            // Agregar filas al modelo
            for (Object[] datos : ventasPorCliente.values()) {
                modeloTabla.addRow(new Object[] {
                        datos[0],
                        datos[1],
                        datos[2],
                        String.format("%.2f", datos[3]),
                        datos[4]
                });
            }

            logger.log(Level.INFO, "Reporte de ventas por cliente generado correctamente");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de ventas por cliente: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de ventas por cliente: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de compras por proveedor
     */
    public void generarReporteComprasPorProveedor(DefaultTableModel modeloTabla) {
        try {
            ArrayList<FacturaCompraType> compras = compraModel.getAll();
            modeloTabla.setRowCount(0);

            // Configurar columnas
            modeloTabla.setColumnIdentifiers(new Object[] {
                    "ID Proveedor", "Nombre Proveedor", "Cantidad Compras",
                    "Total Compras", "Última Compra"
            });

            // Agrupar compras por proveedor (simulación)
            java.util.HashMap<String, Object[]> comprasPorProveedor = new java.util.HashMap<>();

            for (FacturaCompraType compra : compras) {
                String idProveedor = compra.getIdProveedorFacturaCompra();
                if (!comprasPorProveedor.containsKey(idProveedor)) {
                    comprasPorProveedor.put(idProveedor, new Object[] {
                            idProveedor,
                            "Proveedor " + idProveedor, // Debería obtener nombre real
                            0,
                            0.0,
                            compra.getFechaFacturaCompra()
                    });
                }

                Object[] datos = comprasPorProveedor.get(idProveedor);
                datos[2] = (Integer) datos[2] + 1;
                datos[3] = (Double) datos[3] + compra.getTotalFacturaCompra();
                if (compra.getFechaFacturaCompra().after((java.sql.Timestamp) datos[4])) {
                    datos[4] = compra.getFechaFacturaCompra();
                }
            }

            // Agregar filas al modelo
            for (Object[] datos : comprasPorProveedor.values()) {
                modeloTabla.addRow(new Object[] {
                        datos[0],
                        datos[1],
                        datos[2],
                        String.format("%.2f", datos[3]),
                        datos[4]
                });
            }

            logger.log(Level.INFO, "Reporte de compras por proveedor generado correctamente");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de compras por proveedor: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de compras por proveedor: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de existencias críticas (stock bajo y sin stock)
     */
    public void generarReporteExistenciasCriticas(DefaultTableModel modeloTabla) {
        try {
            ArrayList<DecoracionType> productos = inventarioModel.getAll();
            modeloTabla.setRowCount(0);

            // Configurar columnas
            modeloTabla.setColumnIdentifiers(new Object[] {
                    "ID Decoración", "Nombre Decoración", "Stock Actual",
                    "Stock Mínimo", "Estado", "Acción Recomendada"
            });

            for (DecoracionType producto : productos) {
                String estado = "Normal";
                String accion = "Ninguna";

                if (producto.getStockDecoracion() == 0) {
                    estado = "SIN STOCK";
                    accion = "Reponer urgentemente";
                } else if (producto.getStockDecoracion() <= producto.getStockMinimoDecoracion()) {
                    estado = "STOCK BAJO";
                    accion = "Reponer pronto";
                }

                // Solo mostrar productos con problemas de stock
                if (!estado.equals("Normal")) {
                    Object[] fila = {
                            producto.getIdDecoracion(),
                            producto.getNombreDecoracion(),
                            producto.getStockDecoracion(),
                            producto.getStockMinimoDecoracion(),
                            estado,
                            accion
                    };
                    modeloTabla.addRow(fila);
                }
            }

            logger.log(Level.INFO, "Reporte de existencias críticas generado correctamente");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de existencias críticas: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de existencias críticas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de movimientos de inventario (entradas y salidas)
     */
    public void generarReporteMovimientosInventario(DefaultTableModel modeloTabla) {
        try {
            modeloTabla.setRowCount(0);

            // Configurar columnas
            modeloTabla.setColumnIdentifiers(new Object[] {
                    "Fecha", "ID Decoración", "Nombre Decoración",
                    "Tipo Movimiento", "Cantidad", "Motivo", "Responsable"
            });

            // Simular movimientos (debería obtener de una tabla de movimientos)
            // Por ahora, mostramos datos de ejemplo basados en ventas y compras

            // Movimientos de ventas (salidas)
            ArrayList<VentaType> ventas = ventaModel.getAll();
            for (VentaType venta : ventas) {
                Object[] fila = {
                        venta.getFechaVenta(),
                        "VTA-" + venta.getIdVenta(),
                        "Venta #" + venta.getIdVenta(),
                        "SALIDA",
                        "1", // Debería obtener cantidad real
                        "Venta al cliente",
                        venta.getIdUsuarioVendedor()
                };
                modeloTabla.addRow(fila);
            }

            // Movimientos de compras (entradas)
            ArrayList<FacturaCompraType> compras = compraModel.getAll();
            for (FacturaCompraType compra : compras) {
                Object[] fila = {
                        compra.getFechaFacturaCompra(),
                        "COMP-" + compra.getIdFacturaCompra(),
                        "Compra #" + compra.getIdFacturaCompra(),
                        "ENTRADA",
                        "1", // Debería obtener cantidad real
                        "Compra a proveedor",
                        "ADMIN"
                };
                modeloTabla.addRow(fila);
            }

            logger.log(Level.INFO, "Reporte de movimientos de inventario generado correctamente");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de movimientos de inventario: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de movimientos de inventario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de usuarios con privilegios y estado
     */
    public void generarReporteUsuarios(DefaultTableModel modeloTabla) {
        try {
            ArrayList<UsuarioType> usuarios = usuarioModel.getAll();
            modeloTabla.setRowCount(0);

            // Configurar columnas
            modeloTabla.setColumnIdentifiers(new Object[] {
                    "ID Usuario", "Nombre", "Privilegio", "Estado", "Fecha Creación"
            });

            for (UsuarioType usuario : usuarios) {
                Object[] fila = {
                        usuario.getIdUsuario(),
                        usuario.getNombreUsuario(),
                        usuario.getPrivilegioUsuario() != null ? usuario.getPrivilegioUsuario().toString()
                                : "SIN PRIVILEGIO",
                        usuario.getFechaCreacion() != null ? usuario.getFechaCreacion().toString() : "SIN FECHA"
                };
                modeloTabla.addRow(fila);
            }

            logger.log(Level.INFO, "Reporte de usuarios generado correctamente");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de usuarios: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de usuarios: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de usuarios por privilegio
     */
    public void generarReporteUsuariosPorPrivilegio(DefaultTableModel modeloTabla) {
        try {
            ArrayList<UsuarioType> usuarios = usuarioModel.getAll();
            modeloTabla.setRowCount(0);

            // Configurar columnas
            modeloTabla.setColumnIdentifiers(new Object[] {
                    "Privilegio", "Cantidad Usuarios", "Activos", "Inactivos"
            });

            // Agrupar por privilegio
            java.util.Map<String, Object[]> usuariosPorPrivilegio = new java.util.HashMap<>();

            for (UsuarioType usuario : usuarios) {
                String privilegio = usuario.getPrivilegioUsuario() != null ? usuario.getPrivilegioUsuario().toString()
                        : "SIN PRIVILEGIO";

                if (!usuariosPorPrivilegio.containsKey(privilegio)) {
                    usuariosPorPrivilegio.put(privilegio, new Object[] {
                            privilegio, 0, 0, 0
                    });
                }

                Object[] datos = usuariosPorPrivilegio.get(privilegio);
                datos[1] = (Integer) datos[1] + 1;
                if (usuario.isEstadoUsuario()) {
                    datos[2] = (Integer) datos[2] + 1;
                } else {
                    datos[3] = (Integer) datos[3] + 1;
                }
            }

            // Agregar filas al modelo
            for (Object[] datos : usuariosPorPrivilegio.values()) {
                modeloTabla.addRow(datos);
            }

            logger.log(Level.INFO, "Reporte de usuarios por privilegio generado correctamente");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de usuarios por privilegio: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de usuarios por privilegio: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de actividad de usuarios (login y accesos)
     */
    public void generarReporteActividadUsuarios(DefaultTableModel modeloTabla) {
        try {
            modeloTabla.setRowCount(0);

            // Configurar columnas
            modeloTabla.setColumnIdentifiers(new Object[] {
                    "Fecha", "Usuario", "Módulo Accedido", "Acción", "Resultado"
            });

            // Simular datos de actividad (debería obtenerse de tabla de logs)
            Object[][] actividadSimulada = {
                    { java.sql.Timestamp.valueOf("2024-01-15 09:00:00"), "admin", "Usuarios", "Inicio Sesión",
                            "Exitoso" },
                    { java.sql.Timestamp.valueOf("2024-01-15 09:15:00"), "vendedor", "Ventas", "Acceso Clientes",
                            "Exitoso" },
                    { java.sql.Timestamp.valueOf("2024-01-15 10:30:00"), "admin", "Compras", "Intento Acceso",
                            "Denegado" },
                    { java.sql.Timestamp.valueOf("2024-01-15 11:00:00"), "inventario", "Inventario", "Ajuste Stock",
                            "Exitoso" },
                    { java.sql.Timestamp.valueOf("2024-01-15 14:20:00"), "vendedor", "Ventas", "Acceso Reportes",
                            "Exitoso" }
            };

            for (Object[] actividad : actividadSimulada) {
                modeloTabla.addRow(actividad);
            }

            logger.log(Level.INFO, "Reporte de actividad de usuarios generado correctamente");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de actividad de usuarios: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de actividad de usuarios: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Genera reporte de colecciones con conteo de decoraciones
     */
    public void generarReporteColeccionesConDecoraciones(DefaultTableModel modeloTabla) {
        try {
            ArrayList<ColeccionType> colecciones = coleccionModel.getAll();
            modeloTabla.setRowCount(0);

            // Configurar columnas
            modeloTabla.setColumnIdentifiers(new Object[] {
                    "ID", "Nombre", "Diseñador", "# Colección", "Año", "Estado", "# Decoraciones"
            });

            for (ColeccionType coleccion : colecciones) {
                // Obtener conteo de decoraciones para esta colección
                int conteoDecoraciones = coleccionModel.getConteoDecoracionesPorColeccion(coleccion.getIdColeccion());

                Object[] fila = {
                        coleccion.getIdColeccion(),
                        coleccion.getNombreColeccion(),
                        coleccion.getDisenadorColeccion(),
                        coleccion.getNumColeccionColeccion(),
                        coleccion.getAnioColeccion(),
                        coleccion.isEstadoColeccion() ? "Activo" : "Inactivo",
                        conteoDecoraciones
                };
                modeloTabla.addRow(fila);
            }

            logger.log(Level.INFO, "Reporte de colecciones con decoraciones generado correctamente");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al generar reporte de colecciones con decoraciones: " + ex.getMessage(),
                    ex);
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte de colecciones con decoraciones: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
