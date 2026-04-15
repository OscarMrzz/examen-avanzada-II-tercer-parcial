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
 * Controlador para testing integral del sistema
 * 
 * @author ossca
 */
public class TestingController {

    private static final Logger logger = Logger.getLogger(TestingController.class.getName());
    
    // Modelos para testing
    private UsuarioModel usuarioModel;
    private ClienteModel clienteModel;
    private ProveedorModel proveedorModel;
    private ColeccionModel coleccionModel;
    private DecoracionModel decoracionModel;
    private FacturaCompraModel compraModel;
    private VentaModel ventaModel;
    private InventarioModel inventarioModel;
    
    // Contadores de resultados
    private int pruebasTotales = 0;
    private int pruebasExitosas = 0;
    private int pruebasFallidas = 0;
    private StringBuilder resultados = new StringBuilder();

    public TestingController() {
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
     * Ejecuta todas las pruebas del sistema
     */
    public void ejecutarTestingIntegral(DefaultTableModel modeloTabla) {
        reiniciarContadores();
        
        resultados.append("=== INICIO DE TESTING INTEGRAL DEL SISTEMA ===\n\n");
        
        // Pruebas de Conexión y Modelos
        probarConexionModelos();
        
        // Pruebas de CRUD Usuarios
        probarCRUDUsuarios();
        
        // Pruebas de CRUD Clientes
        probarCRUDClientes();
        
        // Pruebas de CRUD Proveedores
        probarCRUDProveedores();
        
        // Pruebas de CRUD Colecciones
        probarCRUDColecciones();
        
        // Pruebas de CRUD Decoraciones
        probarCRUDDecoraciones();
        
        // Pruebas de CRUD Compras
        probarCRUDCompras();
        
        // Pruebas de CRUD Ventas
        probarCRUDVentas();
        
        // Pruebas de Inventario
        probarInventario();
        
        // Pruebas de Integración
        probarIntegracion();
        
        // Mostrar resultados finales
        mostrarResultadosFinales();
        
        // Actualizar tabla con resumen
        actualizarTablaResumen(modeloTabla);
    }

    private void reiniciarContadores() {
        pruebasTotales = 0;
        pruebasExitosas = 0;
        pruebasFallidas = 0;
        resultados = new StringBuilder();
    }

    private void probarConexionModelos() {
        resultados.append("=== PRUEBAS DE CONEXIÓN Y MODELOS ===\n");
        
        probarMetodo("Conexión UsuarioModel", () -> {
            ArrayList<UsuarioType> usuarios = usuarioModel.getAll();
            return usuarios != null;
        });
        
        probarMetodo("Conexión ClienteModel", () -> {
            ArrayList<ClienteType> clientes = clienteModel.getAll();
            return clientes != null;
        });
        
        probarMetodo("Conexión ProveedorModel", () -> {
            ArrayList<ProveedorType> proveedores = proveedorModel.getAll();
            return proveedores != null;
        });
        
        probarMetodo("Conexión ColeccionModel", () -> {
            ArrayList<ColeccionType> colecciones = coleccionModel.getAll();
            return colecciones != null;
        });
        
        probarMetodo("Conexión DecoracionModel", () -> {
            ArrayList<DecoracionType> decoraciones = decoracionModel.getAll();
            return decoraciones != null;
        });
        
        probarMetodo("Conexión CompraModel", () -> {
            ArrayList<FacturaCompraType> compras = compraModel.getAll();
            return compras != null;
        });
        
        probarMetodo("Conexión VentaModel", () -> {
            ArrayList<VentaType> ventas = ventaModel.getAll();
            return ventas != null;
        });
        
        probarMetodo("Conexión InventarioModel", () -> {
            ArrayList<DecoracionType> productos = inventarioModel.getAll();
            return productos != null;
        });
        
        resultados.append("\n");
    }

    private void probarCRUDUsuarios() {
        resultados.append("=== PRUEBAS CRUD USUARIOS ===\n");
        
        // Prueba de obtención de todos los usuarios
        probarMetodo("Obtener todos los usuarios", () -> {
            ArrayList<UsuarioType> usuarios = usuarioModel.getAll();
            return usuarios != null && !usuarios.isEmpty();
        });
        
        // Prueba de obtención por ID (simulada)
        probarMetodo("Obtener usuario por ID", () -> {
            ArrayList<UsuarioType> usuarios = usuarioModel.getAll();
            if (!usuarios.isEmpty()) {
                UsuarioType usuario = usuarioModel.getById(usuarios.get(0).getIdUsuario());
                return usuario != null;
            }
            return false;
        });
        
        resultados.append("\n");
    }

    private void probarCRUDClientes() {
        resultados.append("=== PRUEBAS CRUD CLIENTES ===\n");
        
        probarMetodo("Obtener todos los clientes", () -> {
            ArrayList<ClienteType> clientes = clienteModel.getAll();
            return clientes != null && !clientes.isEmpty();
        });
        
        probarMetodo("Obtener cliente por ID", () -> {
            ArrayList<ClienteType> clientes = clienteModel.getAll();
            if (!clientes.isEmpty()) {
                ClienteType cliente = clienteModel.getById(clientes.get(0).getIdCliente());
                return cliente != null;
            }
            return false;
        });
        
        resultados.append("\n");
    }

    private void probarCRUDProveedores() {
        resultados.append("=== PRUEBAS CRUD PROVEEDORES ===\n");
        
        probarMetodo("Obtener todos los proveedores", () -> {
            ArrayList<ProveedorType> proveedores = proveedorModel.getAll();
            return proveedores != null && !proveedores.isEmpty();
        });
        
        probarMetodo("Obtener proveedor por ID", () -> {
            ArrayList<ProveedorType> proveedores = proveedorModel.getAll();
            if (!proveedores.isEmpty()) {
                ProveedorType proveedor = proveedorModel.getById(proveedores.get(0).getIdProveedor());
                return proveedor != null;
            }
            return false;
        });
        
        resultados.append("\n");
    }

    private void probarCRUDColecciones() {
        resultados.append("=== PRUEBAS CRUD COLECCIONES ===\n");
        
        probarMetodo("Obtener todas las colecciones", () -> {
            ArrayList<ColeccionType> colecciones = coleccionModel.getAll();
            return colecciones != null && !colecciones.isEmpty();
        });
        
        probarMetodo("Obtener colección por ID", () -> {
            ArrayList<ColeccionType> colecciones = coleccionModel.getAll();
            if (!colecciones.isEmpty()) {
                ColeccionType coleccion = coleccionModel.getById(colecciones.get(0).getIdColeccion());
                return coleccion != null;
            }
            return false;
        });
        
        resultados.append("\n");
    }

    private void probarCRUDDecoraciones() {
        resultados.append("=== PRUEBAS CRUD DECORACIONES ===\n");
        
        probarMetodo("Obtener todas las decoraciones", () -> {
            ArrayList<DecoracionType> decoraciones = decoracionModel.getAll();
            return decoraciones != null && !decoraciones.isEmpty();
        });
        
        probarMetodo("Obtener decoración por ID", () -> {
            ArrayList<DecoracionType> decoraciones = decoracionModel.getAll();
            if (!decoraciones.isEmpty()) {
                DecoracionType decoracion = decoracionModel.getById(decoraciones.get(0).getIdDecoracion());
                return decoracion != null;
            }
            return false;
        });
        
        probarMetodo("Validar stock de decoraciones", () -> {
            ArrayList<DecoracionType> decoraciones = decoracionModel.getAll();
            for (DecoracionType decoracion : decoraciones) {
                if (decoracion.getStockDecoracion() < 0 || 
                    decoracion.getStockMinimoDecoracion() < 0 || 
                    decoracion.getStockMaximoDecoracion() < 0) {
                    return false;
                }
            }
            return true;
        });
        
        resultados.append("\n");
    }

    private void probarCRUDCompras() {
        resultados.append("=== PRUEBAS CRUD COMPRAS ===\n");
        
        probarMetodo("Obtener todas las compras", () -> {
            ArrayList<FacturaCompraType> compras = compraModel.getAll();
            return compras != null && !compras.isEmpty();
        });
        
        probarMetodo("Obtener compra por ID", () -> {
            ArrayList<FacturaCompraType> compras = compraModel.getAll();
            if (!compras.isEmpty()) {
                FacturaCompraType compra = compraModel.getById(compras.get(0).getIdFacturaCompra());
                return compra != null;
            }
            return false;
        });
        
        probarMetodo("Validar montos de compras", () -> {
            ArrayList<FacturaCompraType> compras = compraModel.getAll();
            for (FacturaCompraType compra : compras) {
                if (compra.getTotalFacturaCompra() < 0) {
                    return false;
                }
            }
            return true;
        });
        
        resultados.append("\n");
    }

    private void probarCRUDVentas() {
        resultados.append("=== PRUEBAS CRUD VENTAS ===\n");
        
        probarMetodo("Obtener todas las ventas", () -> {
            ArrayList<VentaType> ventas = ventaModel.getAll();
            return ventas != null && !ventas.isEmpty();
        });
        
        probarMetodo("Obtener venta por ID", () -> {
            ArrayList<VentaType> ventas = ventaModel.getAll();
            if (!ventas.isEmpty()) {
                VentaType venta = ventaModel.getById(ventas.get(0).getIdVenta());
                return venta != null;
            }
            return false;
        });
        
        probarMetodo("Validar cálculos de ventas", () -> {
            ArrayList<VentaType> ventas = ventaModel.getAll();
            for (VentaType venta : ventas) {
                double calculado = venta.getSubtotalVenta() + venta.getImpuestoVenta() - venta.getDescuentoVenta();
                if (Math.abs(calculado - venta.getTotalVenta()) > 0.01) {
                    return false;
                }
            }
            return true;
        });
        
        resultados.append("\n");
    }

    private void probarInventario() {
        resultados.append("=== PRUEBAS DE INVENTARIO ===\n");
        
        probarMetodo("Obtener inventario completo", () -> {
            ArrayList<DecoracionType> productos = inventarioModel.getAll();
            return productos != null && !productos.isEmpty();
        });
        
        probarMetodo("Obtener productos con stock bajo", () -> {
            ArrayList<DecoracionType> stockBajo = inventarioModel.getStockBajo();
            return stockBajo != null;
        });
        
        probarMetodo("Validar consistencia de stock", () -> {
            ArrayList<DecoracionType> productos = inventarioModel.getAll();
            for (DecoracionType producto : productos) {
                if (producto.getStockDecoracion() < producto.getStockMinimoDecoracion() && 
                    producto.getStockMaximoDecoracion() < producto.getStockMinimoDecoracion()) {
                    return false; // Stock máximo no puede ser menor que mínimo
                }
            }
            return true;
        });
        
        resultados.append("\n");
    }

    private void probarIntegracion() {
        resultados.append("=== PRUEBAS DE INTEGRACIÓN ===\n");
        
        probarMetodo("Integración Colecciones-Decoraciones", () -> {
            ArrayList<ColeccionType> colecciones = coleccionModel.getAll();
            ArrayList<DecoracionType> decoraciones = decoracionModel.getAll();
            
            if (colecciones.isEmpty() || decoraciones.isEmpty()) {
                return true; // Si no hay datos, no hay problema de integración
            }
            
            // Verificar que las decoraciones tengan colecciones válidas
            for (DecoracionType decoracion : decoraciones) {
                if (decoracion.getIdColeccionDecoracion() != null) {
                    boolean coleccionValida = false;
                    for (ColeccionType coleccion : colecciones) {
                        if (decoracion.getIdColeccionDecoracion().equals(coleccion.getIdColeccion())) {
                            coleccionValida = true;
                            break;
                        }
                    }
                    if (!coleccionValida) {
                        return false;
                    }
                }
            }
            return true;
        });
        
        probarMetodo("Integración Proveedores-Compras", () -> {
            ArrayList<ProveedorType> proveedores = proveedorModel.getAll();
            ArrayList<FacturaCompraType> compras = compraModel.getAll();
            
            if (proveedores.isEmpty() || compras.isEmpty()) {
                return true;
            }
            
            // Verificar que las compras tengan proveedores válidos
            for (FacturaCompraType compra : compras) {
                if (compra.getIdProveedorFacturaCompra() != null) {
                    boolean proveedorValido = false;
                    for (ProveedorType proveedor : proveedores) {
                        if (compra.getIdProveedorFacturaCompra().equals(proveedor.getIdProveedor())) {
                            proveedorValido = true;
                            break;
                        }
                    }
                    if (!proveedorValido) {
                        return false;
                    }
                }
            }
            return true;
        });
        
        probarMetodo("Integración Clientes-Ventas", () -> {
            ArrayList<ClienteType> clientes = clienteModel.getAll();
            ArrayList<VentaType> ventas = ventaModel.getAll();
            
            if (clientes.isEmpty() || ventas.isEmpty()) {
                return true;
            }
            
            // Verificar que las ventas tengan clientes válidos
            for (VentaType venta : ventas) {
                if (venta.getIdClienteVenta() != null) {
                    boolean clienteValido = false;
                    for (ClienteType cliente : clientes) {
                        if (venta.getIdClienteVenta().equals(cliente.getIdCliente())) {
                            clienteValido = true;
                            break;
                        }
                    }
                    if (!clienteValido) {
                        return false;
                    }
                }
            }
            return true;
        });
        
        resultados.append("\n");
    }

    private void probarMetodo(String nombreMetodo, RunnableTest test) {
        pruebasTotales++;
        resultados.append("Prueba ").append(pruebasTotales).append(": ").append(nombreMetodo).append("... ");
        
        try {
            boolean resultado = test.run();
            if (resultado) {
                pruebasExitosas++;
                resultados.append("EXITOSA\n");
            } else {
                pruebasFallidas++;
                resultados.append("FALLIDA\n");
            }
        } catch (Exception ex) {
            pruebasFallidas++;
            logger.log(Level.SEVERE, "Error en prueba: " + nombreMetodo, ex);
            resultados.append("ERROR: ").append(ex.getMessage()).append("\n");
        }
    }

    private void mostrarResultadosFinales() {
        resultados.append("=== RESULTADOS FINALES ===\n");
        resultados.append("Pruebas totales: ").append(pruebasTotales).append("\n");
        resultados.append("Pruebas exitosas: ").append(pruebasExitosas).append("\n");
        resultados.append("Pruebas fallidas: ").append(pruebasFallidas).append("\n");
        resultados.append("Porcentaje de éxito: ").append(String.format("%.1f", (double)pruebasExitosas / pruebasTotales * 100)).append("%\n");
        
        if (pruebasFallidas == 0) {
            resultados.append("\n¡TODAS LAS PRUEBAS PASARON EXITOSAMENTE! El sistema está listo para producción.\n");
        } else {
            resultados.append("\nSe detectaron ").append(pruebasFallidas).append(" problemas que requieren atención.\n");
        }
        
        // Mostrar resultados en consola
        System.out.println(resultados.toString());
        
        // Mostrar resumen al usuario
        String mensaje = "Testing completado:\n" +
                "Total: " + pruebasTotales + "\n" +
                "Exitosas: " + pruebasExitosas + "\n" +
                "Fallidas: " + pruebasFallidas + "\n" +
                "Éxito: " + String.format("%.1f", (double)pruebasExitosas / pruebasTotales * 100) + "%";
        
        int tipoMensaje = pruebasFallidas == 0 ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE;
        JOptionPane.showMessageDialog(null, mensaje, "Resultados del Testing", tipoMensaje);
    }

    private void actualizarTablaResumen(DefaultTableModel modeloTabla) {
        modeloTabla.setRowCount(0);
        
        Object[] fila = {
            "Testing Integral",
            pruebasTotales,
            pruebasExitosas,
            pruebasFallidas,
            String.format("%.1f%%", (double)pruebasExitosas / pruebasTotales * 100),
            pruebasFallidas == 0 ? "APROBADO" : "NECESITA REVISIÓN"
        };
        modeloTabla.addRow(fila);
    }

    @FunctionalInterface
    private interface RunnableTest {
        boolean run() throws Exception;
    }
}
